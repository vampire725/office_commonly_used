package com.sinosoft.ap.system.permissionrolerel.web;

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
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionRoleRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="许可角色关联管理",description="许可角色关联管理")
public class PermissionRoleRelManageController {

	@Autowired
	private PermissionRoleRelService permissionRoleRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 *  新增角色许可关联关系
	 * permissionId与roleIdId不能为空，
	 * 当需要为角色绑定多个许可时，用 “,”将多个许可Id拼接成一个字符串传入即可
	 * 在新增当前绑定关系之前，会将之前所有绑定关系清空
	 * @param permissionRoleRelEntity
	 * @return
	 */
	@Info("新增角色许可关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/savePermissionRoleRel")
	@ResponseBody
	@ApiOperation(value="新增角色许可关联关系",response=Result.class,httpMethod="POST")
	public Object savePermissionRoleRel(PermissionRoleRelEntity permissionRoleRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(permissionRoleRelEntity);
		Object result = cluster.getParentResult("savePermissionRoleRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String roleId = permissionRoleRelEntity.getRoleId();
		String permissionId = permissionRoleRelEntity.getPermissionId();
		if(null==roleId||roleId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			PermissionRoleRelEntity rolePermissionRelEntity2 = new PermissionRoleRelEntity();
			
			rolePermissionRelEntity2.setRoleId(roleId);
			this.permissionRoleRelService.remove(rolePermissionRelEntity2);
			if(!StringUtil.checkNull(permissionId)){
				String[] permissionIds = permissionId.split(",");
				for(String id:permissionIds){
					permissionRoleRelEntity.setPermissionId(id);
					permissionRoleRelService.save(permissionRoleRelEntity);
				}
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}