package com.common.library.cache;

import android.content.Context;

import com.common.library.bean.local.CacheMap;
import com.common.library.bean.local.RequestURL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 类描述：基础配置读取
 */
public class Configuration {
	/** 系统配置缓存实例 */
	private static Configuration CONFIG = null;
	
	/** 地址链接 */
	private static CacheMap<String, RequestURL> URL_MAP = new CacheMap<String,RequestURL>(50);
	
	/** 运行模式 */
	private static boolean IS_DEBUG;
	
	/** 服务地址 */
	private static String SERVER;


    /**
	 * 构造器
	 */
	private Configuration(Context context) {
        load(context);
	}

    /**
     * 获取缓存实例对象
     * @return
     */
     public synchronized static Configuration getInstance(Context context) {
        if(null == CONFIG) {
            CONFIG = new Configuration(context);
        }
        return CONFIG;
    }

	/**
	 * 获取请求信息
	 * @param key
	 * @return
	 */
	public RequestURL getRequestURL(String key) {
		if(URL_MAP.containsKey(key)) {
			return URL_MAP.get(key);
		}
		throw new RuntimeException("您未配置当前请求信息，请及时配置！");
	}
	
	/**
	 * 获取服务地址
	 * @return
	 */
	public String getServer() {
		return SERVER;
	}
	
	/**
	 * 获取运行模式
	 * @return
	 */
	public boolean isDebug() {
		return IS_DEBUG;
	}

    /**
     * 从配置中读取所有请求的参数
     * @throws
     */
    private void load(Context context) {
        try {
			// 加载文件
			Properties props = new Properties();
//            InputStream is = Configuration.class.getResourceAsStream("/global.properties");
            InputStream is = context.getResources().getAssets().open("global.properties");
			props.load(is);

			// 加载专项数据
			parseSpecial(props);
			
			// 分别装载数据
			parse(props);
			
			// 关闭流
			is.close();
		} catch (IOException e) {
			throw new RuntimeException("读取系统配置文件错误");
		}
    }
    
    /**
     * 根据指定参数获取值
     * @param props
     */
    private void parseSpecial(Properties props) {
    	IS_DEBUG = props.getProperty("application.debug").equals("true");
    	if(IS_DEBUG) {
    		SERVER = (String)props.getProperty("application.server.debug");
    	} else {
    		SERVER = (String)props.getProperty("application.server.runtime");
    	}
    }
    
    /**
     * 解析装载文件
     * @param props
     */
    private void parse(Properties props) {
    	Iterator<Entry<Object, Object>> it = props.entrySet().iterator();
    	while (it.hasNext()) { 
    		// 遍历对象
    		Entry<Object, Object> entry = it.next(); 
    		String key = (String)entry.getKey();
    		String value = (String)entry.getValue();
    		
    		if(key.startsWith("request.")) {
    			loadURL(key, value);
    		} 
    	}
    }
    
    private void loadURL(String key, String value) {
    	String[] splits = key.split("\\."); // 截取中间标志
    	String skey = splits[1];
    	String stype = splits[2];
    	
    	// 将对象预置入缓存
    	RequestURL entry = new RequestURL();
    	if(URL_MAP.containsKey(skey)) {
    		entry = URL_MAP.get(skey);
    	} else {
    		URL_MAP.put(skey, entry);
    	}
    	
    	// 分别加载参数
    	if("timeout".equals(stype)) {
    		entry.setTimeout(Integer.parseInt(value));
    	} else if("url".equals(stype)){
    		entry.setUrl(value);
    		if(null == entry.getTimeout()) {
    			entry.setTimeout(25000);
    		}
     	}
    	
    }
    
    public static void main(String[] args) {
//    	Configuration c = Configuration.getInstance();
//		System.out.println(c.isDebug());
//		System.out.println(c.getServer());
//		System.out.println(c.getRequestURL("login").getUrl());
//		System.out.println(c.getRequestURL("login").getTimeout());
	}
}
