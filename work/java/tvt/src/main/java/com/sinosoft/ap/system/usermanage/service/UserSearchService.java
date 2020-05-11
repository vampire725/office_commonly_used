package com.sinosoft.ap.system.usermanage.service;

import java.util.List;

import com.github.pagehelper.Page;
import com.sinosoft.ap.component.page.PageParam;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;

public interface UserSearchService {

	/**
	 * 查询组织下所有用户
	 * @param organId
	 * @return
	 * @throws Exception
	 */
	List<UserEntity> find( String organId) throws Exception ;

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param UserEntity
	 * @return List<UserEntity>
	 */
	List<UserEntity> find( UserEntity userEntity) throws Exception ;

	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param UserEntity
	 * @return List<UserEntity>
	 */
	Page<UserEntity> find( UserEntity userEntity,PageParam pageParam) throws Exception ;

	/**
	 * 根据用户id查询机构编码
	 */
	String findOrganCodeByUserId(String userId) throws Exception;

	List<PermissionEntity> getPermission(String userId) throws Exception;
}
