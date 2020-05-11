package com.sinosoft.ap.system.usermanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
public class UserEntity extends UserDetailsEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1362310667160617014L;

	@ApiModelProperty(value="用户主键",name="userId")
	private String userId;
	
    private Date userCreatetime;
	
	@ApiModelProperty(value="用户电话号码",name="userTelnumber")
    private String userTelnumber;
	
	@ApiModelProperty(value="用户身份证",name="userIdCode")
    private String userIdCode;
	
	@ApiModelProperty(value="用户账号",name="userAccount")
    private String userAccount;
	
	@ApiModelProperty(value="用户邮箱",name="userMail")
    private String userMail;
    
	@ApiModelProperty(value="机构主键",name="organId")
    private String organId;
	
	private String macAddr;//用户绑定mac地址
	
	private Integer userStatus;//用户状态（1正常2带补录3黑名单）
	private String organCode;
	private String organName;
	@ApiModelProperty(value="备用字段",name="backField")
	private String backField;
	
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public String getMacAddr() {
		return macAddr;
	}
	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}
	public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
	
    public void setUserCreatetime(Date userCreatetime){
        this.userCreatetime = userCreatetime;
    }
    public Date getUserCreatetime(){
        return this.userCreatetime;
    }

    public void setUserTelnumber(String userTelnumber){
        this.userTelnumber = userTelnumber;
    }
    public String getUserTelnumber(){
        return this.userTelnumber;
    }

    public void setUserIdCode(String userIdCode){
        this.userIdCode = userIdCode;
    }
    public String getUserIdCode(){
        return this.userIdCode;
    }

    public void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }
    public String getUserAccount(){
        return this.userAccount;
    }

    public void setUserMail(String userMail){
        this.userMail = userMail;
    }
    public String getUserMail(){
        return this.userMail;
    }
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}

    public String getBackField() {
        return backField;
    }

    public void setBackField(String backField) {
        this.backField = backField;
    }
}