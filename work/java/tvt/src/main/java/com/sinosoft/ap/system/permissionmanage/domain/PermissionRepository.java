package com.sinosoft.ap.system.permissionmanage.domain;

import java.util.List;

import org.springframework.stereotype.Repository;


/***
 * @since 2017 年  04 月 07 日 01:12:20 
 */
@Repository
public interface PermissionRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionEntity
     */
	void insert( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionEntity
     */
	void delete( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param PermissionEntity
     */
	void update( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询,角色关联查询
     * @param PermissionEntity
     * @return List<PermissionEntity>
     */
	List<PermissionEntity> select( PermissionEntity permissionEntity ) throws Exception ;
	
    /**
     * 根据组织查询许可
     * @param organId
     * @return List<PermissionEntity>
     */
	List<PermissionEntity> selectOrganPermission( String organId ) throws Exception ;
	
    /**
     * 根据用户查询许可
     * @param organId
     * @return List<PermissionEntity>
     */
	List<PermissionEntity> selectUserPermission( String userId ) throws Exception ;
	
	
}