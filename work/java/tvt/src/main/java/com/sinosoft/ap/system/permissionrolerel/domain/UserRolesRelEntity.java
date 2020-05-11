package com.sinosoft.ap.system.permissionrolerel.domain;

import java.util.List;

public class UserRolesRelEntity {

	private String userId;
	
	private List<String> rolesId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getRolesId() {
		return rolesId;
	}

	public void setRolesId(List<String> rolesId) {
		this.rolesId = rolesId;
	}
	
	
}
