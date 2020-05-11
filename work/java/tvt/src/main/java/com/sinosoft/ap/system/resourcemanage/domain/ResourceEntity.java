package com.sinosoft.ap.system.resourcemanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:13:01 
 */
public class ResourceEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8958757501691059677L;

	@ApiModelProperty(value="资源所属类型",name="resourceBelongType")
	private String resourceBelongType; // 资源所属类型 2019年1月16日(AP系统中只加在查询接口，统一权限全加)
	
	
	@ApiModelProperty(value="资源主键",name="resourceId")
	private String resourceId;

	@ApiModelProperty(value="资源父节点主键",name="resourceParentId")
    private String resourceParentId;

    private Date resourceCreatetime;

	@ApiModelProperty(value="资源分类",name="resourceSort")
    private Integer resourceSort;

	@ApiModelProperty(value="资源名称",name="resourceName")
    private String resourceName;

	@ApiModelProperty(value="资源地址",name="resourceAddress")
    private String resourceAddress;

	@ApiModelProperty(value="资源级别",name="resourceLevel")
    private Integer resourceLevel;

	@ApiModelProperty(value="资源类型",name="resourceType")
    private String resourceType;

	@ApiModelProperty(value="资源描述",name="resourceDesc")
    private String resourceDesc;

	@ApiModelProperty(value="资源编码",name="resourceCode")
    private String resourceCode;

	@ApiModelProperty(value="资源路径",name="resourcePath")
    private String resourcePath;

	
    public String getResourceBelongType() {
		return resourceBelongType;
	}

	public void setResourceBelongType(String resourceBelongType) {
		this.resourceBelongType = resourceBelongType;
	}

	public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return this.resourceId;
    }

    public void setResourceParentId(String resourceParentId) {
        this.resourceParentId = resourceParentId;
    }

    public String getResourceParentId() {
        return this.resourceParentId;
    }

    public void setResourceCreatetime(Date resourceCreatetime) {
        this.resourceCreatetime = resourceCreatetime;
    }

    public Date getResourceCreatetime() {
        return this.resourceCreatetime;
    }

    public void setResourceSort(Integer resourceSort) {
        this.resourceSort = resourceSort;
    }

    public Integer getResourceSort() {
        return this.resourceSort;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceName() {
        return this.resourceName;
    }

    public void setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
    }

    public String getResourceAddress() {
        return this.resourceAddress;
    }

    public void setResourceLevel(Integer resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public Integer getResourceLevel() {
        return this.resourceLevel;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceType() {
        return this.resourceType;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public String getResourceDesc() {
        return this.resourceDesc;
    }

}