package com.sinosoft.ap.system.permissionmanage.web;


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
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionManageService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="许可管理",description="许可管理")
public class PermissionManageController {

	@Autowired
	private PermissionManageService permissionManageService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	/**
	 * 删除许可，同时删除与该许可关联信息，防止产生脏数据
	 * @param permissionId
	 * @return
	 */
	@Info("删除许可，同时删除与该许可关联信息，防止产生脏数据")
	@RequestMapping("/removePermission")
	@ResponseBody
	@ApiOperation(value="删除许可，同时删除与该许可关联信息，防止产生脏数据",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(String permissionId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("permissionId", permissionId);
		Object result = cluster.getParentResult("removePermission", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			PermissionEntity permissionEntity = new PermissionEntity();
			permissionEntity.setPermissionId(permissionId);
			this.permissionManageService.remove(permissionEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增许可
	 * @param permissionEntity
	 * @return
	 */
	@Info("新增许可")
	@SuppressWarnings("unchecked")
	@RequestMapping("/savePermission")
	@ResponseBody
	@ApiOperation(value="新增许可",response=Result.class,httpMethod="POST")
	public Object savePermission(PermissionEntity permissionEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(permissionEntity);
		Object result = cluster.getParentResult("savePermission", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS,this.permissionManageService.save(permissionEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 修改许可
	 * permissionId不能为空，且除去permissionId外至少包含一天非空字段才能修改成功
	 * @param permissionEntity
	 * @return
	 */
	@Info("修改许可")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyPermission")
	@ResponseBody
	@ApiOperation(value="修改许可",response=Result.class,httpMethod="POST")
	public Object modifyByPrimaryColumn(PermissionEntity permissionEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(permissionEntity);
		Object result = cluster.getParentResult("modifyPermission", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			this.permissionManageService.modify(permissionEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
}