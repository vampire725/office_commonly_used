package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
@Repository
public interface PermissionRoleRelRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionRoleRelEntity
     */
	void insert( PermissionRoleRelEntity permissionRoleRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionRoleRelEntity
     */
	void delete( PermissionRoleRelEntity permissionRoleRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionRoleRelEntity
     * @return List<PermissionRoleRelEntity>
     */
	List<PermissionRoleRelEntity> select( PermissionRoleRelEntity permissionRoleRelEntity ) throws Exception ;
	
}