package com.sinosoft.ap.system.usersettings.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.system.loginmanage.domain.UserPasswordEntity;
import com.sinosoft.ap.system.loginmanage.service.UserPasswordService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户安全管理",description="用户安全管理")
public class UserSecurityController {
	
	@Autowired
	private JavaWebToken javaWebToken;
	
	@Autowired
	private UserPasswordService userPasswordService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${stateless.protocol}") 
	private String superAdminProcol; 
	
	@Value("${stateless.protocol.code}") 
	private String superAdminProcolCode; 
	
	@Value("${open.userloginverifycode}") 
	private boolean openuserloginverifycode;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 修改用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param session
	 * @return
	 */
	@RequestMapping("/settingUserPassword")
	@ResponseBody
	@ApiOperation(value="修改用户密码",response=Result.class,httpMethod="POST")
	public Object modifyUserPassword( String oldPassword,String newPassword,ServletRequest request, ServletResponse response ,String token){
		String tokens  = "";
		String userId = "";
		if(!StringUtil.checkNull(token)) {
			tokens = token;
		}else{
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			tokens = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
			Map<String, Object> param = new HashMap<>();
			param.put("token", token);
			param.put("oldPassword", oldPassword);
			param.put("newPassword", newPassword);
			Object result = cluster.getParentResult("settingUserPassword", param,analysis(httpServletRequest));
			if(null!=result) {
				return result;
			}
		}
		Map<String, Object> userInfo = new HashMap<>();
		try {
			userInfo = javaWebToken.parserJavaWebToken(tokens);
		} catch (Exception e1) {
			e1.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e1.getMessage());
		}
		userId = (String) userInfo.get(ResultConstant.USER_ID);	
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		if(StringUtil.checkNull(userId)){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
		}
		userPasswordEntity.setUserId(userId);
		try {
			List<UserPasswordEntity> userPasswordEntities = new ArrayList<>();
			userPasswordEntities = this.userPasswordService.find(userPasswordEntity);
			UserPasswordEntity userPasswordEntity1 = new UserPasswordEntity();
			userPasswordEntity1 = userPasswordEntities.get(0);
			if(userPasswordEntity1.getUserPassword().equals(oldPassword)){
				userPasswordEntity.setUserPassword(newPassword);
				this.userPasswordService.modify(userPasswordEntity);
			}else {
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}
