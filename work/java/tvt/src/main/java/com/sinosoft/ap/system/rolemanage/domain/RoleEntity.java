package com.sinosoft.ap.system.rolemanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:14:00 
 */
public class RoleEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4861163082382712998L;

	@ApiModelProperty(value="角色主键",name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="角色描述",name="roleDesc")
    private String roleDesc;
	
	@ApiModelProperty(value="角色创建时间",name="roleCreatetime")
    private Date roleCreatetime;
	
	@ApiModelProperty(value="角色姓名",name="roleName")
    private String roleName;
	
	@ApiModelProperty(value="角色编码",name="roleCode")
    private String roleCode;
	
    public void setRoleId(String roleId){
        this.roleId = roleId;
    }
    public String getRoleId(){
        return this.roleId;
    }
	
    public void setRoleDesc(String roleDesc){
        this.roleDesc = roleDesc;
    }
    public String getRoleDesc(){
        return this.roleDesc;
    }

    public void setRoleCreatetime(Date roleCreatetime){
        this.roleCreatetime = roleCreatetime;
    }
    public Date getRoleCreatetime(){
        return this.roleCreatetime;
    }

    public void setRoleName(String roleName){
        this.roleName = roleName;
    }
    public String getRoleName(){
        return this.roleName;
    }

    public void setRoleCode(String roleCode){
        this.roleCode = roleCode;
    }
    public String getRoleCode(){
        return this.roleCode;
    }

}