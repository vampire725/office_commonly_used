package com.sinosoft.ap.system.permissionmanage.service;

import java.util.List;

import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;

public interface PermissionSearchService {

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param permissionEntity
	 * @return List<PermissionEntity>
	 */
	List<PermissionEntity> find( PermissionEntity permissionEntity) throws Exception ;

	/**
	 * 根据组织查询许可
	 * @param organId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> findOrganPermission( String  organId) throws Exception ;

	/**
	 * 根据用户查询许可
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<PermissionEntity> findtUserPermission( String  userId) throws Exception ;

}
