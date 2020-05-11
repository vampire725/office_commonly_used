package com.sinosoft.ap.system.usermanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author gxh
 * @since 2017年5月17日11:29:08
 *
 */
public class UserDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1207304465354628341L;

	@ApiModelProperty(value="用户详情",name="publicTime")
	private String  userDetailsId;
	
	@ApiModelProperty(value="用户座机",name="publicTime")
    private String userDomicile;
	
    private Date lastUpdateTime;
	
    private Date userBirth;
	
	@ApiModelProperty(value="用户主键",name="publicTime")
    private String userId;
	
	@ApiModelProperty(value="用户姓名",name="publicTime")
    private String userName;
	
	@ApiModelProperty(value="用户地址",name="publicTime")
    private String userAddress;
	
	@ApiModelProperty(value="用户职位",name="publicTime")
    private String userPosition;
	
	@ApiModelProperty(value="用户民族",name="publicTime")
    private String userNation;
	
	@ApiModelProperty(value="身份证",name="publicTime")
    private String idCard;
	
	@ApiModelProperty(value="用户性别",name="publicTime")
    private String userGender;
	
	@ApiModelProperty(value="正序排序",name="publicTime")
    private String mainsort;
    
	@ApiModelProperty(value="倒序排序",name="publicTime")
    private String backsort;
	
	public void setUserDetailsId(String userDetailsId){
        this.userDetailsId = userDetailsId;
    }
    public String getUserDetailsId(){
        return this.userDetailsId;
    }
    public void setUserDomicile(String userDomicile){
        this.userDomicile = userDomicile;
    }
    public String getUserDomicile(){
        return this.userDomicile;
    }

    public void setLastUpdateTime(Date lastUpdateTime){
        this.lastUpdateTime = lastUpdateTime;
    }
    public Date getLastUpdateTime(){
        return this.lastUpdateTime;
    }

    public void setUserBirth(Date userBirth){
        this.userBirth = userBirth;
    }
    public Date getUserBirth(){
        return this.userBirth;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }

    public void setUserAddress(String userAddress){
        this.userAddress = userAddress;
    }
    public String getUserAddress(){
        return this.userAddress;
    }

    public void setUserPosition(String userPosition){
        this.userPosition = userPosition;
    }
    public String getUserPosition(){
        return this.userPosition;
    }

    public void setUserNation(String userNation){
        this.userNation = userNation;
    }
    public String getUserNation(){
        return this.userNation;
    }

    public void setIdCard(String idCard){
        this.idCard = idCard;
    }
    public String getIdCard(){
        return this.idCard;
    }

    public void setUserGender(String userGender){
        this.userGender = userGender;
    }
    public String getUserGender(){
        return this.userGender;
    }

    public void setMainsort(String mainsort){
    	this.mainsort = mainsort;
    }
    
    public String getMainsort(){
    	return this.mainsort;
    }
    
    public void setBacksort(String backsort){
    	this.backsort = backsort;
    }
    
    public String getBacksort(){
    	return this.backsort;
    }
}