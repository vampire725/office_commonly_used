package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionOrganRelRepository;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
 @Service
public class PermissionOrganRelServiceImpl implements PermissionOrganRelService{

	@Autowired
	private PermissionOrganRelRepository permissionOrganRelRepository;

	 /**
     * 根据给定的参数新增一条数据
     * @param PermissionOrganRelEntity
     */
	@Override
	public void save(PermissionOrganRelEntity permissionOrganRelEntity) throws Exception {
		this.permissionOrganRelRepository.insert(permissionOrganRelEntity);
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param PermissionOrganRelEntity
     */
	@Override
	public void remove(PermissionOrganRelEntity permissionOrganRelEntity) throws Exception {
		this.permissionOrganRelRepository.delete(permissionOrganRelEntity);
		
	}
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param PermissionOrganRelEntity
     * @return List<PermissionOrganRelEntity>
     */
	@Override
	public List<PermissionOrganRelEntity> find(PermissionOrganRelEntity permissionOrganRelEntity) throws Exception {
		
		return this.permissionOrganRelRepository.select(permissionOrganRelEntity);
	}
	
}