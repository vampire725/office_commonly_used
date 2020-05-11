package com.sinosoft.ap.system.rolemanage.service;

import java.util.List;

import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;

public interface RoleSearchService {

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param RoleEntity
	 * @return List<RoleEntity>
	 */
	List<RoleEntity> find( RoleEntity roleEntity) throws Exception ;

	/**
	 * 根据用户查询角色
	 * @param RoleEntity
	 * @return List<RoleEntity>
	 */
	List<RoleEntity> findUserRole( String userId ) throws Exception ;

}
