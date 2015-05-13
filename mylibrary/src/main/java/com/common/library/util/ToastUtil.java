package com.common.library.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.common.library.R;

public class ToastUtil {

    private static Toast mToastCenter;

    private static TextView mToastTextView;

    private static Toast mToastTop;

    /**
     *
     * @param context
     * @param info
     * @param isLong
     */
    private static void showToast(Context context, String info,boolean isLong) {
        if (mToastCenter == null){
            if (isLong){
                mToastCenter = Toast.makeText(context,info,Toast.LENGTH_LONG);
            }else{
                mToastCenter = Toast.makeText(context,info,Toast.LENGTH_SHORT);
            }
            View toastView = LayoutInflater.from(context).inflate(R.layout.toast_view_layout,null);
            mToastTextView = (TextView) toastView.findViewById(R.id.taost_tv);
            mToastCenter.setView(toastView);
//            mToastCenter.setGravity(Gravity.BOTTOM,0,0);
        }/*else{
            mToastCenter.setText(info);
        }*/
        if (mToastTextView != null){
            mToastTextView.setText(info);
        }
        mToastCenter.show();
    }

	public static void showToast(Context context, String info) {
       showToast(context,info,false);
	}

    public static void showToastLong(Context context, String info) {
        showToast(context,info,true);
    }

	public static void showToast(Context context, int stringId) {
		showToast(context, context.getResources().getString(stringId));
	}

    public static void showToastLong(Context context, int stringId) {
        showToast(context, context.getResources().getString(stringId),true);
    }




}
