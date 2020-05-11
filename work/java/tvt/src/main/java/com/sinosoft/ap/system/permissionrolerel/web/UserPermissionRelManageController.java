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
import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.UserPermissionRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户许可关联管理",description="用户许可关联管理")
public class UserPermissionRelManageController {

	@Autowired
	private UserPermissionRelService userPermissionRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 *  新增用户许可关联关系
	 * permissionId与userId不能为空，
	 * 当需要为用户绑定多个许可时，用 “,”将多个许可Id拼接成一个字符串传入即可
	 * 在新增当前绑定关系之前，会将之前所有绑定关系清空
	 * @param userPermissionRelEntity
	 * @return
	 */
	@Info("新增用户许可关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveUserPermissionRel")
	@ResponseBody
	@ApiOperation(value="新增用户许可关联关系",response=Result.class,httpMethod="POST")
	public Object saveUserPermissionRel(UserPermissionRelEntity userPermissionRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userPermissionRelEntity);
		Object result = cluster.getParentResult("saveUserPermissionRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String userId = userPermissionRelEntity.getUserId();
		String permissionId = userPermissionRelEntity.getPermissionId();
		if(null==userId||userId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			UserPermissionRelEntity userPermissionRelEntity2 = new UserPermissionRelEntity();
			
			userPermissionRelEntity2.setUserId(userId);
			this.userPermissionRelService.remove(userPermissionRelEntity2);
			if(!StringUtil.checkNull(permissionId)){
				String[] permissionIds = permissionId.split(",");
				for(String id:permissionIds){
					userPermissionRelEntity.setPermissionId(id);
					userPermissionRelService.save(userPermissionRelEntity);
				}
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}