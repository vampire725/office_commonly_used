package com.sinosoft.ap.system.token.domain;

import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoForTokenRepository {

	void insert (UserInfoForToken userInfoForToken) throws Exception;
	
	Integer update(UserInfoForToken userInfoForToken) throws Exception;
	
	Integer delete(String token) throws Exception ;
	
	UserInfoForToken select(String tokenInfo) throws Exception;
}
