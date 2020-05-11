package com.sinosoft.ap.system.resourcemanage.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:39:43 
 */
public class ResourceIconRelEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4656121314369338700L;

	@ApiModelProperty(value="资源主键",name="resourceId")
	private String resourceId;
	
	@ApiModelProperty(value="资源图标主键",name="resourceId")
    private String resourceIconId;
	

    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }
    public String getResourceId(){
        return this.resourceId;
    }

    public void setResourceIconId(String resourceIconId){
        this.resourceIconId = resourceIconId;
    }
    public String getResourceIconId(){
        return this.resourceIconId;
    }

}