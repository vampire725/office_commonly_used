package com.sinosoft.ap.system.loginmanage.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginEntity;
import com.sinosoft.ap.system.loginmanage.domain.UserLoginRepository;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

/***
 * @since 2017 年  04 月 07 日 01:16:39 
 */
 @Service
public class UserLoginServiceImpl implements UserLoginService{

	@Autowired
	private UserLoginRepository userLoginRepository;
	
    /**
     * 根据给定参数，查找相关数据，支持多条件查询
     * @param UserLoginEntity
     * @return List<UserLoginEntity>
     */
	@Override
	public List<UserLoginEntity> find(UserLoginEntity UserLoginEntity) throws Exception {
		
		return this.userLoginRepository.select(UserLoginEntity);
	}

	@Override
	public List<PermissionEntity> findUserOrganPermission(String userId) throws Exception {
		if (StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		return this.userLoginRepository.selectUserOrganPermission(userId);
	}

	@Override
	public List<PermissionEntity> findUserPermission(String userId) throws Exception {
		if (StringUtil.checkNull(userId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		List<PermissionEntity> list = this.userLoginRepository.selectUserRolePermission(userId);
		list.addAll(this.userLoginRepository.selectUserOwnPermission(userId));
		list.addAll(this.userLoginRepository.selectUserOrganPermission(userId));
		return list;
	}

	
}