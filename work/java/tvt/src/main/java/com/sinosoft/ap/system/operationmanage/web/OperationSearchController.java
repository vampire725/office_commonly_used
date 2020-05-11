package com.sinosoft.ap.system.operationmanage.web;

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
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/ap-system")
@Api(value="资源查询",description="资源查询")
public class OperationSearchController {
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	@Autowired
	private OperationSearchService operationSearchService;
	
	/**
	 * 根据资源ID查询操作接口
	 * resourceId不能为空
	 * @param resourceId
	 * @return
	 */
	@Info("根据资源ID查询操作接口")
	@RequestMapping("/findOperation")
	@ResponseBody
	@ApiOperation(value="根据资源ID查询操作接口",response=Result.class,httpMethod="POST")
	public Object findOperation(String resourceId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("resourceId", resourceId);
		Object result = cluster.getParentResult("findOperation", param,analysis(request));
		if(null!=result) {
			return result;
		}
		OperationEntity entity = new OperationEntity();
		try {
			entity.setResourceId(resourceId);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					operationSearchService.find(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 根据许可查询相关操作
	 * permissionId不能为空
	 * @param permissionId
	 * @return
	 */
	@Info("根据许可查询相关操作")
	@RequestMapping("/findOperationByPermission")
	@ResponseBody
	@ApiOperation(value="根据许可查询相关操作",response=Result.class,httpMethod="POST")
	public Object findOperationByPermission(String permissionId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("permissionId", permissionId);
		Object result = cluster.getParentResult("findOperationByPermission", param,analysis(request));
		if(null!=result) {
			return result;
		}
		OperationEntity entity = new OperationEntity();
		entity.setPermissionId(permissionId);
		try {
			entity.setPermissionId(permissionId);
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					operationSearchService.find(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

}
