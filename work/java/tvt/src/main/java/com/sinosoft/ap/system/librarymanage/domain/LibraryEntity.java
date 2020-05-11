package com.sinosoft.ap.system.librarymanage.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

/***
 * @since 2017 年  04 月 07 日 01:09:54 
 */
public class LibraryEntity implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8021970421244441794L;

	@ApiModelProperty(value="数据字典主键",name="libraryId")
	private String libraryId;
	
	@ApiModelProperty(value="数据字典描述",name="libraryDesc")
    private String libraryDesc;
	
	@ApiModelProperty(value="数据字典编码",name="libraryCode")
    private String libraryCode;
	
	@ApiModelProperty(value="数据字典名称",name="libraryName")
    private String libraryName;

    @ApiModelProperty(value="数据字典层级",name="libraryLevel")
    private String libraryLevel;

    @ApiModelProperty(value="数据字典标识",name="librarySymbol")
    private String librarySymbol;
	
//	@ApiModelProperty(value="创建时间",name="libraryCreatetime",example="2017/11/23-16:31:55")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date libraryCreatetime;
	
	@ApiModelProperty(value="数据字典父级主键",name="libraryParentId")
    private String libraryParentId;
	
    public void setLibraryId(String libraryId){
        this.libraryId = libraryId;
    }
    public String getLibraryId(){
        return this.libraryId;
    }
	
    public void setLibraryDesc(String libraryDesc){
        this.libraryDesc = libraryDesc;
    }
    public String getLibraryDesc(){
        return this.libraryDesc;
    }

    public void setLibraryCode(String libraryCode){
        this.libraryCode = libraryCode;
    }
    public String getLibraryCode(){
        return this.libraryCode;
    }

    public void setLibraryName(String libraryName){
        this.libraryName = libraryName;
    }
    public String getLibraryName(){
        return this.libraryName;
    }

    public void setLibraryCreatetime(Date libraryCreatetime){
        this.libraryCreatetime = libraryCreatetime;
    }
    public Date getLibraryCreatetime(){
        return this.libraryCreatetime;
    }
	public String getLibraryParentId() {
		return libraryParentId;
	}
	public void setLibraryParentId(String libraryParentId) {
		this.libraryParentId = libraryParentId;
	}
	
	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}

    public String getLibraryLevel() {
        return libraryLevel;
    }

    public void setLibraryLevel(String libraryLevel) {
        this.libraryLevel = libraryLevel;
    }

    public String getLibrarySymbol() {
        return librarySymbol;
    }

    public void setLibrarySymbol(String librarySymbol) {
        this.librarySymbol = librarySymbol;
    }
}