package com.common.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;


import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class AppUtil {

	public static String getInstalledApp(Context context) {
		List<PackageInfo> list = context.getPackageManager()
				.getInstalledPackages(PackageManager.GET_PERMISSIONS);
		StringBuilder stringBuilder = new StringBuilder();
		for (PackageInfo packageInfo : list) {
			stringBuilder.append("package name:" + packageInfo.packageName
					+ "\n");
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			stringBuilder.append("Ӧ�����:"
					+ applicationInfo.loadLabel(context.getPackageManager())
					+ "\n");
			if (packageInfo.permissions != null) {
				for (PermissionInfo p : packageInfo.permissions) {
					stringBuilder.append("Ȩ�ް���:" + p.name + "\n");
				}
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}
	
	/**
	 * 得到当前版本信息对象
	* @descrption
	* @author lxc
	* @version 创建时间：2014年12月17日 下午5:40:08
	 */
	public static String getVersion(Context context) {
	        // ---get the package info---
        String versionName = null;
        try {
            PackageManager pm = context.getPackageManager();;
            PackageInfo pi = null;
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = "V"+pi.versionName;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
	    return versionName;
	}

}
