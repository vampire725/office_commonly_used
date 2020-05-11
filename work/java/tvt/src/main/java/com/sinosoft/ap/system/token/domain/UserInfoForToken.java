package com.sinosoft.ap.system.token.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

public class UserInfoForToken implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2559902141208985542L;

	@ApiModelProperty(value="状态",name="state")
	private Integer state;

	@ApiModelProperty(value="令牌信息",name="tokenInfo")
	private String tokenInfo;

	@ApiModelProperty(value="令牌私钥",name="tokenKey")
	private String tokenKey;
	
	@ApiModelProperty(value="令牌状态",name="tokenState")
	private String tokenState;
	
	@ApiModelProperty(value="令牌发布时间",name="tokenState")
	private Date publicTime;
	
	@ApiModelProperty(value="令牌最后使用时间",name="tokenState")
	private Date lastUseTime;

	public Integer getState() {
		return state;
	}

	public String getTokenInfo() {
		return tokenInfo;
	}

	public String getTokenKey() {
		return tokenKey;
	}

	public String getTokenState() {
		return tokenState;
	}

	public Date getPublicTime() {
		return publicTime;
	}

	public Date getLastUseTime() {
		return lastUseTime;
	}

	public UserInfoForToken setState(Integer state) {
		this.state = state;
		return this;
	}

	public UserInfoForToken setTokenInfo(String tokenInfo) {
		this.tokenInfo = tokenInfo;
		return this;
	}

	public UserInfoForToken setTokenKey(String tokenKey) {
		this.tokenKey = tokenKey;
		return this;
	}

	public UserInfoForToken setTokenState(String tokenState) {
		this.tokenState = tokenState;
		return this;
	}

	public UserInfoForToken setPublicTime(Date publicTime) {
		this.publicTime = publicTime;
		return this;
	}

	public UserInfoForToken setLastUseTime(Date lastUseTime) {
		this.lastUseTime = lastUseTime;
		return this;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
	
	public boolean isActive(){
		boolean flg = (null==this.tokenInfo);
//		System.out.println(flg);
		return flg;
	}
	
}
