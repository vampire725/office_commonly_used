package com.sinosoft.ap.util.token;

/**
 * token声明信息Key
 * @author 高秀和
 *
 */
public class HeaderConstant {

	/**签发单位*/
	public static final String ISS = "ISS";
	/**面向用户*/
	public static final String SUB = "SUB";
	/**接受单位*/
	public static final String AUD = "AUD";
	/**过期时间*/
	public static final String EXP = "EXP";
	/**生效时间*/
	public static final String NBF = "NBF";
	/**签发时间*/
	public static final String IAT = "IAT";
	/**身份证*/
	public static final String JTI = "JTI";
	/**token状态-有效*/
	public static final String USE = "1";
	/**token状态-无效*/
	public static final String UNUSE = "2";
	/**token过期*/
	public static final String TOKEN_UN_USE = "TOKEN_UN_USE";
}
