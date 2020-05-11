package com.sinosoft.ap.system.rolemanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.service.RoleSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ap-system")
@Api(value="角色查询管理",description="角色查询管理")
public class RoleSearchController {

	@Autowired
	private RoleSearchService roleSearchService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 查询所有角色
	 * @return
	 */
	@Info("查询所有角色")
	@RequestMapping("/findRole")
	@ResponseBody
	@ApiOperation(value="查询所有角色",response=Result.class,httpMethod="POST")
	public Object findRole(ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("test", "123");
		Object result = cluster.getParentResult("findRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		RoleEntity entity = new RoleEntity();
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					roleSearchService.find(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 根据用户查询相关角色
	 * @param userId
	 * @return
	 */
	@Info("根据用户查询相关角色")
	@RequestMapping("/findUserRole")
	@ResponseBody
	@ApiOperation(value="根据用户查询相关角色",response=Result.class,httpMethod="POST")
	public Object findUserRole(String userId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object result = cluster.getParentResult("findUserRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					roleSearchService.findUserRole(userId));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

}
