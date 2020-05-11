package com.sinosoft.ap.system.rolemanage.service;

import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;

/***
 * @since 2017 年  04 月 07 日 01:14:00 
 */
public interface RoleManageService {

	 /**
     * 根据给定的参数新增一条数据
     * @param RoleEntity
     */
	RoleEntity save( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param RoleEntity
     */
	void remove( RoleEntity roleEntity ) throws Exception ;
	
    /**
     * 指定主键，修改给定信息项
     * @param RoleEntity
     */
	void modify( RoleEntity roleEntity ) throws Exception ;
	
}