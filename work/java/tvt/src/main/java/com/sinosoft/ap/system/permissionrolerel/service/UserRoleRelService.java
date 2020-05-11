package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;

import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRolesRelEntity;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public interface UserRoleRelService {

	 /**
     * 根据给定的参数新增一条数据
     * @param UserRoleRelEntity
     */
	void save( UserRoleRelEntity userRoleRelEntity ) throws Exception ;
	

	 /**
    * 根据给定的参数新增一条数据
    * @param UserRoleRelEntity
    */
	void saveMore( UserRolesRelEntity userRolesRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserRoleRelEntity
     */
	void remove( UserRoleRelEntity userRoleRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserRoleRelEntity
     * @return List<UserRoleRelEntity>
     */
	List<UserRoleRelEntity> find( UserRoleRelEntity userRoleRelEntity) throws Exception ;
	
}