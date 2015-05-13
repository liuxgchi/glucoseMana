package com.common.library.util;

import java.security.MessageDigest;

/**
 * @descrption 加密工具类
 * @author lxc   
 * @version 创建时间：2014年12月17日 下午8:15:47 
 */
public class EncryptUtil {

	/**
	 * 
	* @descrption 加密传输过程中的参数s
	* @author lxc   
	* @version 创建时间：2014年12月17日 下午8:16:01
	 */
	 public static String MD5(String inStr)
	    {
	        MessageDigest md5 = null;
	        try
	        {
	            md5 = MessageDigest.getInstance("MD5");
	        }
	        catch (Exception e)
	        {
	            System.out.println(e.toString());
	            e.printStackTrace();
	            return "";
	        }
	        char[] charArray = inStr.toCharArray();
	        byte[] byteArray = new byte[charArray.length];
	        
	        for (int i = 0; i < charArray.length; i++)
	            byteArray[i] = (byte) charArray[i];
	        
	        byte[] md5Bytes = md5.digest(byteArray);
	        
	        StringBuffer hexValue = new StringBuffer();
	        
	        for (int i = 0; i < md5Bytes.length; i++)
	        {
	            int val = ((int) md5Bytes[i]) & 0xff;
	            if(val < 16)
	                hexValue.append("0");
	            hexValue.append(Integer.toHexString(val));
	        }
	        
	        return hexValue.toString();
	    }
}

