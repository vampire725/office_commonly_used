package com.sinosoft.ap.system.permissionmanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@RestController
@RequestMapping("/ap-system")
@Api(value="许可查询",description="许可查询")
public class PermissionSearchController {

	@Autowired
	private PermissionSearchService permissionSearchService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 查询许可，当传入roleId时，能够根据角色查询许可
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Info("查询许可，当传入roleId时，能够根据角色查询许可")
	@RequestMapping("/findPermission")
	@ResponseBody
	@ApiOperation(value="查询许可，当传入roleId时，能够根据角色查询许可",response=Result.class,httpMethod="POST")
	public Object findPermission(PermissionEntity entity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(entity);
		Object result = cluster.getParentResult("findPermission", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.permissionSearchService.find(entity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 查询与组织直接相关的许可
	 * @param organId
	 * @return
	 */
	@Info("查询与组织直接相关的许可")
	@RequestMapping("/findPermissionByOrgan")
	@ResponseBody
	@ApiOperation(value="查询与组织直接相关的许可",response=Result.class,httpMethod="POST")
	public Object findPermissionByOrgan(String organId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("organId", organId);
		Object result = cluster.getParentResult("findPermissionByOrgan", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.permissionSearchService.findOrganPermission(organId));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 返回与角色直接相关许可
	 * @param roleId
	 * @return
	 */
	@Info("返回与角色直接相关许可")
	@RequestMapping("/findPermissionByRole")
	@ResponseBody
	@ApiOperation(value="返回与角色直接相关许可",response=Result.class,httpMethod="POST")
	public Object findPermissionByRole(String roleId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		Object result = cluster.getParentResult("findPermissionByRole", param,analysis(request));
		if(null!=result) {
			return result;
		}
		if(StringUtil.checkNull(roleId)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		try {
			PermissionEntity permissionEntity = new PermissionEntity();
			permissionEntity.setRoleId(roleId);
			return new Result(ResultConstant.SUCCESS_STATU	,ResultConstant.SELECT_SUCCESS, this.permissionSearchService.find(permissionEntity));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

	/**
	 * 返回与用户直接相关的许可
	 * @param userId
	 * @return
	 */
	@Info("返回与用户直接相关的许可")
	@RequestMapping("/findPermissionByUser")
	@ResponseBody
	@ApiOperation(value="返回与用户直接相关的许可",response=Result.class,httpMethod="POST")
	public Object findPermissionByUser(String userId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object result = cluster.getParentResult("findPermissionByUser", param,analysis(request));
		if(null!=result) {
			return result;
		}
		try {
			return new Result(ResultConstant.SUCCESS_STATU, 
					ResultConstant.SELECT_SUCCESS, 
					this.permissionSearchService.findtUserPermission(userId));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}

}
