package com.common.library.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * activity管理类
 * Created by xing on 2015/5/12.
 */
public class ActivityCollector {

    public static List<Activity> activityList = new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity: activityList){
            activityList.remove(activity);
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
