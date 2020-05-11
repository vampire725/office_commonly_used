package com.sinosoft.ap.system.operationmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:11:50 
 */
public class OperationEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7674362787328029197L;

	@ApiModelProperty(value="资源主键",name="resourceId")
	private String resourceId;
	
	@ApiModelProperty(value="操作主键",name="operationId")
    private String operationId;
	
	@ApiModelProperty(value="操作名称",name="operationName")
    private String operationName;
	
	@ApiModelProperty(value="操作时间",name="operationTime")
    private Date operationTime;
	
	@ApiModelProperty(value="操作描述",name="operationDesc")
    private String operationDesc;
    
	@ApiModelProperty(value="操作编码",name="operationCode")
    private String operationCode;
    
	@ApiModelProperty(value="许可主键",name="permissionId")
    private String permissionId;
	
    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }
    public String getResourceId(){
        return this.resourceId;
    }
	
    public void setOperationId(String operationId){
        this.operationId = operationId;
    }
    public String getOperationId(){
        return this.operationId;
    }
	
    public void setOperationName(String operationName){
        this.operationName = operationName;
    }
    public String getOperationName(){
        return this.operationName;
    }

    public void setOperationTime(Date operationTime){
        this.operationTime = operationTime;
    }
    public Date getOperationTime(){
        return this.operationTime;
    }

    public void setOperationDesc(String operationDesc){
        this.operationDesc = operationDesc;
    }
    public String getOperationDesc(){
        return this.operationDesc;
    }
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
    
    

}