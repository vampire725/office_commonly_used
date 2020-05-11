package com.sinosoft.ap.system.loginmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:14:52 
 */
public class UserPasswordEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1260403796564101961L;

	@ApiModelProperty(value="用户密码主键",name="userPasswordId")
	private String userPasswordId;
	
	@ApiModelProperty(value="用户主键",name="userId")
    private String userId;
	
	@ApiModelProperty(value="用户密码",name="userPassword")
    private String userPassword;
	
	@ApiModelProperty(value="密码创建时间",name="userPasswordCreatetime")
    private Date userPasswordCreatetime;
	
    public void setUserPasswordId(String userPasswordId){
        this.userPasswordId = userPasswordId;
    }
    public String getUserPasswordId(){
        return this.userPasswordId;
    }
	
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

    public void setUserPasswordCreatetime(Date userPasswordCreatetime){
        this.userPasswordCreatetime = userPasswordCreatetime;
    }
    public Date getUserPasswordCreatetime(){
        return this.userPasswordCreatetime;
    }

}