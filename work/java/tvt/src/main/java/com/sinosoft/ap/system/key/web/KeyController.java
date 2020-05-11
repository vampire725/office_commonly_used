package com.sinosoft.ap.system.key.web;

import io.swagger.annotations.ApiOperation;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@RequestMapping("ap-system")
public class KeyController {
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
	
	@Value("${stateless.protocol:admin}")
	private String superAdminProcol; 
	
	@Value("${stateless.protocol.code}") 
	private String superAdminProcolCode; 
	
	@Value("${open.userloginverifycode}") 
	private boolean openuserloginverifycode;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;

	@Autowired
	private Cluster cluster;
	
	private Map<String,String> getKeyInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String,String> keyInfo = new HashMap<String,String>();
		Cookie[] cookies = request.getCookies();
	 	System.out.println("开始获取key信息==========");
	 	System.out.println("====用户key登录接口获取key信息======");
	    if(cookies == null)
	        cookies = new Cookie[0];
	    for(int i = 0; i < cookies.length;i++){
	    	String value = "";
	        Cookie cookie = cookies[i];
	        if("KOAL_CERT_CN".equals(cookie.getName())){
	        	value  = URLDecoder.decode(cookie.getValue(), "GBK"); 
	        	System.out.println(value);
	        	keyInfo.put("userId", value);//用户标识
	        }else if("KOAL_CERT_G".equals(cookie.getName())){
	            value  = URLDecoder.decode(cookie.getValue(), "GBK"); 
	        	System.out.println(value);
	            keyInfo.put("userName", value);//用户姓名
	        }
	    }
	    return keyInfo;
	}
	
	public static void main(String args[]) throws Exception{
		String ss = "%e3%c9%bd%a8%c0%fb";
		String temp = new String(ss.getBytes("gb2312"),"utf-8");
		   String keyWord = URLDecoder.decode(ss, "GBK");  
		System.out.println(keyWord);
	}
	/**
	 * 登录key测试获取
	 * @param request
	 * @param response
	 * @param session
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/keyLogin")
	@ResponseBody
	public Result keyLogin(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception{
		   Map<String, String> keyInfo = getKeyInfo(request,response);
		   Result result = new Result();
			try {
				UserLoginEntity userLoginEntity = new UserLoginEntity();
				String snKey = keyInfo.get("userId");
				if (StringUtil.checkNull(snKey)) {
					result.setMessage("未获取到key信息，请确认key插入电脑");
					result.setStatus(2);
					return result;
				}
				//userLoginEntity.setUserId(snKey);
				userLoginEntity.setUserAccount(snKey);
				List<UserLoginEntity> list = userLoginService.find(userLoginEntity);
				if (list==null||list.size()<1||list.size()>1) {
					result.setMessage("该key未绑定用户。请联系管理员绑定用户");
					result.setStatus(-1);
					return result;
				}
				userLoginEntity = list.get(0);
				LoginData loginData = new LoginData();
				String userId = userLoginEntity.getUserId();
				UserEntity userEntity = new UserEntity();
				userEntity.setUserId(userId);
				List<UserEntity> userEntities = new ArrayList<>();
				userEntities= userSearchService.find(userEntity);
				/*if(!macAddr.equals(userEntities.get(0).getMacAddr())){
					throw new NullAttributeException(ResultConstant.USER_MAC_ERROR);
				}*/
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
				userLoginLogEntity.setLoginUser(userLoginEntity.getUserName());
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
	private LoginData userAuth (HttpSession session,LoginData loginData, String userId) throws Exception {
		List<PermissionEntity> permissionEntities = new ArrayList<>();
		List<PermissionEntity> temp = new ArrayList<>();
		if (userId.equals(superAdminProcolCode)) {
			List<PermissionEntity> list = new ArrayList<>();
			PermissionEntity permissionEntity = new PermissionEntity();
			list = this.permissionSearchService.find(permissionEntity);
			temp = list;
		}else {
			List<PermissionEntity> organPermissionEntities = new ArrayList<>();
//			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
//			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
			organPermissionEntities = this.userLoginService.findUserPermission(userId);
//			userPermissionEntities.addAll(organPermissionEntities);
			temp = organPermissionEntities;
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
			if (null != session) {
				session.setAttribute(LogConstant.SESSION_USER_OPERATION_INFO, new ArrayList<>());
			}
			loginData.setOperationEntities(new ArrayList<>());
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
				operationEntities = this.operationSearchService.findByPermissionId(ids);
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
}
