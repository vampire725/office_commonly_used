package com.sinosoft.ap.system.permissionrolerel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class UserPermissionRelEntity implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1131798321749829765L;

	@ApiModelProperty(value="用户主键",name="userId")
	private String userId;
	
	@ApiModelProperty(value="许可主键",name="permissionId")
    private String permissionId;
	

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }

    public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }
    public String getPermissionId(){
        return this.permissionId;
    }

}