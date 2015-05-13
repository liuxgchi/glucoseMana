package com.common.library.util;

/**
 * 应用设置工具类
 * @author Administrator
 *
 */
public class AppSettingUtil {

	/**
	 *是否允许推送消息(默认应该是可以推送的）
	 */
	public static boolean isPushMsg = true;


	public static boolean isPushMsg() {
		return isPushMsg;
	}

	/**
	 * 设置是否可以推送消息
	 * @param isPushMsg
	 */
	public static void setPushMsg(boolean isPushMsg) {
		AppSettingUtil.isPushMsg = isPushMsg;
		if (isPushMsg) {
			
		}else {
			
		}
	}

	
	
}
