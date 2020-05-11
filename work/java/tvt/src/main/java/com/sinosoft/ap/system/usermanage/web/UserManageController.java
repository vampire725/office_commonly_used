package com.sinosoft.ap.system.usermanage.web;



import static com.sinosoft.ap.util.token.GetToken.analysis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sinosoft.ap.common.aop.annotation.Info;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginEntity;
import com.sinosoft.ap.system.loginmanage.domain.UserPasswordEntity;
import com.sinosoft.ap.system.loginmanage.service.UserPasswordService;
import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRolesRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.UserPermissionRelService;
import com.sinosoft.ap.system.permissionrolerel.service.UserRoleRelService;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usermanage.service.UserSearchService;
import com.sinosoft.ap.system.usermanage.service.UserService;
import com.sinosoft.ap.system.userorganizationrel.domain.UserOrganizationRelEntity;
import com.sinosoft.ap.system.userorganizationrel.service.UserOrganizationRelService;
import com.sinosoft.ap.util.cluster.web.Cluster;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.md5.MD5Encrypt;
import com.sinosoft.ap.util.result.Result;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/ap-system")
@Api(value="用户管理",description="用户管理")
public class UserManageController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPasswordService userPasswordService;
	
	@Autowired
	private UserOrganizationRelService userOrganizationRelService;
	
	@Autowired
	private UserPermissionRelService userPermissionRelService;
	
	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Autowired
	private UserSearchService userSearchService;
	
	@Autowired
	private Cluster cluster;
	
	@Value("${ap.cluster.token.key}") 
	private String tokenKey;
	
	/**
	 * 删除用户，可以批量删除，用","拼接
	 * 删除用户同时删除用户所有相关关联关系
	 * @param userId
	 * @return
	 */
	@Info("删除用户，可以批量删除，用\",\"拼接")
	@RequestMapping("/removeUser")
	@ResponseBody
	@ApiOperation(value="删除用户，可以批量删除，用\",\"拼接",response=Result.class,httpMethod="POST")
	public Object removeByAttribute(String userId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object result = cluster.getParentResult("removeUser", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String[] ids = userId.split(",");
		try {
			/**
			 * 删除用户
			 * 删除用户与组织
			 * 用户与角色
			 * 用户与许可关联关系
			 * 
			 */
			for(int i=0;i<ids.length;i++){
				UserOrganizationRelEntity userOrganizationRelEntity = new UserOrganizationRelEntity();
				userOrganizationRelEntity.setUserId(userId);
				UserPermissionRelEntity userPermissionRelEntity = new UserPermissionRelEntity();
				userPermissionRelEntity.setUserId(userId);
				UserRoleRelEntity userRoleRelEntity = new UserRoleRelEntity();
				userRoleRelEntity.setUserId(userId);
				UserEntity entity = new UserEntity();
				entity.setUserId(ids[i]);
				userService.remove(entity);
				userOrganizationRelService.remove(userOrganizationRelEntity);
				userPermissionRelService.remove(userPermissionRelEntity);
				userRoleRelService.remove(userRoleRelEntity);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	
	/**
	 * 新增用户
	 * 新增用户组织关联
	 * 新增用户密码
	 * 新增用户名不能和已有的重复
	 * @param userLoginEntity organId
	 * @param organId
	 * @return
	 */
	@Info("新增用户,用户名不能和已有的重复")
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveUser")
	@ResponseBody
	@ApiOperation(value="新增用户名不能和已有的重复",response=Result.class,httpMethod="POST")
	public Object saveUser(UserLoginEntity userLoginEntity,String organId,ServletRequest request,String roleId){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userLoginEntity);
		param.put("organId", organId);
		Object result = cluster.getParentResult("saveUser", param,analysis(request));
		if(null!=result) {
			return result;
		}
		UserEntity userEntity = new UserEntity();
//		UserEntity userEntity2 = new UserEntity();
		String account = userLoginEntity.getUserAccount();
		String userpassword = userLoginEntity.getUserPassword();
		/**
		 * 用户名重复性检查
		 */
//		if(null!=account&&account!="") {
//			userEntity2.setUserAccount("= '"+account+"'");
//		}else {
//			userEntity2.setUserAccount(null);
//		}
		if(StringUtil.checkNull(account)) {
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
		}
//		List<UserEntity> userEntities = new ArrayList<>();
		UserPasswordEntity userPasswordEntity = new UserPasswordEntity();
		String userId = MD5Encrypt.encrypt(account);
		userPasswordEntity.setUserPasswordCreatetime(new Date());
		userPasswordEntity.setUserPasswordId(PrimaryKeyUtil.create());
		userPasswordEntity.setUserPassword(userpassword);
		userPasswordEntity.setUserId(userId);
		BeanUtils.copyProperties(userLoginEntity, userEntity);
	    userEntity.setUserId(userId);
		try {
//			userEntities = userSearchService.find(userEntity2);
//			if(userEntities.size()>0){
//				return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
//			}
//			userEntity.setUserAccount(userLoginEntity.getUserAccount());
//			userEntity.setUserCreatetime(new Date());
//			userEntity.setUserMail(userLoginEntity.getUserMail());
//			userEntity.setUserTelnumber(userLoginEntity.getUserTelnumber());
//			userPasswordEntity.setUserPassword(userLoginEntity.getUserPassword());
			/**
			 * 保存用户密码信息
			 */
			userService.save(userEntity);
			userPasswordService.save(userPasswordEntity);
			userLoginEntity.setUserId(userId);
			/**
			 * 判断是否需要保存用户与组织关联关系
			 */
			UserOrganizationRelEntity userOrganizationRelEntity = new UserOrganizationRelEntity();
			if(null!=organId&&!organId.isEmpty()){
				userOrganizationRelEntity.setOrganId(organId);
				userOrganizationRelEntity.setUserId(userId);
				userOrganizationRelService.save(userOrganizationRelEntity);
			}
			if(!StringUtil.checkNull(roleId)) {
				UserRoleRelEntity userRoleRelEntity = new UserRoleRelEntity();
				userRoleRelEntity.setUserId(userId);
				userRoleRelService.remove(userRoleRelEntity);
				UserRolesRelEntity userRolesRelEntity = new UserRolesRelEntity();
				userRolesRelEntity.setUserId(userId);
				List<String> list = new ArrayList<>();
				list = java.util.Arrays.asList(roleId.split(","));
				userRolesRelEntity.setRolesId(list);
				this.userRoleRelService.saveMore(userRolesRelEntity);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.INSERT_SUCCESS,userLoginEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU,"账户已存在");
		}
	}
	
	/**
	 * 修改用户信息
	 * 用户id不能为空
	 * 用户名不能与已有的用户重复
	 * @param userEntity
	 * @return
	 */
	@Info("修改用户信息")
	@SuppressWarnings("unchecked")
	@RequestMapping("/modifyUser")
	@ResponseBody
	@ApiOperation(value="修改用户信息",response=Result.class,httpMethod="POST")
	public Object modifyByPrimaryColumn(UserEntity userEntity,ServletRequest request,String roleId){
		Map<String, Object> param = new HashMap<>();
		param = JSONObject.fromObject(userEntity);
		Object result = cluster.getParentResult("modifyUser", param,analysis(request));
		if(null!=result) {
			return result;
		}
		String account = userEntity.getUserAccount();
		/**
		 * 用户id不能为空
		 */
		if(StringUtil.checkNull(userEntity.getUserId())){
			return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
		}
		UserEntity userEntity2 = new UserEntity();
//		List<UserEntity> userEntities = new ArrayList<>();
		if(null!=account&&account!="") {
			userEntity2.setUserAccount("= '"+account+"'");
		}else {
			userEntity2.setUserAccount(null);
		}
		try {
			/**
			 * 用户名重复性检查
			 */
//			userEntities = userSearchService.find(userEntity2);
//			if(userEntities.size()>1){
//				return new Result(ResultConstant.FAIL_STATU, ResultConstant.INSERT_FAIL_ERROE_ATTRIBUTE);
//			}
			userService.modify(userEntity);
			String userId = userEntity.getUserId();
			UserRoleRelEntity userRoleRelEntity = new UserRoleRelEntity();
			userRoleRelEntity.setUserId(userId);
			userRoleRelService.remove(userRoleRelEntity);
			if(!StringUtil.checkNull(roleId)) {
				UserRolesRelEntity userRolesRelEntity = new UserRolesRelEntity();
				userRolesRelEntity.setUserId(userId);
				List<String> list = new ArrayList<>();
				list = java.util.Arrays.asList(roleId.split(","));
				userRolesRelEntity.setRolesId(list);
				this.userRoleRelService.saveMore(userRolesRelEntity);
			}
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	/**
	 * 初始化用户密码为123456
	 * @param userId
	 * @return
	 */
	@Info("初始化用户密码为123456")
	@RequestMapping("/initUserPassword")
	@ResponseBody
	@ApiOperation(value="初始化用户密码为123456",response=Result.class,httpMethod="POST")
	public Object initUserPassword(String userId,ServletRequest request){
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		Object results = cluster.getParentResult("initUserPassword", param,analysis(request));
		if(null!=results) {
			return results;
		}
		Result result = new Result();
		/**
		 *非空判断
		 */
		if(null==userId||userId.isEmpty()){
			result.setStatus(ResultConstant.FAIL_STATU);
			result.setMessage(ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			return result;
		}
		UserEntity userEntity = new UserEntity();
		userEntity.setUserId(userId);
		UserPasswordEntity us = new UserPasswordEntity();
		us.setUserId(userId);
		try {
			List<UserEntity> userEntities = new ArrayList<>();
			List<UserPasswordEntity> userPasswordEntities = new ArrayList<>();
			userEntities= userSearchService.find(userEntity);
			userPasswordEntities = userPasswordService.find(us);
			/**
			 * 重名检查
			 */
			if(userEntities.size()<1||userPasswordEntities.size()<1){
				return new Result(ResultConstant.FAIL_STATU, ResultConstant.UPDATA_FAIL_ERROE_ATTRIBUTE);
			}
			userEntity = userEntities.get(0);
			us = userPasswordEntities.get(0);
			us.setUserPassword(MD5Encrypt.encrypt("123456"));
			userPasswordService.modify(us);
			return new Result(ResultConstant.SUCCESS_STATU, ResultConstant.UPDATA_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultConstant.FAIL_STATU, e.getMessage());
		}
	}
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        CustomDateEditor dateEditor = new CustomDateEditor(simpleDateFormat, true);
        binder.registerCustomEditor(Date.class,dateEditor);
    }
	
		
}