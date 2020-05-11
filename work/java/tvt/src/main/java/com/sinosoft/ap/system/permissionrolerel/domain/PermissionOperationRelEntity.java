package com.sinosoft.ap.system.permissionrolerel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:42 
 */
public class PermissionOperationRelEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7299589058526004318L;
	
	@ApiModelProperty(value="操作主键",name="operetionId")
	private String operetionId;
	
	@ApiModelProperty(value="许可主键",name="permissionId")
    private String permissionId;
	

    public void setOperetionId(String operetionId){
        this.operetionId = operetionId;
    }
    public String getOperetionId(){
        return this.operetionId;
    }

    public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }
    public String getPermissionId(){
        return this.permissionId;
    }

}