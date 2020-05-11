package com.sinosoft.ap.system.permissionrolerel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class PermissionRoleRelEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7026064985726518022L;

	@ApiModelProperty(value="角色主键",name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="许可主键",name="permissionId")
    private String permissionId;
	

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }
    public String getRoleId(){
        return this.roleId;
    }

    public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }
    public String getPermissionId(){
        return this.permissionId;
    }

}