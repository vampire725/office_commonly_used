//package com.sinosoft.ap.system.usersettings.web;
//
//import java.io.File;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//
//import com.sinosoft.ap.common.aop.annotation.Info;
//import com.sinosoft.ap.common.constant.LogConstant;
//import com.sinosoft.ap.common.exception.NullAttributeException;
//import com.sinosoft.ap.component.uploadfile.service.FileUploadService;
//import com.sinosoft.ap.system.usermanage.domain.UserEntity;
//import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;
//import com.sinosoft.ap.system.usersettings.service.UserHeadService;
//import com.sinosoft.ap.util.cluster.web.Cluster;
//import com.sinosoft.ap.util.file.FileUtil;
//import com.sinosoft.ap.util.id.PrimaryKeyUtil;
//import com.sinosoft.ap.util.image.ImageUtil;
//import com.sinosoft.ap.util.result.Result;
//import com.sinosoft.ap.util.result.ResultConstant;
//import com.sinosoft.ap.util.string.StringUtil;
//import com.sinosoft.ap.util.token.JavaWebToken;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("ap-system")
//@Api(value="用户头像管理",description="用户头像管理")
//public class UserHeadController {
//	
//	
//	@Autowired
//	private JavaWebToken javaWebToken;
//	
//	@Autowired
//	private FileUploadService fileUploadService;
//	
//	@Autowired
//	private UserHeadService userHeadService;
//	
////	 @Value("${head.paht}")
//	private String  headPath;
//
//	@Autowired
//	private Cluster cluster;
//
//	@Value("${stateless.protocol}")
//	private String superAdminProcol;
//
//	@Value("${stateless.protocol.code}")
//	private String superAdminProcolCode;
//
//	@Value("${open.userloginverifycode}")
//	private boolean openuserloginverifycode;
//
//	@Value("${ap.cluster.token.key}")
//	private String tokenKey;
//	/**
//	 * 头像上传+剪切
//	 * @param file
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping("/uploadUserHead")
//	@ResponseBody
//	@ApiOperation(value="头像上传+剪切",response=Result.class,httpMethod="POST")
//	public  Result uploadUserHead(HttpServletRequest req,
//		      MultipartHttpServletRequest multiReq,HttpSession session,
//		      String toCropImgX, String toCropImgY,
//		      String toCropImgW, String toCropImgH,String userIds)throws Exception{
//		try {
//			String separator = System.getProperty("file.separator");
//			UserEntity uEntity = (UserEntity) session.getAttribute(LogConstant.SESSION_USER_INFO);
//			String userId = "";
//			if(null==uEntity) {
//				userId = userIds;
//			}else {
//				String temp = uEntity.getUserId();
//				if(!StringUtil.checkNull(temp)) {
//					userId = temp;
//				}
//			}
//			UserHeadEntity userHeadEntity1 = new UserHeadEntity();
//			userHeadEntity1.setUserId(userId);
//			List<UserHeadEntity> list = new ArrayList<>();
//			if(null!=list&&list.size()>0) {
//				FileUtil.deleteLocalFile(list.get(0).getUserHeadAddress());
//			}
//			this.userHeadService.remove(userHeadEntity1);
//			MultipartFile file = multiReq.getFile("files");
//			if (file.isEmpty()) {
//				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
//			}
//			headPath = headPath.replace(".", separator);
//			String path = System.getProperty("user.dir");
//			path = path.endsWith("bin")?path.replaceAll("bin", ""):path;
//			path = path.endsWith(separator)? path:path+separator;
//			path = path+headPath;
//			//@SuppressWarnings("deprecation")
//			//String filePath = this.fileUploadService.uploadFileToLocal(file,path);
//			String filePath = this.fileUploadService.uploadFileToLocal(file, path, null);
//			System.out.println(filePath);
//			File file2 = new File(filePath);
//		    int imX = castToInteger(toCropImgX);
//		    int imY = castToInteger(toCropImgY);
//		    int imW = castToInteger(toCropImgW);
//		    int imH = castToInteger(toCropImgH);
//			String result =  ImageUtil.cutImage(file2, path, imX, imY, imX+imW, imY+imH);
//			FileUtil.deleteLocalFile(filePath);
//			UserHeadEntity userHeadEntity = new UserHeadEntity();
//			userHeadEntity.setUserHeadId(PrimaryKeyUtil.create());
//			userHeadEntity.setUserHeadAddress(result);
//			userHeadEntity.setUserId(userId);
//			userHeadService.save(userHeadEntity);
//			result = result.substring(result.lastIndexOf("static"));
//			System.out.println(result);
//			result = result.substring(6);
//			System.out.println(result);
//			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS,result);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
//		}
//	}
//	
//	
//	@RequestMapping("/saveUserHeadInfo")
//	@ResponseBody
//	@ApiOperation(value="保存用户头像",response=Result.class,httpMethod="POST")
//	public  Result saveUserHeadInfo(UserHeadEntity userHeadEntity)throws Exception{
//		try {
//			UserHeadEntity userHeadEntity1 = new UserHeadEntity();
//			userHeadEntity1.setUserId(userHeadEntity.getUserId());
//			List<UserHeadEntity> list = new ArrayList<>();
//			if(null!=list&&list.size()>0) {
//				FileUtil.deleteLocalFile(list.get(0).getUserHeadAddress());
//			}
//			this.userHeadService.remove(userHeadEntity1);
//			userHeadService.save(userHeadEntity);
//			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPLOAD_FILE_SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPLOAD_FILE_FAIL);
//		}
//	}
//	private Integer castToInteger(String str) {
//		Double a = Double.parseDouble(str);
//		return new BigDecimal(a).setScale(0, BigDecimal.ROUND_HALF_EVEN).intValue();
//	}
//	
//	/**
//	 * 查询用户头像信息
//	 * @param session
//	 * @return
//	 */
//	@Info("查询用户头像信息")
//	@RequestMapping("/findUserHeadImage")
//	@ResponseBody
//	@ApiOperation(value="查询用户头像信息",response=Result.class,httpMethod="POST")
//	public Object findUserHeadImage( ServletRequest request, ServletResponse response,String userIds ) {
//		String userId  = "";
//		if(!StringUtil.checkNull(userIds)) {
//			userId = userIds;
//		}else{
//			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//			String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
//			Map<String, Object> userInfo = new HashMap<>();
//			try {
//				userInfo = javaWebToken.parserJavaWebToken(token, tokenKey);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new Result(ResultConstant.FAIL_STATU, e.getMessage());
//			}
//			userId = (String) userInfo.get(ResultConstant.USER_ID);			
//			Map<String, Object> param = new HashMap<>();
//			param.put("userIds", userIds);
//			Object result = cluster.getParentResult("findUserHeadImage", param);
//			if(null!=result) {
//				return result;
//			}
//		}
//		UserHeadEntity userHeadEntity = new UserHeadEntity();
//		userHeadEntity.setUserId(userId);
//		try {
//			List<UserHeadEntity> list = this.userHeadService.find(userHeadEntity);
//			if(null==list||list.size()<1) {
//				throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
//			}
//			UserHeadEntity userHeadEntity2 = new UserHeadEntity();
//			userHeadEntity2 = list.get(0);
//			String headaddress = userHeadEntity2.getUserHeadAddress();
//			headaddress = headaddress.substring(headaddress.lastIndexOf("static"));
//			headaddress = headaddress.substring(6);
//			userHeadEntity2.setUserHeadAddress(headaddress);
//			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS,list.get(0) );
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
//		}
//	}
//	
//	@RequestMapping("/removeUserHeadImage")
//	@ResponseBody
//	@ApiOperation(value="删除用户头像信息",response=Result.class,httpMethod="POST")
//	public Object removeUserHeadImage( ServletRequest request, ServletResponse response,String userIds ) {
//		String userId  = "";
//		if(!StringUtil.checkNull(userIds)) {
//			userId = userIds;
//			Map<String, Object> param = new HashMap<>();
//			param.put("userIds", userIds);
//			Object result = cluster.getParentResult("removeUserHeadImage", param);
//			if(null!=result) {
//				return result;
//			}
//		}else{
//			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//			String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
//			Map<String, Object> userInfo = new HashMap<>();
//			try {
//				userInfo = javaWebToken.parserJavaWebToken(token, tokenKey);
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new Result(ResultConstant.FAIL_STATU, e.getMessage());
//			}
//			userId = (String) userInfo.get(ResultConstant.USER_ID);			
//		}
//		UserHeadEntity userHeadEntity = new UserHeadEntity();
//		userHeadEntity.setUserId(userId);
//		try {
//			this.userHeadService.remove(userHeadEntity);
//			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
//		}
//	}
//	
//	
//}
