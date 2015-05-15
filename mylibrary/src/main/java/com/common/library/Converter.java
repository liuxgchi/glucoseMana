package com.common.library;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 类描述：辅助转换器
 * <p>
 * 创建人: Abdi
 * <p>
 * 创建时间： @2015-4-4 @下午5:01:16
 */
public class Converter {
	/**
	 * 获得类的方法名，按照JAVABEAN的规范
	 * 
	 * @param property 属性名称
	 * @param prefix  前缀，一般为set 或 get
	 * @return String
	 */
	public static String getMethodName(String property, String prefix) {
		String prop = Character.toUpperCase(property.charAt(0)) + property.substring(1);
		return prefix + prop;
	}
	
	/**
	 * 对测评人数据进行排序
	 * @param list
	 */
	public static <T>void sortEvaluateUserList(List<T> list) {
		// 对结果进行排序
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T o1, T o2) {
				try {
					String evaluateTime1 = (String)o1.getClass().getMethod(Converter.getMethodName("evaluateTime", "get")).invoke(o1);
					String evaluateTime2 = (String)o2.getClass().getMethod(Converter.getMethodName("evaluateTime", "get")).invoke(o2);
					
					long t1 = Long.parseLong(evaluateTime1);
					long t2 = Long.parseLong(evaluateTime2);
					if(t1 < t2) {
						return 1;
					}else if(t1 == t2) {
						return 0;
					}
					return -1;
				} catch (Exception e) {
					throw new RuntimeException("请确认数据的正确性，保证有值", e);
				}
			}
		});
	}
	
	/**
	 * 从MAP中获取列表
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T>List<T> getListFromMap(HashMap map,  Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		if(null != map) {
			for(Object key : map.keySet()) {
				T obj = JSONObject.parseObject(((JSONObject) map.get(key)).toString(), clazz);
				list.add(obj);
			}
		}
		return list;
	}
	
	/**
	 * 从currentuuid向后取pageSize条
	 * @param currentuuid
	 * @param size
	 * @return
	 */
	public static <T>List<T> getList(List<T> list, String currentuuid, int size) {
		if(list == null || list.size() == 0) {
			return null;
		}
		
		List<T> result = new ArrayList<T>();
		boolean begin = false;
		int i = 0;
		for(T t : list) {
			try {
				String evaluateuuid = (String)t.getClass().getMethod(Converter.getMethodName("evaluateuuid", "get")).invoke(t);
				if(evaluateuuid.equals(currentuuid)) {
					begin = true;
					continue;
				}
				
				if(begin) {
					i ++;
					result.add(t);
				}
				
				if(i == size) {
					break;
				}
			} catch (Exception e) {
				throw new RuntimeException("获取类数据错误", e);
			}
		}
		
		// 返回空，说明数据没有数据了
		if(result.size() == 0) {
			return null;
		}
		return result;
	}
}
