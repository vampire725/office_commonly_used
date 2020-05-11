package com.sinosoft.ap.system.token.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.system.token.domain.UserInfoForToken;
import com.sinosoft.ap.util.string.StringUtil;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.json.JSONObject;
/**
 * 
 * @author : 高秀和
 * @date : 2017年11月20日-下午11:22:02
 * @since : V1.0
 * @version : V1.0
 */
@Service
public class UserInfoForTokenServiceImpl implements UserInfoForTokenService{
	
	private static Logger logger = LogManager.getLogger(UserInfoForTokenServiceImpl.class);
	
	private static String key = "15620952172";
	
//	@Autowired
//	private CacheManager cacheManager;
	
	private Cache cache;

//	@PostConstruct
//	private void init(){
//		this.cache = this.cacheManager.getCache("token");
//	}
	@Override
	public void delete(String token) throws Exception{
		cache.remove(token);
	}

	@Override
	public UserInfoForToken update(UserInfoForToken userInfoForToken) throws Exception{
		String token = userInfoForToken.getTokenInfo();
		userInfoForToken.setLastUseTime(new Date());
		Element element = new Element(token, userInfoForToken);
		cache.put(element);
		logger.info("有用户访问，授权信息为："+JSONObject.fromObject(userInfoForToken));
		return userInfoForToken;
	}

	@Override
	public UserInfoForToken select(String token) throws Exception{
		Element element = cache.get(token);
		UserInfoForToken userInfoForTokens = new UserInfoForToken();
		if(null==element) {
			userInfoForTokens.setTokenInfo(token);
			userInfoForTokens.setTokenKey(key);
			userInfoForTokens.setTokenState("1");
			Element element2 = new Element(token, userInfoForTokens);
			cache.put(element);
		}
//		UserInfoForToken userInfoForToken = (UserInfoForToken) element.getObjectValue();
//		UserInfoForToken userInfoForToken = new UserInfoForToken();
//		userInfoForToken.setTokenKey(key);
		userInfoForTokens.setState(1);
		logger.info("有用户访问，授权信息为："+JSONObject.fromObject(userInfoForTokens));
		if(null!=userInfoForTokens){
			logger.info("访问通过，授权信息为："+JSONObject.fromObject(userInfoForTokens));
			return userInfoForTokens;
		} else {
			return null;
		}
	}

	@Override
	public UserInfoForToken insert(UserInfoForToken userInfoForToken) throws Exception{
		String token = userInfoForToken.getTokenInfo();
		logger.info("有用户登录，授权信息为："+JSONObject.fromObject(userInfoForToken));
		if (StringUtil.checkNull(token)) {
			return null;
		} 
		userInfoForToken.setLastUseTime(new Date());
		userInfoForToken.setPublicTime(new Date());
		Element element = new Element(token, userInfoForToken);
		cache.put(element);
		logger.info("用户访问成功，授权信息为："+JSONObject.fromObject(userInfoForToken));
		return userInfoForToken;
	}

}
