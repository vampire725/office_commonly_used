package com.sinosoft.ap.system.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.common.domain.CommonManageMapper;
import com.sinosoft.ap.system.common.vo.RoleManageVO;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
@Service
public class CommonManageServiceImpl implements CommonManageService{

	@Autowired
	private CommonManageMapper commonMapper ;
	
	@Override
	public List<UserEntity> selectUserInfoByRole(RoleManageVO entity)
			throws Exception {
		
		return this.commonMapper.selectUserInfoByRole(entity);
	}

	@Override
	public List<UserEntity> selectUserInfoByRoles(List<String> roleNames)
			throws Exception {

		return this.commonMapper.selectUserInfoByRoles(roleNames);
	}
}
