package com.sinosoft.ap.system.token.service;

import com.sinosoft.ap.system.token.domain.UserInfoForToken;

public interface UserInfoForTokenService {

	void delete(String token) throws Exception;
	
	UserInfoForToken update(UserInfoForToken userInfoForToken) throws Exception;
	
	UserInfoForToken select(String token) throws Exception;
	
	UserInfoForToken insert(UserInfoForToken userInfoForToken) throws Exception;
	
}
