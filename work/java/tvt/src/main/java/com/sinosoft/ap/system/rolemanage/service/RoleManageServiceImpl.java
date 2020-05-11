package com.sinosoft.ap.system.rolemanage.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.system.permissionrolerel.domain.PermissionRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.domain.UserRoleRelEntity;
import com.sinosoft.ap.system.permissionrolerel.service.PermissionRoleRelService;
import com.sinosoft.ap.system.permissionrolerel.service.UserRoleRelService;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleRepository;
import com.sinosoft.ap.util.id.PrimaryKeyUtil;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

/***
 * @since 2017 年  04 月 07 日 01:14:00 
 */
 @Service
public class RoleManageServiceImpl implements RoleManageService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRelService userRoleRelService;
	
	@Autowired
	private PermissionRoleRelService permissionRoleRelService;

	 /**
     * 根据给定的参数新增一条数据
     * @param RoleEntity
     */
	@Override
	public RoleEntity save(RoleEntity roleEntity) throws Exception {
		roleEntity.setRoleCreatetime(new Date());
		roleEntity.setRoleId(PrimaryKeyUtil.create());
		this.roleRepository.insert(roleEntity);
		return roleEntity;
		
	}
	
    /**
     * 根据给定条件删除一条数据，条件可以有多个
     * @param RoleEntity
     */
	@Override
//	@Transactional(readOnly=false,transactionManager="apsystemTransactionManager",rollbackFor = {Exception.class})
	public void remove(RoleEntity roleEntity) throws Exception {
		String roleId = roleEntity.getRoleId();
		if (StringUtil.checkNull(roleId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		UserRoleRelEntity userRoleRelEntity = new UserRoleRelEntity();
		userRoleRelEntity.setRoleId(roleId);
		PermissionRoleRelEntity permissionRoleRelEntity = new PermissionRoleRelEntity();
		permissionRoleRelEntity.setRoleId(roleId);
		this.userRoleRelService.remove(userRoleRelEntity);
		this.permissionRoleRelService.remove(permissionRoleRelEntity);
		this.roleRepository.delete(roleEntity);
		
	}
	
    /**
     * 指定主键，修改给定信息项
     * @param RoleEntity
     */
	@Override
	public void modify(RoleEntity roleEntity) throws Exception {
		String roleId = roleEntity.getRoleId();
		if (StringUtil.checkNull(roleId)) {
			throw new NullAttributeException(ResultConstant.NULL_ATTRIBUTE);
		}
		this.roleRepository.update(roleEntity);
		
	}
	
}