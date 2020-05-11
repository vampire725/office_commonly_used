package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserPermissionRelRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
 @Service
public class UserPermissionRelServiceImpl implements UserPermissionRelService{

	@Autowired
	private UserPermissionRelRepository userPermissionRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param UserPermissionRelEntity
     */
	@Override
	public void save(UserPermissionRelEntity userPermissionRelEntity) throws Exception {
		this.userPermissionRelRepository.insert(userPermissionRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param UserPermissionRelEntity
     */
	@Override
	public void remove(UserPermissionRelEntity userPermissionRelEntity) throws Exception {
		this.userPermissionRelRepository.delete(userPermissionRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserPermissionRelEntity
     * @return List<UserPermissionRelEntity>
     */
	@Override
	public List<UserPermissionRelEntity> find(UserPermissionRelEntity userPermissionRelEntity) throws Exception {
		
		return this.userPermissionRelRepository.select(userPermissionRelEntity);
	}
	
}