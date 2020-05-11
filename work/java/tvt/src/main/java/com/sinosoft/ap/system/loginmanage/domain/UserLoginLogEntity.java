package com.sinosoft.ap.system.loginmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
public class UserLoginLogEntity implements Serializable{

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5065659351433532502L;
	
	@ApiModelProperty(value="用户登陆主键",name="userLoginLogId")
	private String userLoginLogId;

	@ApiModelProperty(value="用户主键",name="userId")
    private String userId;

	@ApiModelProperty(value="登录时间",name="loginTime")
    private Date loginTime;

	@ApiModelProperty(value="用户名",name="loginUser")
    private String loginUser;
	
	@ApiModelProperty(value="用户登陆ip",name="loginIp")
    private String loginIp;
	
	@ApiModelProperty(value="用户操作系统",name="loginOs")
    private String loginOs;
	
	@ApiModelProperty(value="用户登陆浏览器",name="loginBrower")
    private String loginBrower;
	
    public void setUserLoginLogId(String userLoginLogId){
        this.userLoginLogId = userLoginLogId;
    }
    public String getUserLoginLogId(){
        return this.userLoginLogId;
    }
	
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
	
    public void setLoginTime(Date loginTime){
        this.loginTime = loginTime;
    }
    public Date getLoginTime(){
        return this.loginTime;
    }

    public void setLoginUser(String loginUser){
        this.loginUser = loginUser;
    }
    public String getLoginUser(){
        return this.loginUser;
    }

    public void setLoginIp(String loginIp){
        this.loginIp = loginIp;
    }
    public String getLoginIp(){
        return this.loginIp;
    }

    public void setLoginOs(String loginOs){
        this.loginOs = loginOs;
    }
    public String getLoginOs(){
        return this.loginOs;
    }

    public void setLoginBrower(String loginBrower){
        this.loginBrower = loginBrower;
    }
    public String getLoginBrower(){
        return this.loginBrower;
    }

}