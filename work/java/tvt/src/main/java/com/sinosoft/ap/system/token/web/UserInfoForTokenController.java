package com.sinosoft.ap.system.token.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.system.token.domain.UserInfoForToken;
import com.sinosoft.ap.system.token.service.UserInfoForTokenService;
import com.sinosoft.ap.util.result.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RequestMapping("ap-system")
@RestController
@Api(value="令牌管理",description="令牌管理")
public class UserInfoForTokenController {

	@Autowired
	private UserInfoForTokenService userInfoForTokenService;
	
	@RequestMapping("deleteToken")
	@ApiOperation(value="删除令牌",response=Result.class,httpMethod="POST")
	public void deleteToken(String token) throws Exception {
		this.userInfoForTokenService.delete(token);
	}

	@RequestMapping("updataToken")
	@ApiOperation(value="更新令牌",response=Result.class,httpMethod="POST")
	public void updataToken(UserInfoForToken userInfoForToken) throws Exception {
		this.userInfoForTokenService.update(userInfoForToken);
	}

	@RequestMapping("selectToken")
	@ApiOperation(value="查询令牌",response=Result.class,httpMethod="POST")
	public UserInfoForToken selectToken(String token) throws Exception {
		
		return this.userInfoForTokenService.select(token);
	}

	@RequestMapping("insertToken")
	@ApiOperation(value="新增令牌",response=Result.class,httpMethod="POST")
	public UserInfoForToken insertToken(UserInfoForToken userInfoForToken) throws Exception {
		this.userInfoForTokenService.insert(userInfoForToken);;
		return userInfoForToken;
	}
}
