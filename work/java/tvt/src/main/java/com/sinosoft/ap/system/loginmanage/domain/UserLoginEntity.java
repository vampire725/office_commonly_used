package com.sinosoft.ap.system.loginmanage.domain;

import java.io.Serializable;

import com.sinosoft.ap.system.usermanage.domain.UserEntity;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:16:39 
 */
public class UserLoginEntity extends UserEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="用户主键",name="userId")
	private String userId;
	
	@ApiModelProperty(value="用户密码",name="userPassword")
    private String userPassword;
	
	@ApiModelProperty(value="用户电话",name="userTelnumber")
    private String userTelnumber;
	
	@ApiModelProperty(value="用户编码",name="userIdCode")
    private String userIdCode;
	
	@ApiModelProperty(value="用户账户",name="userAccount")
    private String userAccount;
	
	@ApiModelProperty(value="用户邮箱",name="userMail")
    private String userMail;
	
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
	
    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
    public String getUserPassword(){
        return this.userPassword;
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

}