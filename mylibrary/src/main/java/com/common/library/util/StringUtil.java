package com.common.library.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作String字符串的工作累
 * 
 * @author renheng
 *
 */
public class StringUtil {

	/**
	 * 截取一个字符串中的所有中文
	 * 
	 * @return 截取出来的中文
	 */
	public static String getChineseString(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c <= 40891 && c >= 19968) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 是否是合法手机号
	 * @param phoneNum
	 * @return
	 */
	public static boolean isPhoneNo(String phoneNum){
		Pattern p = Pattern.compile("^1\\d{10}");
		Matcher m = p.matcher(phoneNum);
		return m.matches();
	}

	/**
	 * 是否是合法邮编
	 * @param phoneCode
	 * @return
	 */
	public static boolean isPostCode(String phoneCode){
		Pattern p = Pattern.compile("[0-9]{6}");
		Matcher m = p.matcher(phoneCode);
		return m.matches();
	}
	
	/**
	 * 返回带有货币符号的字符串
	 * @param str
	 * @return
	 */
	public static String getCharPrice(String str){
		return "￥"+str;
	}
	

	public static String getCharCount(String str){
		return " x "+str;
	}


    /**
     * list转换为用逗号分割的字符串
     * @param list
     * @return
     */
    public static String List2String(List<String> list){
        StringBuffer sb = new StringBuffer();
        if (list == null && (list !=null && list.size() == 0)){
            return null;
        }
        if (list.size() == 1) {
            String s = list.get(0);
            return s;
        }else if (list.size() > 1){
            for(int i = 0 ; i < list.size(); i++){
                sb.append(list.get(i));
                if (i != list.size() -1){
                    sb.append(",");
                }
            }
        }
        return  sb.toString();
    }

    /**
     * 将带有逗号的字符串拆分，转化成list
     * @param s
     * @return
     */
    public static  List String2List(String s){
        List<String> list = new ArrayList<String>();
        if(TextUtils.isEmpty(s)){
            return list;
        }
        if(s.contains(",")){
            String[] strings = s.split(",");
            for(String ss:strings){
                list.add(ss);
            }
        }else{
            list.add(s);
        }

        return list;
    }

    /**
     * list转换为用回车分割的字符串
     * @param list
     * @return
     */
    public static String ListToStringWithEnter(List<String> list){
        StringBuffer sb = new StringBuffer();
        if (list == null && (list !=null && list.size() == 0)){
            return null;
        }
        if (list.size() == 1) {
            String s = list.get(0);
            return s.trim();
        }else if (list.size() > 1){
            for(int i = 0 ; i < list.size(); i++){
                sb.append(list.get(i));
                if (i != list.size() -1){
                    sb.append("\n");
                }
            }
        }
        return  sb.toString().trim();
    }


    /**
     * 缩进
     */
    public static String retractString(String s){
        return  "\t\t\t\t"+s.trim();
    }

    public static String trimString(String s){
        String result = "";
        if(!TextUtils.isEmpty(s)){
            result = s.trim();
        }
        return  result;
    }


}
