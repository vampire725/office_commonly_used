package com.sinosoft.ap.system.userorganizationrel.web;

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
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.service.UserOrganizationRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户组织关联管理",description="用户组织关联管理")
public class UserOrganizationRelManageController {

	@Autowired
	private UserOrganizationRelService userOrganizationRelService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 删除用户组织关联关系
	 * 参数必须不能为空
	 * @param userOrganizationRelEntity
	 * @return
	 */
	@Info("删除用户组织关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/removeUserOrganizationRel")
	@ResponseBody
	@ApiOperation(value="删除用户组织关联关系",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(UserOrganizationRelEntity userOrganizationRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userOrganizationRelEntity);
		Object result = cluster.getParentResult("removeUserOrganizationRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String userId = userOrganizationRelEntity.getUserId();
		String organId = userOrganizationRelEntity.getOrganId();
		if(null==userId||null==organId||userId.isEmpty()||organId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.NULL_ATTRIBUTE);
		}
		try {
			userOrganizationRelService.remove(userOrganizationRelEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增用户关联关系
	 * 参数必须不能为空
	 * @param userOrganizationRelEntity
	 * @return
	 */
	@Info("新增用户关联关系")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveUserOrganizationRel")
	@ResponseBody
	@ApiOperation(value="新增用户关联关系",response=Result.class,httpMethod="POST")
	public Object saveUserOrganizationRel(UserOrganizationRelEntity userOrganizationRelEntity,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userOrganizationRelEntity);
		Object result = cluster.getParentResult("saveUserOrganizationRel", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String userId = userOrganizationRelEntity.getUserId();
		String organId = userOrganizationRelEntity.getOrganId();
		if(null==userId||null==organId||userId.isEmpty()||organId.isEmpty()){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
		try {
			userOrganizationRelService.save(userOrganizationRelEntity);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
}