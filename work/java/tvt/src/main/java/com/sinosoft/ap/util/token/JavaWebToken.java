package com.sinosoft.ap.util.token;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sinosoft.ap.common.exception.NullAttributeException;
import com.sinosoft.ap.component.authority.exception.AuthException;
import com.sinosoft.ap.util.result.ResultConstant;
import com.sinosoft.ap.util.string.StringUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JavaWebToken{

	private static Logger logger = LogManager.getLogger(JavaWebToken.class);
	
//	@Autowired
//	private UserInfoForTokenService userInfoForTokenService;
	
	/**
	 * 初始化jwt头消息
	 * @return
	 */
	public Map<String, Object> initHeader() {
		Map<String, Object> header = new HashMap<>();
		Date date = new Date();
		header.put(HeaderConstant.ISS, "中科软科技股份有限公司");
		header.put(HeaderConstant.IAT, date.getTime());
		return header;
	}

	// 该方法使用HS256算法和Secret:secret生成signKey
	private static Key getKeyInstance(String secret) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
		Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
		return signingKey;
	}
	// 解析Token，同时也能验证Token，当验证失败返回null
	public Map<String, Object> parserJavaWebToken(String jwt) throws Exception {
//		UserInfoForToken userInfoForToken = userInfoForTokenService.select(jwt);
		if(StringUtil.checkNull(jwt)) {
			throw new AuthException(ResultConstant.UN_LOGIN);
		}
		String key = "15620952172";
//		String state = userInfoForToken.getTokenState();
		Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance(key)).parseClaimsJws(jwt).getBody();
//		if (state.equals(HeaderConstant.UNUSE)) {
//			throw new AuthException(HeaderConstant.TOKEN_UN_USE);
//		}
//		Date lastUseTime = userInfoForToken.getLastUseTime();
//		logger.info("距上次登录："+convertTime(new Date().getTime() - lastUseTime.getTime()));
//		userInfoForToken.setTokenInfo(null==jwt?"123":jwt);
//		this.userInfoForTokenService.update(userInfoForToken);
		return jwtClaims;
    }
	
	public Map<String, Object> parserJavaWebToken(String jwt,String key) throws Exception {
//		UserInfoForToken userInfoForToken = userInfoForTokenService.select(jwt);
//		if(null==userInfoForToken) {
//			throw new AuthException(ResultConstant.UN_LOGIN);
//		}
//		String keys = userInfoForToken.getTokenKey();
		String keys = "15620952172";
//		String state = userInfoForToken.getTokenState();
		Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance(keys)).parseClaimsJws(jwt).getBody();
//		if (state.equals(HeaderConstant.UNUSE)) {
//			throw new AuthException(HeaderConstant.TOKEN_UN_USE);
//		}
//		Date lastUseTime = userInfoForToken.getLastUseTime();
//		logger.info("距上次登录："+convertTime(new Date().getTime() - lastUseTime.getTime()));
//		userInfoForToken.setTokenInfo(null==jwt?"123":jwt);
//		this.userInfoForTokenService.update(userInfoForToken);
		return jwtClaims;
    }
	
	private String convertTime(Long long1) {
		Long dd = 24*60*60*1000L;
		Long HH = 60*60*1000L;
		Long mm = 60*1000L;
		Long ss = 1000L;
		int day = (int) (long1/dd);
		Long temp  = long1%dd;
		int hou = (int) (temp/HH);
		temp = temp%HH;
		int minu = (int) (temp/mm);
		temp = temp%mm;
		int sou = (int) (temp/ss);
		return (day+"天"+hou+"小时"+minu+"分钟"+sou+"秒").replace("-", "");
	}
	/**
	 * 输入token头、有效载荷、密钥生成token
	 * 
	 * @param header
	 * @param claim
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public String createToken(Map<String, Object> header, Map<String, Object> claim) throws Exception {
		String key = "15620952172";
		String token = Jwts.builder().setHeader(header).setClaims(claim)
				.signWith(SignatureAlgorithm.HS256, getKeyInstance(key)).compact();
//		UserInfoForToken userInfoForToken = new UserInfoForToken();
//		userInfoForToken.setLastUseTime(new Date());
//		userInfoForToken.setPublicTime(new Date());
//		userInfoForToken.setTokenInfo(token);
//		userInfoForToken.setTokenState(HeaderConstant.USE);
//		userInfoForToken.setTokenKey(key);
//		this.userInfoForTokenService.insert(userInfoForToken);
		return token;
	}
	
	public void delete(String token) throws Exception{
		if(StringUtil.checkNull(token)) {
			throw new NullAttributeException("");
		}
//		this.userInfoForTokenService.delete(token);
	}

}