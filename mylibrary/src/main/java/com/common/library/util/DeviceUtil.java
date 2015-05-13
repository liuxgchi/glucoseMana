package com.common.library.util;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;


import com.common.library.R;
import com.common.library.bean.local.CurrentLocation;
import com.common.library.bean.local.DeviceInfo;

import java.util.List;

/**
 * 获取设备信息的工具类，包括屏幕宽度高度，设备唯一标识等等
 *
 * @author lxc
 */
public class DeviceUtil {

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }


    /**
     * 得到设备信息
     *
     * @param context
     * @return
     */
    public static DeviceInfo getDeiveInfo(Context context) {
        String device_model = Build.MODEL; // 设备型号
        String version_sdk = Build.VERSION.SDK; // 设备SDK版本
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setDevice_model(device_model);
        deviceInfo.setVersion_release(version_release);
        deviceInfo.setVersion_sdk(version_sdk);
        return deviceInfo;
    }

    /**
     * 得到当前经纬度
     *
     * @param context
     * @return
     */
    public static CurrentLocation getLocation(final Context context) {
        CurrentLocation currentLocation = null;
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null && location.getLatitude() != 1.0) {
                currentLocation = new CurrentLocation();
                currentLocation.setLatitude(location.getLatitude());
                currentLocation.setLongtitude(location.getLongitude());
                return currentLocation;
            }
        }

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null && location.getLatitude() != 1.0) {
                currentLocation = new CurrentLocation();
                currentLocation.setLatitude(location.getLatitude());
                currentLocation.setLongtitude(location.getLongitude());
                return  currentLocation;
            }else{
                ToastUtil.showToastLong(context, R.string.location_excep);
            }
        }else{
            ToastUtil.showToastLong(context, R.string.location_excep);
        }

        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null && location.getLatitude() != 1.0 && location.getLatitude() != 0.0) {
                currentLocation.setLatitude(location.getLatitude());
                currentLocation.setLongtitude(location.getLongitude());
                return currentLocation;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, (float) 1000.0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                if (location != null && location.getLatitude() != 1) {
//                    currentLocation.setLatitude(location.getLatitude());
//                    currentLocation.setLongtitude(location.getLongitude());
                }else{
//                    ToastUtil.showToast(context,"update excep");
                }
//                locationManager.removeUpdates(this);
            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }
        });
        return currentLocation;
    }

}
