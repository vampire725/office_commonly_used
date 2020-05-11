package com.sinosoft.ap.system.loginmanage.web;

import static com.sinosoft.ap.util.token.GetToken.analysis;

import com.sinosoft.ap.system.permissionmanage.service.PermissionManageService;
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
import com.sinosoft.ap.system.log.service.ApsUserVisiteSystemLogService;
import com.sinosoft.ap.system.loginmanage.service.UserLoginService;
import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.operationmanage.service.OperationSearchService;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.service.PermissionSearchService;
import com.sinosoft.ap.system.resourcemanage.domain.ResourceVO;
import com.sinosoft.ap.system.resourcemanage.service.ResourceService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.list.ListUtil;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;
import com.sinosoft.ap.util.token.JavaWebToken;


@RestController
@RequestMapping("ap-system")
@Api(value="用户权限管理",description="用户权限管理")
public class  UserAuthorityController {
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private OperationSearchService operationSearchService;
	
	@Autowired
	private PermissionSearchService permissionSearchService;
	
	@Autowired
	private ApsUserVisiteSystemLogService userLogService;

	@Autowired
	private PermissionManageService permissionManageService;
	
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
	
	@Autowired
	private JavaWebToken javaWebToken;
	
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 查询当前用户所能查看的资源信息
	 * @return
	 */
	@Info("查询当前用户所能查看的资源信息")
	@RequestMapping("/getUserResourceList")
	@ResponseBody
	@ApiOperation(value="查询当前用户所能查看的资源信息",response=Result.class,httpMethod="POST")
	public Object getUserResourceList( ServletRequest request, ServletResponse response, String system_code ,String userIds,String tree,OperationEntity oper){

		if(StringUtil.checkNull(system_code)) {
			return new Result(ResultConstant.FAIL_STATU,ResultConstant.NULL_ATTRIBUTE);
		}
		String userId  = "";
		if(!StringUtil.checkNull(userIds)) {
			userId = userIds;
		}else{
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String token = httpServletRequest.getHeader(LogConstant.AUTHORIZATION);
			Map<String, Object> userInfo = new HashMap<>();
			try {
				userInfo = javaWebToken.parserJavaWebToken(analysis(request));
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(ResultConstant.FAIL_STATU, e.getMessage());
			}
			userId = (String) userInfo.get(ResultConstant.USER_ID);			
			Map<String, Object> param = new HashMap<>();
			if(!StringUtil.checkNull(userId)) {
				param.put("userIds", userId);
				param.put("system_code", system_code);
				param.put("tree", tree);
			}
			Object result = cluster.getParentResult("getUserResourceList", param,token);
			System.out.println("子系统登录日志记录结束结束=================================");
			if(null!=result) {
				return result;
			}
		}
		try {
			List<String> ids =new ArrayList<>();
			List<String> name = new ArrayList<>();
			List<OperationEntity> list = permissionManageService.userAuth(userId);
//			List<String> ids = permissionManageService.getResourceId(userId);
			for(OperationEntity o:list){
				if (!ids.contains(o.getResourceId())) {
					ids.add(o.getResourceId());
				}
			}
			if(!StringUtil.checkNull(system_code)&&!system_code.equals("system_code")) {
				ids.remove(system_code);				
			}
			List<ResourceVO> resourceVOs = new ArrayList<>();
			if(ids!=null&&ids.size()>0) {
				resourceVOs = this.resourceService.findResourceIcon(ids);
			}
			resourceVOs = resourceVOs==null? new ArrayList<>():resourceVOs;
			if(resourceVOs.size()<1) {
				return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS,new ArrayList<>());
			}
			List<ResourceVO> temp = new ArrayList<>();
			if(system_code.equals("system_code")) {
				resourceVOs.forEach(t -> {
					if(t.getResourcePath().contains("1a1fee100c148ef068ce62e574252fce")) {
						temp.add(t);
						name.add(t.getResourceName());
					}
				});
			}else if(StringUtil.checkNull(tree)) {
				resourceVOs.forEach(t -> {
					if(t.getResourceParentId().equals(system_code)) {
						t.setResourceParentId("0");
						temp.add(t);
						name.add(t.getResourceName());
					}
				});				
			}else {
				resourceVOs.forEach(t -> {
					if(t.getResourcePath().contains(system_code)) {
						if(t.getResourceParentId().equals(system_code)) {
							t.setResourceParentId("0");							
						}
						temp.add(t);
						name.add(t.getResourceName());
					}
				});	
			}
//			System.out.println("当前登录用户可用资源"+name);
			this.userLogService.saveUserLogSysteInfos(userId,system_code);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.SELECT_SUCCESS,temp);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
//	private List<OperationEntity> userAuth (String userId) throws Exception {
//		List<PermissionEntity> permissionEntities = new ArrayList<>();
//		List<PermissionEntity> temp = new ArrayList<>();
//		if (userId.equals(superAdminProcolCode)) {
//			List<PermissionEntity> list = new ArrayList<>();
//			PermissionEntity permissionEntity = new PermissionEntity();
//			list = this.permissionSearchService.find(permissionEntity);
//			temp = list;
//		}else {
//			List<PermissionEntity> organPermissionEntities = new ArrayList<>();
//			organPermissionEntities = this.userLoginService.findUserOrganPermission(userId);
//			List<PermissionEntity> userPermissionEntities = new ArrayList<>();
//			userPermissionEntities = this.userLoginService.findUserPermission(userId);
//			userPermissionEntities.addAll(organPermissionEntities);
//			temp = userPermissionEntities;
//		}
//		List<String> ids = new ArrayList<>();
//		if (null!=temp&&temp.size()>0) {
//			for (PermissionEntity permissionEntity:temp) {
//				if (!ids.contains(permissionEntity.getPermissionId())) {
//					ids.add(permissionEntity.getPermissionId());
//					permissionEntities.add(permissionEntity);
//				}
//			}
//		}
//		if (ListUtil.checkNull(ids)) {
//			return new ArrayList<>();
//		}else {
//			if(userId.equals(superAdminProcolCode)) {
//				List<OperationEntity> temps = this.operationSearchService.find(new OperationEntity());
//				return temps;
//			}else{
//				List<OperationEntity> operationEntities = new ArrayList<>();
//				operationEntities = this.operationSearchService.findByPermissionId(ids);
//				return operationEntities;
//			}
//		}
//	}
	
}
