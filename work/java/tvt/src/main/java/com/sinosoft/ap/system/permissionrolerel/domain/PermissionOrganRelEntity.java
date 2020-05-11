package com.sinosoft.ap.system.permissionrolerel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class PermissionOrganRelEntity implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5497359231902565068L;

	@ApiModelProperty(value="机构主键",name="organId")
	private String organId;
	
	@ApiModelProperty(value="许可主键",name="permissionId")
    private String permissionId;
	

    public void setOrganId(String organId){
        this.organId = organId;
    }
    public String getOrganId(){
        return this.organId;
    }

    public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }
    public String getPermissionId(){
        return this.permissionId;
    }

}