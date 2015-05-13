package com.common.library.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;

import com.common.library.bean.local.CurrentLocation;


/**
 * Created by xing on 2015/4/22.
 */
public class LoactionUtil {

    /**
     * 是否打开gps
     * @param context
     * @return
     */
    private static final boolean isOPenGps(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 强制帮用户打开GPS
     * @param context
     */
    private static final void openGPS(Context context) {
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
    }

    private static CurrentLocation getCurrentLocation(Context context){
        CurrentLocation currentLocation = new CurrentLocation();
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        currentLocation.setLatitude(latitude);
        currentLocation.setLongtitude(longitude);
        return currentLocation;
    }

    /**
     * 得到当前位置
     * @param context
     * @return
     */
    public static CurrentLocation getLocation(Context context){
        if (!LoactionUtil.isOPenGps(context)){
            LoactionUtil.openGPS(context);
        }
        return  getCurrentLocation(context);
    }
}
