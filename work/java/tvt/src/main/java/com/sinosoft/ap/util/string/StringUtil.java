package com.sinosoft.ap.util.string;

import sun.misc.BASE64Encoder;

public class StringUtil {

    /**
     * 判断字符串是否为空，是否为空字符串，true 代表是；
     *
     * @param str
     * @return
     */
    public static boolean checkNull(String str) {

        if (str != null && !str.trim().isEmpty() && !str.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串为null返回""
     * 不为null直接返回
     *
     * @param string
     * @return String
     * @throws
     * @author HongyanShan
     * @date 2017/10/24 13:44
     */
    public static String returnString(String string) {
        if (checkNull(string)) {
            return "";
        }
        return string;
    }

    /**
     * 随机生成一定位数的字符串
     * @param symbol
     * @param length
     * @return
     */
    public static String randomStrFromStr(String symbol,int length){
        String KeyString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();
        for (int i = 0; i < length; i++) {
            sb.append(KeyString.charAt((int) Math.round(Math.random() * (len - 1))));
        }
        if(symbol.equals("")){
            return sb.toString();
        }
        return symbol+"_"+sb.toString();
    }

    public static String StrToBase64(String str) throws Exception{
        String symbolBase64Str = new BASE64Encoder().encode(str.getBytes("UTF-8"));
        return symbolBase64Str;
    }

    public static String Base64ToStr(String str) throws Exception{
        byte[] bt = null;
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        bt = decoder.decodeBuffer( str );
        return new String(bt);
    }

}
