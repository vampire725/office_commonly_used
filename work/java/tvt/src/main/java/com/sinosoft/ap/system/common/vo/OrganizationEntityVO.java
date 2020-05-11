package com.sinosoft.ap.system.common.vo;

import java.util.Date;

public class OrganizationEntityVO {

	private String organParentId;

    private String organId;

    private Date organCreatetime;

    private Integer organLevel;

    private Integer organSort;

    private String organCode;

    private String organDesc;

    private String organName;

    private String organPath;
    
    private String parentOrganCode;

	public String getOrganParentId() {
		return organParentId;
	}

	public void setOrganParentId(String organParentId) {
		this.organParentId = organParentId;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public Date getOrganCreatetime() {
		return organCreatetime;
	}

	public void setOrganCreatetime(Date organCreatetime) {
		this.organCreatetime = organCreatetime;
	}

	public Integer getOrganLevel() {
		return organLevel;
	}

	public void setOrganLevel(Integer organLevel) {
		this.organLevel = organLevel;
	}

	public Integer getOrganSort() {
		return organSort;
	}

	public void setOrganSort(Integer organSort) {
		this.organSort = organSort;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganDesc() {
		return organDesc;
	}

	public void setOrganDesc(String organDesc) {
		this.organDesc = organDesc;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganPath() {
		return organPath;
	}

	public void setOrganPath(String organPath) {
		this.organPath = organPath;
	}

	public String getParentOrganCode() {
		return parentOrganCode;
	}

	public void setParentOrganCode(String parentOrganCode) {
		this.parentOrganCode = parentOrganCode;
	}
    
    
}
