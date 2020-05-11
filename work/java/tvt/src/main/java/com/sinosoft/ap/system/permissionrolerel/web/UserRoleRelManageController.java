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
import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.UserRoleRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户角色关联管理",description="用户角色关联管理")
public class UserRoleRelManageController {

	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 *  新增用户角色关联关系
	 * userId与roleId不能为空，
	 * 当需要为用户绑定多个角色时，用 “,”将多个角色Id拼接成一个字符串传入即可
	 * 在新增当前绑定关系之前，会将之前所有绑定关系清空
	 * @param userRoleRelEntity
	 * @return
	 */
	@Info("新增用户角色关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveUserRoleRel")
	@ResponseBody
	@ApiOperation(value="新增用户角色关联关系",response=Result.class,httpMethod="POST")
	public Object saveUserRoleRel(UserRoleRelEntity userRoleRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userRoleRelEntity);
		Object result = cluster.getParentResult("saveUserRoleRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String userId = userRoleRelEntity.getUserId();
		String roleId = userRoleRelEntity.getRoleId();
		if(null==userId||userId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			UserRoleRelEntity userRoleRelEntity2 = new UserRoleRelEntity();
			
			userRoleRelEntity2.setUserId(userId);
			this.userRoleRelService.remove(userRoleRelEntity2);
			if(!StringUtil.checkNull(roleId)){
				String[] roleIds = roleId.split(",");
				for(String id:roleIds){
					userRoleRelEntity.setRoleId(id);
					userRoleRelService.save(userRoleRelEntity);
				}
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}