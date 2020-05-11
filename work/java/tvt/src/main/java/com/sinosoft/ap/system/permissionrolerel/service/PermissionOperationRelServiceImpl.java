package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOperationRelRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:42 
 */
 @Service
public class PermissionOperationRelServiceImpl implements PermissionOperationRelService{

	@Autowired
	private PermissionOperationRelRepository permissionOperationRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOperationRelEntity
     */
	@Override
	public void save(PermissionOperationRelEntity permissionOperationRelEntity) throws Exception {
		this.permissionOperationRelRepository.insert(permissionOperationRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOperationRelEntity
     */
	@Override
	public void remove(PermissionOperationRelEntity permissionOperationRelEntity) throws Exception {
		this.permissionOperationRelRepository.delete(permissionOperationRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOperationRelEntity
     * @return List<PermissionOperationRelEntity>
     */
	@Override
	public List<PermissionOperationRelEntity> find(PermissionOperationRelEntity permissionOperationRelEntity) throws Exception {
		
		return this.permissionOperationRelRepository.select(permissionOperationRelEntity);
	}

	@Override
	public void saveList(List<PermissionOperationRelEntity> permissionOperationRelEntity) throws Exception {
		this.permissionOperationRelRepository.saveList(permissionOperationRelEntity);
		
	}
	
}