package com.sinosoft.ap.system.resourcemanage.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ResourceVO extends ResourceEntity implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1855934718602739315L;

	@ApiModelProperty(value="资源图标主键",name="resourceIconId")
	private String resourceIconId;
	
	@ApiModelProperty(value="资源图标地址",name="resourceIconAddress")
    private String resourceIconAddress;

	public String getResourceIconId() {
		return resourceIconId;
	}

	public void setResourceIconId(String resourceIconId) {
		this.resourceIconId = resourceIconId;
	}

	public String getResourceIconAddress() {
		return resourceIconAddress;
	}

	public void setResourceIconAddress(String resourceIconAddress) {
		this.resourceIconAddress = resourceIconAddress;
	}
    
}
