package com.sinosoft.ap.system.resourcemanage.domain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ResourceOperationTree implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2126803869359393423L;

	@ApiModelProperty(value="资源所属类型",name="resourceBelongType")
	private String resourceBelongType; // 资源所属类型(对应APS_Resource表字段) 2019年1月16日
	
	@ApiModelProperty(value="主键",name="id")
	private String id;

	@ApiModelProperty(value="父节点主键",name="parentId")
    private String parentId;

	@ApiModelProperty(value="资源分类",name="resourceSort")
    private Integer resourceSort;

	@ApiModelProperty(value="名称",name="name")
    private String name;

	@ApiModelProperty(value="地址",name="address")
    private String address;

	@ApiModelProperty(value="资源类型",name="resourceType")
    private String resourceType;

	@ApiModelProperty(value="类型",name="type")
    private String type;

	@ApiModelProperty(value="描述",name="desc")
    private String desc;

	@ApiModelProperty(value="编码",name="code")
    private String code;

	@ApiModelProperty(value="图标",name="icon")
    private String icon;
    
	@ApiModelProperty(value="路径",name="path")
    private String path;
    
	
	
    public String getResourceBelongType() {
		return resourceBelongType;
	}

	public void setResourceBelongType(String resourceBelongType) {
		this.resourceBelongType = resourceBelongType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getResourceSort() {
        return resourceSort;
    }

    public void setResourceSort(Integer resourceSort) {
        this.resourceSort = resourceSort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
