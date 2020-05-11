package com.sinosoft.ap.system.resourcemanage.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:13:13 
 */
public class ResourceIconEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3990502158976021732L;

	@ApiModelProperty(value="资源图标主键",name="resourceIconId")
	private String resourceIconId;
	
	@ApiModelProperty(value="资源图标地址",name="resourceIconAddress")
    private String resourceIconAddress;
	
	@ApiModelProperty(value="资源图标名称",name="resourceIconName")
    private String resourceIconName;
	
    public void setResourceIconId(String resourceIconId){
        this.resourceIconId = resourceIconId;
    }
    public String getResourceIconId(){
        return this.resourceIconId;
    }
	
    public void setResourceIconAddress(String resourceIconAddress){
        this.resourceIconAddress = resourceIconAddress;
    }
    public String getResourceIconAddress(){
        return this.resourceIconAddress;
    }

    public void setResourceIconName(String resourceIconName){
        this.resourceIconName = resourceIconName;
    }
    public String getResourceIconName(){
        return this.resourceIconName;
    }

}