package com.sinosoft.ap.system.rolemanage.domain;

import java.util.List;

public class RoleClassEntity {

	private String classId;
	
	private List<String> roleId;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public List<String> getRoleId() {
		return roleId;
	}

	public void setRoleId(List<String> roleId) {
		this.roleId = roleId;
	}
	
	
}

