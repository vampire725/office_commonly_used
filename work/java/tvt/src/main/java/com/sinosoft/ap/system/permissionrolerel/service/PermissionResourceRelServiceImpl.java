package com.sinosoft.ap.system.permissionrolerel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.permissionrolerel.domain.PermissionResourceRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionResourceRelRepository;

@Service
public class PermissionResourceRelServiceImpl implements PermissionResourceRelService {
	
	@Autowired
	private PermissionResourceRelRepository  permissionResourceRelRepository;

	@Override
	public void save(PermissionResourceRelEntity permissionResourceRelEntity)
			throws Exception {
			this.permissionResourceRelRepository.insert(permissionResourceRelEntity);
	}

	@Override
	public void remove(PermissionResourceRelEntity permissionResourceRelEntity)
			throws Exception {
		this.permissionResourceRelRepository.delete(permissionResourceRelEntity);

	}

	@Override
	public List<PermissionResourceRelEntity> find(
			PermissionResourceRelEntity permissionResourceRelEntity)
			throws Exception {
		return this.permissionResourceRelRepository.select(permissionResourceRelEntity);
	}

}
