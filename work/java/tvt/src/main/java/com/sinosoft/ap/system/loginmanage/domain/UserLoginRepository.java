package com.sinosoft.ap.system.loginmanage.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;

/***
 * @since 2017 年  04 月 07 日 01:16:39 
 */
@Repository
public interface UserLoginRepository{

    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginEntity
     * @return List<UserLoginEntity>
     */
	List<UserLoginEntity> select( UserLoginEntity UserLoginEntity ) throws Exception ;
	/**
	 * 查询用户所属组织许可信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> selectUserOrganPermission(String userId) throws Exception;
	/**
	 * 查询用户角色绑定的许可
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> selectUserRolePermission(String userId) throws Exception;
	
	/**
	 * 查询用户自身的权限
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> selectUserOwnPermission(String userId) throws Exception;
	
}