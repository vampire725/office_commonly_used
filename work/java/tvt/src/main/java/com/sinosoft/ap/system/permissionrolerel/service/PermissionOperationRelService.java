package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;

import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelEntity;

/***
 * @since 2017 年  04 月 07 日 01:39:42 
 */
public interface PermissionOperationRelService {

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOperationRelEntity
     */
	void save( PermissionOperationRelEntity permissionOperationRelEntity ) throws Exception ;
	
	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOperationRelEntity
     */
	void saveList( List<PermissionOperationRelEntity> permissionOperationRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOperationRelEntity
     */
	void remove( PermissionOperationRelEntity permissionOperationRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOperationRelEntity
     * @return List<PermissionOperationRelEntity>
     */
	List<PermissionOperationRelEntity> find( PermissionOperationRelEntity permissionOperationRelEntity) throws Exception ;
	
}