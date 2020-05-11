package com.sinosoft.ap.system.permissionmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:12:20 
 */
public class PermissionEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="许可主键",name="permissionId")
	private String permissionId;
	
	@ApiModelProperty(value="许可编码",name="permissionCode")
    private String permissionCode;
	
	@ApiModelProperty(value="许可创建时间",name="permissionCode")
    private Date permissionCreatetime;
	
	@ApiModelProperty(value="许可名称",name="permissionName")
    private String permissionName;
	
	@ApiModelProperty(value="许可描述",name="permissionDesc")
    private String permissionDesc;
    
	@ApiModelProperty(value="角色主键",name="roleId")
    private String roleId;
    
	@ApiModelProperty(value="类型",name="type")
    private String type;

    @ApiModelProperty(value="特例许可类型",name="type")
    private Integer permissionType;

    @ApiModelProperty(value="许可状态",name="permissionStatus")
    private Integer permissionStatus;

    @ApiModelProperty(value="许可类型",name="permissionSymbol")
    private Integer permissionSymbol;

    @ApiModelProperty(value="许可销毁时间",name="permissionDestroytime")
    private Date permissionDestroytime;

    @ApiModelProperty(value="销毁时间字符串",name="destroyStr")
    private String destroyStr;

    @ApiModelProperty(value="申请人",name="permissionEntryuser")
    private String permissionEntryuser;

    @ApiModelProperty(value="审批备注",name="permissionReason")
    private String permissionReason;
    
    private String resourceId;
	
    public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }
    public String getPermissionId(){
        return this.permissionId;
    }
	
    public void setPermissionCode(String permissionCode){
        this.permissionCode = permissionCode;
    }
    public String getPermissionCode(){
        return this.permissionCode;
    }

    public void setPermissionCreatetime(Date permissionCreatetime){
        this.permissionCreatetime = permissionCreatetime;
    }
    public Date getPermissionCreatetime(){
        return this.permissionCreatetime;
    }

    public void setPermissionName(String permissionName){
        this.permissionName = permissionName;
    }
    public String getPermissionName(){
        return this.permissionName;
    }

    public void setPermissionDesc(String permissionDesc){
        this.permissionDesc = permissionDesc;
    }
    public String getPermissionDesc(){
        return this.permissionDesc;
    }
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

    public Integer getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Integer permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getPermissionStatus() {
        return permissionStatus;
    }

    public void setPermissionStatus(Integer permissionStatus) {
        this.permissionStatus = permissionStatus;
    }

    public Integer getPermissionSymbol() {
        return permissionSymbol;
    }

    public void setPermissionSymbol(Integer permissionSymbol) {
        this.permissionSymbol = permissionSymbol;
    }

    public Date getPermissionDestroytime() {
        return permissionDestroytime;
    }

    public void setPermissionDestroytime(Date permissionDestroytime) {
        this.permissionDestroytime = permissionDestroytime;
    }

    public String getDestroyStr() {
        return destroyStr;
    }

    public void setDestroyStr(String destroyStr) {
        this.destroyStr = destroyStr;
    }

    public String getPermissionEntryuser() {
        return permissionEntryuser;
    }

    public void setPermissionEntryuser(String permissionEntryuser) {
        this.permissionEntryuser = permissionEntryuser;
    }

    public String getPermissionReason() {
        return permissionReason;
    }

    public void setPermissionReason(String permissionReason) {
        this.permissionReason = permissionReason;
    }
}