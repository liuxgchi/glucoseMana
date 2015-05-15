package com.common.library.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.common.library.R;
import com.common.library.cache.ImageCache;
import com.lidroid.xutils.BitmapUtils;

/**
 * Created by think on 2015/3/15.
 */
public class BitmapHelper extends BitmapUtils {
    /** 对象保存 */
    private static BitmapHelper helper;

    /** 不带占位图的helper*/
    private static BitmapHelper helperWithNoSpacePhoto;


    /**
     * 构造器
     * @param context
     */
    public BitmapHelper(Context context){
        super(context);
    }

    /**
     * 获取图像处理实例
     * @param context
     * @return
     */
    public synchronized static BitmapHelper getBitMapUtils(Context context){
        if (helper == null){
            helper = new BitmapHelper(context);
        }
        helper.configDefaultLoadingImage(R.drawable.ic_launcher);
        helper.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        helper.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        helper.configMemoryCacheEnabled(true);
        helper.configDiskCacheEnabled(true);
//        helper.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(context).scaleDown(3));
        return helper;
    }

    /**
     * 获取图像处理实例
     * @param context
     * @return
     */
    public synchronized static BitmapHelper getBitMapUtilsWithoutSpacePho(Context context){
        if (helperWithNoSpacePhoto == null){
            helperWithNoSpacePhoto = new BitmapHelper(context);
        }
        helperWithNoSpacePhoto.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        helperWithNoSpacePhoto.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        helperWithNoSpacePhoto.configMemoryCacheEnabled(true);
        helperWithNoSpacePhoto.configDiskCacheEnabled(true);
//        helper.configDefaultBitmapMaxSize(BitmapCommonUtils.getScreenSize(context).scaleDown(3));
        return helperWithNoSpacePhoto;
    }
    /**
     * 如果存在本地图片，那么显示本地图片，否则直接读取URL
     * @param context
     * @param welImgv
     * @param url
     */
    public void displayIfExistInternal(Context context, ImageView welImgv, String url) {
        if (url.startsWith("http")){
            String path = ImageCache.getInstance().getPicPath(url);
            if(path == null) {
                this.display(welImgv,url);
            } else {
                this.display(welImgv, path);
            }
        }else{
            // 什么时候可能不是以http开头呢？
            this.display(welImgv,url);
        }
    }

}
