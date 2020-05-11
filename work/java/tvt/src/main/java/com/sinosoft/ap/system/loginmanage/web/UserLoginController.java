package com.sinosoft.ap.system.loginmanage.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.component.verifycode.service.VerifyCodeService;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginEntity;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginLogEntity;
import com.sinosoft.ap.system.loginmanage.exception.LoginException;
import com.sinosoft.ap.system.loginmanage.service.UserLoginLogService;
import com.sinosoft.ap.system.loginmanage.service.UserLoginService;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationSearchService;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.organizationmanage.service.OrganizationSearchService;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionSearchService;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionResourceRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionResourceRelService;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.service.UserOrganizationRelService;
import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;
import com.sinosoft.ap.system.usersettings.service.UserHeadService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.data.LoginData;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.list.ListUtil;
import com.sinosoft.ap.util.md5.MD5Encrypt;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

import static com.sinosoft.ap.util.token.GetToken.analysis;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户登录管理",description="用户登录管理")
public class UserLoginController {
	
	@Autowired
	private JavaWebToken javaWebToken;

	@Autowired
	private UserOrganizationRelService userOrganizationRelService;
	
	@Autowired
	private UserLoginService userLoginService;

	@Autowired
	private UserLoginLogService userLoginLogService;

	@Autowired
	private VerifyCodeService verifyCodeService;
	
	@Autowired
	private UserSearchService userSearchService;

	@Autowired
	private UserHeadService userHeadService;
	
	@Autowired
	private OperationSearchService operationSearchService;
	
	@Autowired
	private PermissionSearchService permissionSearchService;
	
	@Autowired
	private OrganizationSearchService organizationSearchService;
	
	@Autowired
	private PermissionResourceRelService permissionResourceRelService;
	
//	@Autowired
//	private Organization
	
	
	@Value("${stateless.protocol}") 
	private String superAdminProcol; 
	
	@Value("${stateless.protocol.code}") 
	private String superAdminProcolCode; 
	
	@Value("${open.userloginverifycode}") 
	private boolean openuserloginverifycode;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;

	@Autowired
	private Cluster cluster;
	
	/**
	 * 用户登录接口，登录成功后将用户信息和用户权限信息存入session，并且保存用户登录日志
	 * 
	 * @param userLoginEntity
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@Info("用户登录接口，登录成功后将用户信息和用户权限信息存入session，并且保存用户登录日志")
	@RequestMapping("/UserLogin")
	@ResponseBody
	@ApiOperation(value="用户登录接口，登录成功后将用户信息和用户权限信息存入session，并且保存用户登录日志",response=Result.class,httpMethod="POST")
	public Result UserLogin(UserLoginEntity userLoginEntity, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, String verifyCode) {
		Result result = new Result();
		try {
			String username = userLoginEntity.getUserAccount();
			String password = userLoginEntity.getUserPassword();
			if (StringUtil.checkNull(username)||StringUtil.checkNull(password)) {
				throw new LoginException(ResultConstant.NULL_ATTRIBUTE);
			}
			if(openuserloginverifycode){
				if (!checkVerifyCode(session, verifyCode)) {
					throw new LoginException(ResultConstant.VERITYCODE_ERROE);
				}
			}
			if (!username.equals(superAdminProcol)) {
				userLoginEntity.setUserPassword(null);
				List<UserLoginEntity> list = userLoginService.find(userLoginEntity);
				if (list==null||list.size()<1||list.size()>1) {
					throw new NullAttributeException(ResultConstant.USER_UNKNOW_USERNAME);
				}
				userLoginEntity = list.get(0);
				if (!userLoginEntity.getUserPassword().equals(password)) {
					throw new NullAttributeException(ResultConstant.USER_PASSWORD_ERROR);
				}
				LoginData loginData = new LoginData();
				String userId = userLoginEntity.getUserId();
				UserEntity userEntity = new UserEntity();
				userEntity.setUserId(userId);
				List<UserEntity> userEntities = new ArrayList<>();
				userEntities= userSearchService.find(userEntity);
				String userNames = userEntities.get(0).getUserName();
				userLoginEntity.setUserName(userNames);
				/**
				 * 生成用户身份信息放入会话中
				 */
				userLoginEntity.setUserPassword("");
				loginData.setUserEntity(userLoginEntity);
				/**
				 * 生成用户操作权限信息放入会话中
				 */
				loginData = userAuth(session, loginData, userId);
				UserOrganizationRelEntity userOrganizationRelEntity = new UserOrganizationRelEntity();
				userOrganizationRelEntity.setUserId(userId);
				UserOrganizationRelEntity userOrganizationRelEntity2 = this.userOrganizationRelService.find(userOrganizationRelEntity).get(0);
				loginData.getUserEntity().setOrganId(userOrganizationRelEntity2.getOrganId());
				/**
				 * 保存用户登录日志
				 */
				UserLoginLogEntity userLoginLogEntity = new UserLoginLogEntity();
				userLoginLogEntity.setUserId(userId);
				userLoginLogEntity.setLoginUser(username+userNames);
				userLoginLogEntity.setUserLoginLogId(PrimaryKeyUtil.create());
				this.userLoginLogService.save(userLoginLogEntity, request, response, session);
				result.setStatus(ResultConstant.SUCCESS_STATU);
				result.setMessage(ResultConstant.LOGIN_SUCCESS);
				/**
				 * 查询用户头像信息
				 */
				UserHeadEntity userHeadEntity = new UserHeadEntity();
				userHeadEntity.setUserId(userId);
				List<UserHeadEntity> userHeadlist = this.userHeadService.find(userHeadEntity);
				if (null != userHeadlist && userHeadlist.size() > 0) {
					UserHeadEntity userHeadEntity2 = userHeadlist.get(0);
					String address = userHeadEntity2.getUserHeadAddress();
					address = address.substring(address.lastIndexOf("static"));
					address = address.substring(6);
					userHeadEntity2.setUserHeadAddress(address);
					loginData.setUserHeadEntity(userHeadEntity2);
				}
				/**
				 * 将用户身份信息及权限信息返给前端
				 */
				OrganizationEntity organizationEntity = this.organizationSearchService.findByUser(userId);
				loginData.setOrganizationEntity(organizationEntity);
				result.setData(loginData);
				/**
				 * 收集用户信息
				 */
				Map<String, Object> map = new HashMap<>();
				map.put(ResultConstant.USER_NAME, userNames);
				map.put(ResultConstant.USER_ID, userLoginEntity.getUserId());
				map.put(ResultConstant.ORGAN_ID, organizationEntity.getOrganId());
				map.put(ResultConstant.ORGAN_NAME, organizationEntity.getOrganName());
				map.put(ResultConstant.USER_ACCOUNT, userLoginEntity.getUserAccount());
				map.put(ResultConstant.USER_PHON, userLoginEntity.getUserTelnumber());
				map.put(ResultConstant.ORGAN_CODE, organizationEntity.getOrganCode());
				return generateToken(map, result);
				
			}else {
				if (!password.equals(MD5Encrypt.encrypt(superAdminProcolCode))) {
					throw new NullAttributeException(ResultConstant.USER_PASSWORD_ERROR);
				}else {
					LoginData loginData = new LoginData();
					UserEntity userEntity = new UserEntity();
					userEntity.setUserId(superAdminProcolCode);
					userEntity.setUserAccount("Admin");
					userEntity.setUserName("中科软技术支撑团队");
					loginData.setUserEntity(userEntity);
					loginData = userAuth(session, loginData, superAdminProcolCode);
					/**
					 * 生成用户基本信息
					 */
					OrganizationEntity organizationEntity = new OrganizationEntity();
					organizationEntity.setOrganName("中科软技术支撑团队");
					loginData.setOrganizationEntity(organizationEntity);
					result.setData(loginData);
					result.setStatus(ResultConstant.SUCCESS_STATU);
					result.setMessage(ResultConstant.LOGIN_SUCCESS);
					/**
					 * 生成用户操作权限信息放入会话中
					 */
					loginData = userAuth(session, loginData, superAdminProcolCode);
					/**
					 * 收集用户信息
					 */
					Map<String, Object> map = new HashMap<>();
					map.put(ResultConstant.USER_NAME, "中科软技术支撑团队");
					map.put(ResultConstant.USER_ID, superAdminProcolCode);
					map.put(ResultConstant.ORGAN_ID, superAdminProcolCode);
					map.put(ResultConstant.USER_ACCOUNT, "中科软技术支撑团队");
					return generateToken(map, result);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 用户验证码检查
	 * 
	 * @param session
	 * @param verityCode
	 * @return
	 */
	private boolean checkVerifyCode(HttpSession session, String verityCode) {

		String systemCode = (String) session.getAttribute(LogConstant.SESSION_LOGIN_VERITYCODE);
		if (StringUtil.checkNull(systemCode) || StringUtil.checkNull(verityCode)) {
			return false;
		}
		if (systemCode.equals(verityCode.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * 生成token并且返回result
	 * @throws Exception 
	 */
	private Result generateToken(Map<String, Object> map,Result result) throws Exception{
		String jwt = javaWebToken.createToken(javaWebToken.initHeader(), map);
		result.setToken(jwt);
		return result;
	}

	/**
	 * 用户登录验证码生成功能
	 * 
	 * @param request
	 * @param response
	 */
	@Info("用户登录验证码生成功能")
	@RequestMapping(value="/UserLoginVerifyCode",method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	@ApiOperation(value="用户登录验证码生成功能",response=Result.class,httpMethod="POST")
	public Result UserLoginVerifyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
//			System.out.println("验证码已经生成!");
			response.setStatus(200);
			this.verifyCodeService.getVerifyCode(request, response, LogConstant.SESSION_LOGIN_VERITYCODE);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.CREATE_VERITYCODE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.CREATE_VERITYCODE_FAIL);
		}
	}
	
//	private LoginData userAuth (HttpSession session,LoginData loginData, String userId) throws Exception {
//		List<PermissionEntity> permissionEntities = new ArrayList<>();
//		List<PermissionEntity> temp = new ArrayList<>();
//		if (userId.equals(superAdminProcolCode)) {
//			List<PermissionEntity> list = new ArrayList<>();
//			PermissionEntityVO permissionEntity = new PermissionEntityVO();
//			list = this.permissionSearchService.find(permissionEntity);
//			temp = list;
//		}else {
//			List<PermissionEntity> organPermissionEntities = new ArrayList<>();
////			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
////			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
//			organPermissionEntities = this.userLoginService.findUserPermission(userId);
////			userPermissionEntities.addAll(organPermissionEntities);
//			temp = organPermissionEntities;
//		}
//		List<String> ids = new ArrayList<>();
//		permissionEntities = new ArrayList<>();
//		if (null!=temp&&temp.size()>0) {
//			for (PermissionEntity permissionEntity:temp) {
//				if (!ids.contains(permissionEntity.getPermissionId())) {
//					ids.add(permissionEntity.getPermissionId());
//					permissionEntities.add(permissionEntity);
//				}
//			}
//		}
//		if(null!=session) {
//			session.setAttribute(LogConstant.SESSION_USER_PERMISSION_INFO, permissionEntities);
//		}
//		if (ListUtil.checkNull(ids)) {
//			if (null != session) {
//				session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO, new ArrayList<>());
//			}
//			loginData.setOperationEntities(new ArrayList<>());
//		}else {
//			if(userId.equals(superAdminProcolCode)) {
//				List<OperationEntity> temps = this.operationSearchService.find(new OperationEntity());
//				if(null!=session) {
//					session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO,
//							temps);
//				}
//				loginData.setOperationEntities(temps);
//			}else{
//				List<OperationEntity> operationEntities = new ArrayList<>();
//				operationEntities = this.operationSearchService.findByPermissionId(ids);
//				if(null!=session){
//					session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO,
//							operationEntities==null ?
//									new ArrayList<>():operationEntities);
//				}
//				loginData.setOperationEntities(operationEntities);
//			}
//		}
//		return loginData;
//	}
	
	/**
	 * 弃用版本2.0.0.C
	 * @param request
	 * @param response
	 * @param ct
	 * @return
	 */
//	private LoginData userInfo(HttpSession session,String userId,String username,UserLoginEntity userLoginEntity,LoginData loginData) {
//		UserEntity userEntity = new UserEntity();
//		userEntity.setUserId(userId);
//		List<UserEntity> temp = new ArrayList<>();
//		try {
//			temp = this.userSearchService.find(userEntity);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if(!ListUtil.checkNull(temp)) {
//			userEntity = temp.get(0);
//		}
//		session.setAttribute(LogConstant.SESSION_USER_INFO, userEntity);
//		loginData.setUserEntity(userEntity);
//		return loginData;
//	}
	@Info("用户登录接口，登录成功后将用户信息和用户权限信息存入session，并且保存用户登录日志")
	@RequestMapping("/UserLoginForChild")
	@ResponseBody
	@ApiOperation(value="用户登录接口，登录成功后将用户信息和用户权限信息存入session，并且保存用户登录日志",response=Result.class,httpMethod="POST")
	public Object UserLoginForChild(HttpServletRequest request, HttpServletResponse response ,String ct) {
		String tokens;
		JSONObject resultJSON = new JSONObject();
		if(!StringUtil.checkNull(ct)) {
			try {
				resultJSON = initJsonP(ct);
				resultJSON = cluster.dealWithJsonObject(resultJSON);
				return resultJSON;
			} catch (Exception e) {
				e.printStackTrace();
				Result result = new Result(ResultConstant.FAIL_STATU, ResultConstant.SERVICE_ERROR);
				return result;
			}
		}else{
			response.setContentType("text/plain");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			String jsonpCallback = request.getParameter("callback");// 客户端请求参数
			try {
				PrintWriter out = response.getWriter();
//				Map<String, String> map = new HashMap<String, String>();
//				map.put("result", "content");
				tokens = request.getParameter("authorization");
				Map<String, Object> param = new HashMap<>();
				param.put("ct", tokens);
				Object result = cluster.getParentResult("UserLoginForChild", param,analysis(request));
				if(null!=result) {
					resultJSON = JSONObject.fromObject(result);
				}else{
					resultJSON = this.initJsonP(tokens);
				}
				String temp = jsonpCallback + "(" + resultJSON.toString() + ")";
//				System.out.println(temp);
				out.println(temp);// 返回jsonp格式数据
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private JSONObject initJsonP(String tokens) throws Exception{
		JSONObject resultJSON = new JSONObject();
		Result result = new Result();
		Map<String, Object> userInfo = this.javaWebToken.parserJavaWebToken(tokens);
		String username = (String) userInfo.get(ResultConstant.USER_NAME);
		String userid = (String) userInfo.get(ResultConstant.USER_ID);
		String userAccount = (String) userInfo.get(ResultConstant.USER_ACCOUNT);
		String phone = (String) userInfo.get(ResultConstant.USER_PHON);
		String organId = (String) userInfo.get(ResultConstant.ORGAN_ID);
		String organName = (String) userInfo.get(ResultConstant.ORGAN_NAME);
		String organCode = (String) userInfo.get(ResultConstant.ORGAN_CODE);
		UserLoginEntity userLoginEntity = new UserLoginEntity();
		userLoginEntity.setUserId(userid);
		userLoginEntity.setUserName(username);
		userLoginEntity.setUserAccount(userAccount);
		userLoginEntity.setUserTelnumber(phone);
		if (!username.equals(superAdminProcol)) {
			
			LoginData loginData = new LoginData();
			/**
			 * 生成用户身份信息放入会话中
			 */
			loginData.setUserEntity(userLoginEntity);;
			/**
			 * 生成用户操作权限信息放入会话中
			 */
			loginData = userAuth(null, loginData, userid);
			
			result.setStatus(ResultConstant.SUCCESS_STATU);
			result.setMessage(ResultConstant.LOGIN_SUCCESS);
			/**
			 * 查询用户头像信息
			 */
			UserHeadEntity userHeadEntity = new UserHeadEntity();
			userHeadEntity.setUserId(userid);
			List<UserHeadEntity> userHeadlist = this.userHeadService.find(userHeadEntity);
			if (null != userHeadlist && userHeadlist.size() > 0) {
				UserHeadEntity userHeadEntity2 = userHeadlist.get(0);
				loginData.setUserHeadEntity(userHeadEntity2);
			}
			/**
			 * 将用户身份信息及权限信息返给前端
			 */
			OrganizationEntity organizationEntity = new OrganizationEntity();
			organizationEntity.setOrganId(organId);
			organizationEntity.setOrganName(organName);
			organizationEntity.setOrganCode(organCode);
			loginData.setOrganizationEntity(organizationEntity);
			result.setData(loginData);
			resultJSON = JSONObject.fromObject(result); // 根据需要拼装json
		} else {
			LoginData loginData = new LoginData();
			UserEntity userEntity = new UserEntity();
			userEntity.setUserId(superAdminProcolCode);
			userEntity.setUserAccount("Admin");
			userEntity.setUserName("中科软技术支撑团队");
			loginData.setUserEntity(userEntity);
			loginData = userAuth(null, loginData, superAdminProcolCode);
			OrganizationEntity organizationEntity = new OrganizationEntity();
			organizationEntity.setOrganName(organName);
			organizationEntity.setOrganId(organId);
			loginData.setOrganizationEntity(organizationEntity);
			result.setData(loginData);
			result.setStatus(ResultConstant.SUCCESS_STATU);
			result.setMessage(ResultConstant.LOGIN_SUCCESS);
			resultJSON = JSONObject.fromObject(result); // 根据需要拼装json
		}
		return resultJSON;
	}

	private LoginData userAuth (HttpSession session,LoginData loginData, String userId) throws Exception {
		List<PermissionEntity> permissionEntities = new ArrayList<>();
		List<PermissionEntity> temp = new ArrayList<>();
		if (userId.equals(superAdminProcolCode)) {
			List<PermissionEntity> list = new ArrayList<>();
			PermissionEntity permissionEntity = new PermissionEntity();
			list = this.permissionSearchService.find(permissionEntity);
			temp = list;
		}else {
//			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
			//旧获取全部许可
//			userPermissionEntities = this.userLoginService.findUserPermission(userId);
			//新
            userPermissionEntities=userSearchService.getPermission(userId);
			temp = userPermissionEntities;
		}
		List<String> ids = new ArrayList<>();
		permissionEntities = new ArrayList<>();
		if (null!=temp&&temp.size()>0) {
			for (PermissionEntity permissionEntity:temp) {
				if (!ids.contains(permissionEntity.getPermissionId())) {
					ids.add(permissionEntity.getPermissionId());
					permissionEntities.add(permissionEntity);
				}
			}
		}
		if(null!=session) {
			session.setAttribute(LogConstant.SESSION_USER_PERMISSION_INFO, permissionEntities);
		}
		if (ListUtil.checkNull(ids)) {
			if(userId.equals(superAdminProcolCode)) {
                List<OperationEntity> temps = this.operationSearchService.find(new OperationEntity());
                if(null!=session) {
                    session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO,
                            temps);
                }
                loginData.setOperationEntities(temps);
            }else{
           	 if (null != session) {
                    session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO, new ArrayList<>());
                }
                loginData.setOperationEntities(new ArrayList<>());
            }
		}else {
			if(userId.equals(superAdminProcolCode)) {
				List<OperationEntity> temps = this.operationSearchService.find(new OperationEntity());
				if(null!=session) {
					session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO,
							temps);
				}
				loginData.setOperationEntities(temps);
			}else{
				List<OperationEntity> operationEntities = new ArrayList<>();
				//旧获取操作
//				operationEntities = this.operationSearchService.findByPermissionId(ids);
				//新
				operationEntities = getOperation(permissionEntities);

				if(null!=session){
					session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO,
							operationEntities==null ?
									new ArrayList<>():operationEntities);
				}
				loginData.setOperationEntities(operationEntities);
			}
		}
		return loginData;
	}

	@SuppressWarnings("unchecked")
	private List<OperationEntity> getOperation(List<PermissionEntity> permissionEntities)throws Exception{
		List<OperationEntity> operationEntities = new ArrayList<>();
		List<PermissionEntity> currents = new ArrayList<>();
		List<PermissionEntity> specials = new ArrayList<>();
		List<PermissionEntity> cases = new ArrayList<>();
		if(ListUtil.checkNull(permissionEntities)){
			return operationEntities;
		}
		permissionEntities.forEach(permission->{
			if(permission.getPermissionSymbol()==0){
				currents.add(permission);
			}else if(permission.getPermissionSymbol()==1){
				specials.add(permission);
			}else if(permission.getPermissionSymbol()==2){
				cases.add(permission);
			}
		});
		List<String> ids=new ArrayList<>();
		if(!ListUtil.checkNull(specials)){
			for(PermissionEntity special:specials){
				ids.add(special.getPermissionId());
			}
			if(!ListUtil.checkNull(currents)){
				List<String> intersection=new ArrayList<>();
				intersection.addAll(ids);
				List<String> idss=new ArrayList<>();
				for(PermissionEntity current:currents){
					idss.add(current.getPermissionId());
				}
				for(PermissionEntity currentt:currents){
					String currentId=currentt.getPermissionId();
					String curreId=currentt.getResourceId();
					boolean boo = true;
					for(PermissionEntity speciall:specials){
						String spereId=speciall.getResourceId();
						if(spereId.equals(curreId)){
							boo=false;
							break;
						}
					}
					if(boo){
						intersection.add(currentId);
					}
				}
				operationEntities=this.operationSearchService.findByPermissionId(intersection);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}else{
				operationEntities=this.operationSearchService.findByPermissionId(ids);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}
		}else{
			if(!ListUtil.checkNull(currents)){
				List<String> idss=new ArrayList<>();
				for(PermissionEntity current:currents){
					idss.add(current.getPermissionId());
				}
				operationEntities=this.operationSearchService.findByPermissionId(idss);
				operationEntities=operationEntities.stream().collect(collectingAndThen(
						toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
				);
			}
		}
		if(!ListUtil.checkNull(cases)){
			for(PermissionEntity pe:cases){
				ids.add(pe.getPermissionId());
			}
			List<OperationEntity> casesOperationEntities=this.operationSearchService.findByPermissionId(ids);
			operationEntities.addAll(casesOperationEntities);
			operationEntities=operationEntities.stream().collect(collectingAndThen(
					toCollection(() -> new TreeSet<>(comparing(OperationEntity::getOperationId))), ArrayList::new)
			);
		}
		return operationEntities;
	}

}