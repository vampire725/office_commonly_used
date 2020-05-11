package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface PermissionResourceRelRepository {
	
	/**
     * 根据给定的参数新增一条数据
     * @param PermissionOrganRelEntity
     */
	void insert( PermissionResourceRelEntity permissionResourceRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOrganRelEntity
     */
	void delete( PermissionResourceRelEntity permissionResourceRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOrganRelEntity
     * @return List<PermissionOrganRelEntity>
     */
	List<PermissionResourceRelEntity> select( PermissionResourceRelEntity permissionResourceRelEntity ) throws Exception ;
	

}
