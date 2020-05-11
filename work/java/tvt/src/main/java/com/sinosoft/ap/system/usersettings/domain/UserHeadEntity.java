package com.sinosoft.ap.system.usersettings.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:14:51 
 */
public class UserHeadEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7008420209963765342L;

	@ApiModelProperty(value="用户主键",name="userId")
	private String userId;
	
	@ApiModelProperty(value="用户头像",name="userHeadId")
    private String userHeadId;
	
	@ApiModelProperty(value="用户头像地址",name="userHeadAddress")
    private String userHeadAddress;
	
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
	
    public void setUserHeadId(String userHeadId){
        this.userHeadId = userHeadId;
    }
    public String getUserHeadId(){
        return this.userHeadId;
    }
	
    public void setUserHeadAddress(String userHeadAddress){
        this.userHeadAddress = userHeadAddress;
    }
    public String getUserHeadAddress(){
        return this.userHeadAddress;
    }

}