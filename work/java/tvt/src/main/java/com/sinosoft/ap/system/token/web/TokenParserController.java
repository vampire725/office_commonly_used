package com.sinosoft.ap.system.token.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("ap-system")
@Api(value="解析用户token",description="解析用户token")
public class TokenParserController {

	@Autowired
	private JavaWebToken javaWebToken;
	
	@RequestMapping("parserJavaWebToken")
	@ApiOperation(value="解析用户token",httpMethod="POST")
	public Result parserJavaWebToken(String token) {
		if(StringUtil.checkNull(token)) {
			return new Result(ResultConstant.FAIL_STATU, "参数不存在");
		}else {
			try {
				Map<String, Object> userInfo = javaWebToken.parserJavaWebToken(token);
				return new Result(ResultConstant.SUCCESS_STATU, "解析成功", userInfo);
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(ResultConstant.FAIL_STATU, e.getMessage());
			}
		}
	}
}
