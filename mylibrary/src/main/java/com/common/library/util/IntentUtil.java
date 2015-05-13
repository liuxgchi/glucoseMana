package com.common.library.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
 * @author lxc
 * @date 2014年9月28日
 * 
 */
public class IntentUtil {
	
	public static void startActivity(Context context,Class<?> cls){
		startActivity(context, cls, null);
	}

	public static void startActivityAndFinishMyself(Context context,Class<?> cls){
		startActivityAndFinishMyself(context, cls, null);
	}


	public static void startActivity(Context context,Class<?> cls,Bundle bundle){
		Intent intent = new Intent(context, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

    public static void startActivityAndFinishOthers(Context context,Class<?> cls,Bundle bundle){
        Intent intent = new Intent(context, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startActivityAndFinishOthers(Context context,Class<?> cls){
        startActivityAndFinishOthers(context, cls, null);

    }

    public static void startActivityAndFinishMyself(Context context,Class<?> cls,Bundle bundle){
        startActivity(context,cls,bundle);
        if(context instanceof Activity){
            ((Activity) context).finish();
        }
	}


	
	
}
