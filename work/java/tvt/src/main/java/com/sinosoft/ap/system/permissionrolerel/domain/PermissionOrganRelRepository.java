package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
@Repository
public interface PermissionOrganRelRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOrganRelEntity
     */
	void insert( PermissionOrganRelEntity permissionOrganRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOrganRelEntity
     */
	void delete( PermissionOrganRelEntity permissionOrganRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOrganRelEntity
     * @return List<PermissionOrganRelEntity>
     */
	List<PermissionOrganRelEntity> select( PermissionOrganRelEntity permissionOrganRelEntity ) throws Exception ;
	
}