package com.common.library.util;

import android.util.Log;

/**
 * 日志工具类
 */
public class LogUtil {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static final int LEVEL = VERBOSE;

	public static final String TAG = "XingAndroid";

	public static boolean DEBUG_FLAG = true;

	 public static void setShowLog(boolean flag) {
        DEBUG_FLAG = flag;
    }

	 
	public static void outLogDetail(Object result) {
		outLogDetail(TAG, result);
	}

	public static void outLogDetail(String tag, Object result) {
        try{
            if (DEBUG_FLAG) {
                StackTraceElement ste = new Throwable().getStackTrace()[2];
                Log.i(TAG, ste.getFileName() + ": Line:" + ste.getLineNumber() + "-log->" + result);
            }
        }catch (Exception e){

        }

	}

	public static void outLogDetail(int result) {
        try{
            if (DEBUG_FLAG) {
                StackTraceElement ste = new Throwable().getStackTrace()[2];
                Log.i(TAG, ste.getFileName() + ": Line:" + ste.getLineNumber() + "-log->" + result);
            }
        }catch (Exception e){

        }

	}

}
