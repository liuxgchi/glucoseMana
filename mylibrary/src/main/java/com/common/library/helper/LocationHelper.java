package com.common.library.helper;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

/**
 * 定位帮助类
 * Created by xing on 2015/4/27.
 */
public class LocationHelper implements AMapLocationListener {

    private LocationManagerProxy mLocationManagerProxy;

    private onLocationChangedListener listener;

    public static LocationHelper instance;

    public Context context;

    private LocationHelper(Context context){
        this.context = context;
    }

    public static LocationHelper getInstance(Context context){
        if (instance == null){
            instance = new LocationHelper(context);
        }
        return  instance;
    }

    /**
     * 初始化定位
     */
    public void initLocatation() {
        mLocationManagerProxy = LocationManagerProxy.getInstance(context);
        //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        //注意设置合适的定位时间的间隔，并且在合适时间调用removeUpdates()方法来取消定位请求
        //在定位结束后，在合适的生命周期调用destroy()方法
        //其中如果间隔时间为-1，则定位只定一次
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, -1, 1000, this);
        mLocationManagerProxy.setGpsEnable(false);
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getAMapException().getErrorCode() == 0) {
            //获取位置信息
            Double geoLat = aMapLocation.getLatitude();
            Double geoLng = aMapLocation.getLongitude();
            if (geoLat == 1 || geoLat == 0) {
                openGPSSettings();
            }else{
                listener.onLocationChanged(aMapLocation);
            }
        } else {
            openGPSSettings();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public interface onLocationChangedListener{
        public void onLocationChanged(AMapLocation aMapLocation);
    }

    public void setOnLocationChangedListern(onLocationChangedListener listener){
        this.listener = listener;
    }

    //打开和关闭gps第二种方法
    private void openGPSSettings() {
        //获取GPS现在的状态（打开或是关闭状态）
        //    boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER);
        //    if (gpsEnabled) {
        ////关闭GPS
        //        Settings.Secure.setLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER, false);
        //    } else {
        //打开GPS
        //        Settings.Secure.setLocationProviderEnabled(context.getContentResolver(), LocationManager.GPS_PROVIDER, true);
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
//    }
    }

}
