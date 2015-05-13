package com.glucoseman;

import android.app.Application;
import android.content.Context;

/**
 * Created by xing on 2015/5/7.
 */
public class MyApplication extends Application {

   private static MyApplication instance = null;

   private static Context context = null;

    private String name;

    public static MyApplication getInstance(){
        return  instance;
    }

    public static Context getContext(){
        return  context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        instance = MyApplication.this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
