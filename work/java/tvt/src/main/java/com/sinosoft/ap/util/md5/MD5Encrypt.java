package com.sinosoft.ap.util.md5;

import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
  
public class MD5Encrypt {  
	
	/**
	 * 对字符串进行MD5加密
	 * @param src
	 * @return
	 */
    public static final String encrypt(String src) {  
        //char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };  
  
        byte[] input = src.getBytes();  
  
        try {  
            MessageDigest digest = MessageDigest.getInstance("MD5");  
            digest.update(input);  
            input = digest.digest();  
            int length = input.length;  
            StringBuffer strBuff = new StringBuffer();  
            for(int i = 0;i<length;i++)  
            {  
                //将字符转变成对应的ASSIC值  
                int val = ((int)input[i])&0xff;  
                //转变成对应的值后小于4位  
                if(val<16)  
                {  
                    strBuff.append("0");  
                }  
                strBuff.append(Integer.toHexString(val));  
//              strBuff.append(hexDigits[val%16]);  
            }  
              
            return strBuff.toString();  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
  
        return src;  
    }

    public static void main(String[] args) {
        System.out.println(MD5Encrypt.encrypt("zkr@123"));
    }
}  