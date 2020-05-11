package com.sinosoft.ap.system.organizationmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 用于查询组织机构树
 */
public class UserOrganizationTree implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -913632907160601962L;

	@ApiModelProperty(value="父节点主键",name="parentId")
	private String parentId;

	@ApiModelProperty(value="主键",name="id")
    private String id;

	@ApiModelProperty(value="创建时间",name="reatetime")
    private Date reatetime;

	@ApiModelProperty(value="机构分类",name="organSort")
    private Integer organSort;

	@ApiModelProperty(value="编码",name="code")
    private String code;
	
	@ApiModelProperty(value="描述",name="desc")
    private String desc;

	@ApiModelProperty(value="名称",name="name")
    private String name;

	@ApiModelProperty(value="类型",name="type")
    private String type;

	@ApiModelProperty(value="路径",name="desc")
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReatetime() {
        return reatetime;
    }

    public void setReatetime(Date reatetime) {
        this.reatetime = reatetime;
    }

    public Integer getOrganSort() {
        return organSort;
    }

    public void setOrganSort(Integer organSort) {
        this.organSort = organSort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
