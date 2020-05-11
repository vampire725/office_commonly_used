package com.sinosoft.ap.system.organizationmanage.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/***
 * @since 2017 年  04 月 07 日 01:12:06 
 */
public class OrganizationEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2048891939946767290L;

	@ApiModelProperty(value="机构父节点主键",name="organParentId")
	private String organParentId;

	@ApiModelProperty(value="机构主键",name="organId")
    private String organId;

	@ApiModelProperty(value="机构创建时间",name="organCreatetime")
    private Date organCreatetime;

	@ApiModelProperty(value="机构级别",name="organLevel")
    private Integer organLevel;

	@ApiModelProperty(value="机构分类",name="organSort")
    private Integer organSort;

	@ApiModelProperty(value="机构编码",name="organCode")
    private String organCode;

	@ApiModelProperty(value="机构描述",name="organDesc")
    private String organDesc;

	@ApiModelProperty(value="机构名字",name="organName")
    private String organName;

	@ApiModelProperty(value="机构路径",name="organPath")
    private String organPath;

    public String getOrganPath() {
        return organPath;
    }

    public void setOrganPath(String organPath) {
        this.organPath = organPath;
    }

    public void setOrganParentId(String organParentId) {
        this.organParentId = organParentId;
    }

    public String getOrganParentId() {
        return this.organParentId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getOrganId() {
        return this.organId;
    }

    public void setOrganCreatetime(Date organCreatetime) {
        this.organCreatetime = organCreatetime;
    }

    public Date getOrganCreatetime() {
        return this.organCreatetime;
    }

    public void setOrganLevel(Integer organLevel) {
        this.organLevel = organLevel;
    }

    public Integer getOrganLevel() {
        return this.organLevel;
    }

    public void setOrganSort(Integer organSort) {
        this.organSort = organSort;
    }

    public Integer getOrganSort() {
        return this.organSort;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getOrganCode() {
        return this.organCode;
    }

    public void setOrganDesc(String organDesc) {
        this.organDesc = organDesc;
    }

    public String getOrganDesc() {
        return this.organDesc;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getOrganName() {
        return this.organName;
    }

}