package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelRepository;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRolesRelEntity;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
 @Service
public class UserRoleRelServiceImpl implements UserRoleRelService{

	@Autowired
	private UserRoleRelRepository userRoleRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserRoleRelEntity
     */
	@Override
	public void save(UserRoleRelEntity userRoleRelEntity) throws Exception {
		this.userRoleRelRepository.insert(userRoleRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserRoleRelEntity
     */
	@Override
	public void remove(UserRoleRelEntity userRoleRelEntity) throws Exception {
		this.userRoleRelRepository.delete(userRoleRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserRoleRelEntity
     * @return List<UserRoleRelEntity>
     */
	@Override
	public List<UserRoleRelEntity> find(UserRoleRelEntity userRoleRelEntity) throws Exception {
		
		return this.userRoleRelRepository.select(userRoleRelEntity);
	}

	@Override
	public void saveMore(UserRolesRelEntity userRolesRelEntity) throws Exception {
		this.userRoleRelRepository.insertMore(userRolesRelEntity);
	}
	
}