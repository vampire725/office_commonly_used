package com.sinosoft.ap.system.userorganizationrel.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class UserOrganizationRelEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2960844043131032312L;
	
	@ApiModelProperty(value="机构主键",name="organId")
	private String organId;
	
	@ApiModelProperty(value="用户主键",name="userId")
    private String userId;
	

    public void setOrganId(String organId){
        this.organId = organId;
    }
    public String getOrganId(){
        return this.organId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }

}