package com.sinosoft.ap.util.data;

import java.util.ArrayList;

/**
 * 用于封装用户或机构的权限信息
 *
 */
public class Data {
	
	private Object permissionList;
	
	private Object operationList;

	private Object roleList;

	public Data() {
		this.permissionList = new ArrayList<>();
		this.operationList = new ArrayList<>();
		this.roleList = new ArrayList<>();
	}
	
	public Object getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(Object permissionList) {
		this.permissionList = permissionList;
	}

	public Object getOperationList() {
		return operationList;
	}

	public void setOperationList(Object operationList) {
		this.operationList = operationList;
	}

	public Object getRoleList() {
		return roleList;
	}

	public void setRoleList(Object roleList) {
		this.roleList = roleList;
	}
	
	
	
}
