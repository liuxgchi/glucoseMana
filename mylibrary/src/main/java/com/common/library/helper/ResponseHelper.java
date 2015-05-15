package com.common.library.helper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;


/**
 * 从响应数据中获取参数
 */
public class ResponseHelper {
	/**
	 * 从响应获取数据
	 * @param response
	 * @param key
	 * @return
	 */
	public static String get(String response, String key) {
		JSONObject object = JSONObject.parseObject(response);
		if(object.containsKey(key)) {
			return object.getString(key);
		}
		return null;
	}
	
	/**
	 * 从响应获取数据
	 * @param response
	 * @param clazz
	 * @return
	 */
	public static <T>List<T> getList(String response, Class<T> clazz) {
		return JSONArray.parseArray(response, clazz);
		
	}
	
	/**
	 * 从响应获取数据
	 * @param response
	 * @param clazz
	 * @return
	 */
	public static <T>List<T> getList(String response, String key, Class<T> clazz) {
		JSONObject object = JSONObject.parseObject(response);
		if(null != object && object.containsKey(key)) {
			String objectJSON = object.getString(key);
			return JSONArray.parseArray(objectJSON, clazz);
		}
		return null;
	}

	/**
	 * 从响应获取数据
	 * @param response
	 * @param clazz
	 * @return
	 */
	public static <T> T get(String response, Class<T> clazz) {
		return JSONObject.parseObject(response, clazz);
	}

	/**
	 * 从响应获取数据
	 * @param response
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> T get(String response,String key,Class<T> clazz) {
		String objectJSON = get(response, key);
		if(null != objectJSON) {
			return JSONObject.parseObject(objectJSON, clazz);
		}
		return null;
	}
}