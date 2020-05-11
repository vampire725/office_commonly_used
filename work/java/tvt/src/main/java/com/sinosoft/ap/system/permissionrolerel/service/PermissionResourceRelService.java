package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;

import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionResourceRelEntity;

public interface PermissionResourceRelService {

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOrganRelEntity
     */
	void save( PermissionResourceRelEntity permissionResourceRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOrganRelEntity
     */
	void remove( PermissionResourceRelEntity permissionResourceRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOrganRelEntity
     * @return List<PermissionOrganRelEntity>
     */
	List<PermissionResourceRelEntity> find( PermissionResourceRelEntity permissionResourceRelEntity) throws Exception ;
}
