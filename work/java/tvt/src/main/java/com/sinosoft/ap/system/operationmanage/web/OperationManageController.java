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
import com.sinosoft.ap.system.operationmanage.service.OperationManageService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="资源管理",description="资源管理")
public class OperationManageController {

	@Autowired
	private OperationManageService operationManageService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 根据操作Id删除指定操作
	 * operatinId不能为空
	 * @param operationId
	 * @return
	 */
	@Info("根据操作Id删除指定操作")
	@RequestMapping("/removeOperation")
	@ResponseBody
	@ApiOperation(value="根据操作Id删除指定操作",response=Result.class,httpMethod="POST")
	public Object removeOperation(String operationId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("operationId", operationId);
		Object result = cluster.getParentResult("removeOperation", param,analysis(request));
		if(null!=result) {
			return result;
		}
		OperationEntity entity = new OperationEntity();
	    entity.setOperationId(operationId);
		try {
			operationManageService.remove(entity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增资源操作，成功后返回新增资源信息
	 *operationCode不能和已有重复
	 * @param operationEntity
	 * @return
	 */
	@Info("新增资源操作，成功后返回新增资源信息")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveOperation")
	@ResponseBody
	@ApiOperation(value="新增资源操作，成功后返回新增资源信息",response=Result.class,httpMethod="POST")
	public Object saveOperation(OperationEntity operationEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(operationEntity);
		Object result = cluster.getParentResult("saveOperation", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU,
					ResultConstant.INSERT_SUCCESS,
					operationManageService.save(operationEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 根据给定OperationEntity修改操作
	 * 其中operaId不能为空
	 * @param operationEntity
	 * @return
	 */
	@Info("根据给定OperationEntity修改操作")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyOperation")
	@ResponseBody
	@ApiOperation(value="根据给定OperationEntity修改操作",response=Result.class,httpMethod="POST")
	public Object modifyOperation(OperationEntity operationEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(operationEntity);
		Object result = cluster.getParentResult("modifyOperation", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			operationManageService.modify(operationEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}	