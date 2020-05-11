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
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionOrganRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="许可机构关联管理",description="许可机构关联管理")
public class PermissionOrganRelManageController {

	@Autowired
	private PermissionOrganRelService permissionOrganRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 *  新组织许可关联关系
	 * permissionId与organId不能为空，
	 * 当需要为组织绑定多个许可时，用 “,”将多个许可Id拼接成一个字符串传入即可
	 * 在新增当前绑定关系之前，会将之前所有绑定关系清空
	 * @param permissionOrganRelEntity
	 * @return
	 */
	@Info("新组织许可关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/savePermissionOrganRel")
	@ResponseBody
	@ApiOperation(value="新组织许可关联关系",response=Result.class,httpMethod="POST")
	public Object savePermissionOrganRel(PermissionOrganRelEntity permissionOrganRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(permissionOrganRelEntity);
		Object result = cluster.getParentResult("savePermissionOrganRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String organId = permissionOrganRelEntity.getOrganId();
		String permissionId = permissionOrganRelEntity.getPermissionId();
		if(null==organId||organId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			PermissionOrganRelEntity organPermissionRelEntity2 = new PermissionOrganRelEntity();
			
			organPermissionRelEntity2.setOrganId(organId);
			this.permissionOrganRelService.remove(organPermissionRelEntity2);
			if(!StringUtil.checkNull(permissionId)){
				String[] permissionIds = permissionId.split(",");
				for(String id:permissionIds){
					permissionOrganRelEntity.setPermissionId(id);;
					this.permissionOrganRelService.save(permissionOrganRelEntity);
				}
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}