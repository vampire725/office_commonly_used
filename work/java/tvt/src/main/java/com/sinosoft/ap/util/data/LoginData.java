package com.sinosoft.ap.util.data;

import java.util.List;

import com.sinosoft.ap.system.operationmanage.domain.OperationEntity;
import com.sinosoft.ap.system.organizationmanage.domain.OrganizationEntity;
import com.sinosoft.ap.system.permissionmanage.domain.PermissionEntity;
import com.sinosoft.ap.system.rolemanage.domain.RoleEntity;
import com.sinosoft.ap.system.usermanage.domain.UserEntity;
import com.sinosoft.ap.system.usersettings.domain.UserHeadEntity;

/**
 * 用于封装登录成功后的用户信息以及用户权限信息
 *
 */
public class LoginData {

	private UserEntity userEntity;
	
	private List<RoleEntity> roleEntities;
	
	private List<PermissionEntity> permissionEntities;
	
	private List<OperationEntity> operationEntities;
	
	private UserHeadEntity userHeadEntity;
	
	private OrganizationEntity organizationEntity;

	public OrganizationEntity getOrganizationEntity() {
		return organizationEntity;
	}

	public void setOrganizationEntity(OrganizationEntity organizationEntity) {
		this.organizationEntity = organizationEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public List<RoleEntity> getRoleEntities() {
		return roleEntities;
	}

	public void setRoleEntities(List<RoleEntity> roleEntities) {
		this.roleEntities = roleEntities;
	}

	public List<PermissionEntity> getPermissionEntities() {
		return permissionEntities;
	}

	public void setPermissionEntities(List<PermissionEntity> permissionEntities) {
		this.permissionEntities = permissionEntities;
	}

	public List<OperationEntity> getOperationEntities() {
		return operationEntities;
	}

	public void setOperationEntities(List<OperationEntity> operationEntities) {
		this.operationEntities = operationEntities;
	}

	public UserHeadEntity getUserHeadEntity() {
		return userHeadEntity;
	}

	public void setUserHeadEntity(UserHeadEntity userHeadEntity) {
		this.userHeadEntity = userHeadEntity;
	}
	
	
	
}
