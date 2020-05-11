package com.sinosoft.ap.system.resourcemanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceIconEntity;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceOperationTree;
import com.sinosoft.ap.system.resourcemanage.service.ResService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="资源管理",description="资源管理")
public class ResourceManageController {

	@Autowired
	private ResService resService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	/**
	 * 查询资源
	 * @return
	 */
	@Info("查询资源")
	@RequestMapping("/findResource")
	@ApiOperation(value="查询资源",response=Result.class,httpMethod="POST")
	public Object findResource(ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("test", "123");
		Object result = cluster.getParentResult("findResource", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS, this.resService.findResourceIcon(null));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	private List<ResourceOperationTree> setType(List<ResourceOperationTree> list,String type) {
		if(list==null||list.size()<1) {
			return new ArrayList<>();
		}
		for(int i=0; i<list.size(); i++) {
			list.get(i).setType(type);
		}
		return list;
	}
	
	/**
	 * 查询组织操作树
	 * @return
	 */
	@Info("查询组织操作树")
	@RequestMapping("/findResourceOperationTree")
	@ApiOperation(value="查询组织操作树",response=Result.class,httpMethod="POST")
	public Object findResourceOperationTree(ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("test", "123");
		Object result = cluster.getParentResult("findResourceOperationTree", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			List<ResourceOperationTree> resourceTrees  = new ArrayList<>();
			resourceTrees = setType(this.resService.findResourceTree(), "Resource");
			List<ResourceOperationTree> operationTrees = new ArrayList<>();
			operationTrees = setType(this.resService.findOperationTree(), "Operation");
			resourceTrees.addAll(operationTrees);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS, resourceTrees);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 删除资源，同时删除资源图标,以及资源下所有操作
	 * 其中resourceId不能为空
	 * @param resourceId
	 * @return
	 */
	@Info("删除资源，同时删除资源图标,以及资源下所有操作")
	@RequestMapping("/removeResource")
	@ApiOperation(value="删除资源，同时删除资源图标,以及资源下所有操作",response=Result.class,httpMethod="POST")
	public Object removeResource(String resourceId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("resourceId", resourceId);
		Object result = cluster.getParentResult("removeResource", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			ResourceEntity resourceEntity = new ResourceEntity();
			resourceEntity.setResourceId(resourceId);
			this.resService.remove(resourceEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 保存资源，以及资源图标
	 * @param resourceEntity
	 * @param resourceIconEntity
	 * @return
	 */
	@Info("保存资源，以及资源图标")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveResource")
	@ApiOperation(value="保存资源，以及资源图标",response=Result.class,httpMethod="POST")
	public Object saveResource(ResourceEntity resourceEntity,ResourceIconEntity resourceIconEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		if(null!=resourceEntity) {
			param = JSONObject.fromObject(resourceEntity);
		}
		if(null!=resourceIconEntity) {
			param.putAll(JSONObject.fromObject(resourceIconEntity));
		}
		Object result = cluster.getParentResult("saveResource", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS, this.resService.save(resourceEntity, resourceIconEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 修改资源及图标,修改时不能改为已存在资源名称
	 * 其中resourceId不能为空
	 * @param resourceEntity
	 * @param resourceIconEntity
	 * @return
	 */
	@Info("修改资源及图标,修改时不能改为已存在资源名称")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyResource")
	@ApiOperation(value="修改资源及图标,修改时不能改为已存在资源名称",response=Result.class,httpMethod="POST")
	public Object modifyResource(ResourceEntity resourceEntity,ResourceIconEntity resourceIconEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		if(null!=resourceEntity) {
			param = JSONObject.fromObject(resourceEntity);
		}
		if(null!=resourceIconEntity) {
			param.putAll(JSONObject.fromObject(resourceIconEntity));
		}
		Object result = cluster.getParentResult("modifyResource", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			this.resService.modify(resourceEntity, resourceIconEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 调整资源排序
	 * @param upId
	 * @param downId
	 * @return
	 */
	@Info("调整资源排序")
	@RequestMapping("/sortResource")
	@ResponseBody
	@ApiOperation(value="调整资源排序",response=Result.class,httpMethod="POST")
	public Object sortResource(String upId,String downId,ServletRequest request){
		if(null==upId||upId.isEmpty()||null==downId||downId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
		}
		Map<String, Object> param = new HashMap<>();
		param.put("upId", upId);
		param.put("downId", downId);
		Object result = cluster.getParentResult("sortResource", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			this.resService.sortResource(upId, downId);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}