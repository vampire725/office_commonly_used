package com.sinosoft.ap.system.usersettings.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.service.UserService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户信息管理",description="用户信息管理")
public class UserInfoController {
	
	@Autowired
	private JavaWebToken javaWebToken;

	@Autowired
	private UserService userService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${stateless.protocol}") 
	private String superAdminProcol; 
	
	@Value("${stateless.protocol.code}") 
	private String superAdminProcolCode; 
	
	@Value("${open.userloginverifycode}") 
	private boolean openuserloginverifycode;
	
	@InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
	/**
	 * 修改用户信息
	 * @param userEntity
	 * @param session
	 * @return
	 */
	@Info("修改用户信息")
	@RequestMapping("/modifyUserInfo")
	@ResponseBody
	@ApiOperation(value="修改用户信息",response=Result.class,httpMethod="POST")
	public Object modifyUserInfo( UserEntity userEntity, ServletRequest request, ServletResponse response , String token){
		String tokens  = "";
		String userId = "";
		if(!StringUtil.checkNull(token)) {
			tokens = token;
		}else{
		/*	Map<String, Object> param = new HashMap<>();
			param.put("token", token);
			Object result = cluster.getParentResult("modifyUserInfo", param,analysis(request));
			if(null!=result) {
				return result;
			}*/
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			tokens = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
		}
		Map<String, Object> userInfo = new HashMap<>();
		try {
			userInfo = javaWebToken.parserJavaWebToken(tokens);
		} catch (Exception e1) {
			e1.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU,e1.getMessage());
		}
		userId = (String) userInfo.get(ResultConstant.USER_ID);			
		userEntity.setUserAccount(null);
		userEntity.setUserId(userId);
		try {
			this.userService.modify(userEntity);
			userEntity.setUserAccount((String)userInfo.get(ResultConstant.USER_ACCOUNT));
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS,userEntity);
		} catch (Exception e) {
			return new Result(ResultConstant.FAIL_STATU,e.getMessage());
		}
	}
}
