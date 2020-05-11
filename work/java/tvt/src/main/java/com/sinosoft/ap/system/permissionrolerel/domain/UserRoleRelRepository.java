package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;
import org.springframework.stereotype.Repository;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
@Repository
public interface UserRoleRelRepository{

	 /**
     * 根据给定的参数新增一条数据
     * @param UserRoleRelEntity
     */
	void insert( UserRoleRelEntity userRoleRelEntity ) throws Exception ;
	
	 /**
     * 根据给定的参数新增一条数据
     * @param UserRoleRelEntity
     */
	void insertMore( UserRolesRelEntity userRolesRelEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserRoleRelEntity
     */
	void delete( UserRoleRelEntity userRoleRelEntity ) throws Exception ;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserRoleRelEntity
     * @return List<UserRoleRelEntity>
     */
	List<UserRoleRelEntity> select( UserRoleRelEntity userRoleRelEntity ) throws Exception ;
	
}