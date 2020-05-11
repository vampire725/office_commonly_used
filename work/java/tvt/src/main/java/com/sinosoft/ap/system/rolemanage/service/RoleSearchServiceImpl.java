package com.sinosoft.ap.system.rolemanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleRepository;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

@Service
public class RoleSearchServiceImpl implements RoleSearchService {

	@Autowired
	private RoleRepository roleRepository;
	/**
	 * 根据给定参数，查找相关数据，支持多条件查询
	 * @param RoleEntity
	 * @return List<RoleEntity>
	 */
	@Override
	public List<RoleEntity> find(RoleEntity roleEntity) throws Exception {
		return this.roleRepository.select(roleEntity);
	}

	@Override
	public List<RoleEntity> findUserRole(String userId) throws Exception {
		if (StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		return this.roleRepository.selectUserRole(userId);
	}

}
