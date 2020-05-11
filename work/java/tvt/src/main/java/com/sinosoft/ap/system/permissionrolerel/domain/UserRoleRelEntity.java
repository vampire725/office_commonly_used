package com.sinosoft.ap.system.permissionrolerel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class UserRoleRelEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4673707985503768243L;

	@ApiModelProperty(value="用户主键",name="userId")
	private String userId;
	
	@ApiModelProperty(value="角色主键",name="roleId")
    private String roleId;
	

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }
    public String getRoleId(){
        return this.roleId;
    }

}