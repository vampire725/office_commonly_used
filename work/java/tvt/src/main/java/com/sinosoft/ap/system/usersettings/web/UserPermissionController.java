package com.sinosoft.ap.system.usersettings.web;


import static com.sinosoft.ap.util.token.GetToken.analysis;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.common.constant.LogConstant;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionSearchService;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.service.RoleSearchService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户许可管理",description="用户许可管理")
public class UserPermissionController {
	
	@Autowired
	private JavaWebToken javaWebToken;
	
	@Autowired
	private RoleSearchService roleSearchService;
	
	@Autowired
	private PermissionSearchService permissionSearchService;

	@Autowired
	private Cluster cluster;
	
	@Value("${stateless.protocol}") 
	private String superAdminProcol; 
	
	@Value("${stateless.protocol.code}") 
	private String superAdminProcolCode; 
	
	@Value("${open.userloginverifycode}") 
	private boolean openuserloginverifycode;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	/**
	 * 查询当前用户所有许可
	 * @return
	 */
	@Info("查询当前用户所有许可")
	@RequestMapping("/getUserAuthList")
	@ResponseBody
	@ApiOperation(value="查询当前用户所有许可",response=Result.class,httpMethod="POST")
	public Object getUserAuthList( ServletRequest request, ServletResponse response, String token){
		String tokens  = "";
		String userId = "";
		if(!StringUtil.checkNull(token)) {
			tokens = token;
		}else{
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			tokens = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
			Map<String, Object> param = new HashMap<>();
			param.put("token", tokens);
			Object result = cluster.getParentResult("getUserAuthList", param,analysis(httpServletRequest));
			if(null!=result) {
				return result;
			}
		}
		Map<String, Object> userInfo = new HashMap<>();
		try {
			userInfo = javaWebToken.parserJavaWebToken(tokens);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		userId = (String) userInfo.get(ResultConstant.USER_ID);			
		Result result = new Result();
		if(null==userId||userId.isEmpty()){
			result.setStatus(ResultConstant.FAIL_STATU);
			result.setMessage(ResultConstant.SELECT_FAIL_ERROE_ATTRIBUTE);
			return result;
		}
		try {
			/**
			 * 查询用户许可，以及用户所有角色许可
			 */
			List<PermissionEntity> permissionEntities = new ArrayList<>();
			permissionEntities = this.permissionSearchService.findtUserPermission(userId);
			List<RoleEntity> roleEntities = new ArrayList<>();
			roleEntities = this.roleSearchService.findUserRole(userId)==null?new ArrayList<>():this.roleSearchService.findUserRole(userId);
			List<String> ids = new ArrayList<>();
			for(RoleEntity r:roleEntities){
				ids.add(r.getRoleId());
			}
			List<PermissionEntity> permissionEntities2 = new ArrayList<>();
			for(String id:ids){
				PermissionEntity permissionEntity = new PermissionEntity();
				permissionEntity.setRoleId(id);
				List<PermissionEntity> temp = this.permissionSearchService.find(permissionEntity);
				permissionEntities2.removeAll(temp);
				permissionEntities2.addAll(temp);
			}
			
			permissionEntities.removeAll(permissionEntities2);
			permissionEntities.addAll(permissionEntities2);
			permissionEntities = removeDouble(permissionEntities);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS,permissionEntities);
		} catch (Exception e) {
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	private List<PermissionEntity> removeDouble(List<PermissionEntity> lists) {
		List<PermissionEntity> permissionEntities = new ArrayList<>();
		for(int i=0; i<lists.size();i++) {
			if(i == 0) {
				permissionEntities.add(lists.get(0));
			}
			else {
				for (int j = 0;j<permissionEntities.size();j++) {
					if(!permissionEntities.get(j).getPermissionId().equals(lists.get(i).getPermissionId())) {
						permissionEntities.add(lists.get(i));
					}
				}
			}
		}
		return permissionEntities;
	}
	
}