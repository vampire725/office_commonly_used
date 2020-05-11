package com.sinosoft.ap.util.id;

import java.util.UUID;


import com.sinosoft.ap.util.md5.MD5Encrypt;

/**
 * 自定义主键生成策略
 *
 */
public class PrimaryKeyUtil {

	public static String create(){
		
		return MD5Encrypt.encrypt(UUID.randomUUID().toString());
	}
	
}
