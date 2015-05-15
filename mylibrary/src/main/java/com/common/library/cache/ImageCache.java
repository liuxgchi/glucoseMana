package com.common.library.cache;

import android.content.Context;
import android.provider.SyncStateContract;

import com.alibaba.fastjson.JSONObject;
import com.common.library.TextDB;
import com.common.library.bean.local.CacheMap;
import com.common.library.constant.Constants;

import java.io.InputStream;

/**
 * 内置图片读取
 */
public class ImageCache {
	/** 图片缓存实例 */
	private static ImageCache IMAGE = null;
	
	/** 图片映射关系 */
	private static CacheMap<String, String> ADDRESS_MAP = new CacheMap<String,String>(50);
	
	/**
	 * 构造器
	 */
	private ImageCache(Context context) {
		load(context);
	}
	
	/**
	 * 获取缓存实例对象
	 * @return
	 */
	public synchronized static ImageCache getInstance(Context context) {
		if(null == IMAGE) {
			IMAGE = new ImageCache(context);
		}
		return IMAGE;
	}

    /**
     * 获取缓存实例对象
     * @return
     */
    public synchronized static ImageCache getInstance() {
        return IMAGE;
    }

    /**
     * 得到图片路径
     * @param url
     * @return
     */
    public String getPicPath(String url){
        return  ADDRESS_MAP.get(url);
    }

	/**
	 * 加载图片映射关系
	 */
	private void load(Context context) {
        try {
            // 读取文件
            InputStream is = context.getResources().getAssets().open(Constants.FILE_IMAGE);

            // 进行遍历，将图片进行缓存
            JSONObject images = TextDB.getObject(is, JSONObject.class);
            if(null != images) {
                for(String url : images.keySet()) {
                    ADDRESS_MAP.put(url, Constants.FOLDER_IMAGE + images.getString(url));
                }
            }
        } catch (Exception e) {
            throw  new RuntimeException("读取图片映射关系错误。", e);
        }
    }
	
	public static void main(String[] args) {
		ImageCache.getInstance();
	}
}
