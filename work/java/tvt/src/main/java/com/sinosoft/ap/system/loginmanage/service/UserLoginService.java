package com.sinosoft.ap.system.loginmanage.service;

import java.util.List;

import com.sinosoft.ap.system.loginmanage.domain.UserLoginEntity;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;

/***
 * @since 2017 年  04 月 07 日 01:16:39 
 */
public interface UserLoginService {
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginEntity
     * @return List<UserLoginEntity>
     */
	List<UserLoginEntity> find( UserLoginEntity UserLoginEntity) throws Exception ;
	
	/**
	 * 查询用户所属组织许可信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> findUserOrganPermission(String userId) throws Exception;
	
	/**
	 * 查询所属用户许可信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> findUserPermission(String userId) throws Exception;

}