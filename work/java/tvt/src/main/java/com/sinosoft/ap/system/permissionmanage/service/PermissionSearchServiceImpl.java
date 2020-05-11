package com.sinosoft.ap.system.permissionmanage.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionRepository;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class PermissionSearchServiceImpl implements PermissionSearchService {


	@Autowired
	private PermissionRepository permissionRepository;
	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param PermissionEntity
	 * @return List<PermissionEntity>
	 */
	@Override
	public List<PermissionEntity> find(PermissionEntity permissionEntity) throws Exception {
		return this.permissionRepository.select(permissionEntity);
	}

	/**
	 * 根据组织查询许可
	 * @param organId
	 * @return List<PermissionEntity>
	 */
	@Override
	public List<PermissionEntity> findOrganPermission(String organId) throws Exception {
		if(StringUtil.checkNull(organId)){
			new ArrayList<>();
		}
		return this.permissionRepository.selectOrganPermission(organId);
	}

	@Override
	public List<PermissionEntity>findtUserPermission(String userId) throws Exception {
		if(StringUtil.checkNull(userId)){
			new ArrayList<>();
		}
		return this.permissionRepository.selectUserPermission(userId);
	}

}
