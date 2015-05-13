package com.common.library.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.library.R;


/**
 * Created by xing on 2015/4/14.
 */
public class DialogUtil {


    private static Dialog mDialog;


    public  static void showOpDialog(Context context) {
        showOpDialog(context, "加载中...");
    }
    /**
     * 显示操作请求的progressDialog(例如登录)，一定要在请求方法之前调用 。
     * @param context
     * @param msg
     * @return
     */
    public  static void showOpDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.op_progress_layout, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.progress_loading_layout);// 加载布局
        TextView tv = (TextView) v.findViewById(R.id.loading_tv);
        tv.setText(msg);
        ImageView loadingImgv = (ImageView) v.findViewById(R.id.loading_imgv);
        Animation loadAnim = AnimationUtils.loadAnimation(context, R.anim.regular_loading_anim);
        loadingImgv.startAnimation(loadAnim);

        if (mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new Dialog(context, R.style.op_dialog);// 创建自定义样式dialog
        mDialog.setCancelable(true);// 不可以用“返回键”取消
        mDialog.show();
        int wid = DensityUtil.dip2px(context,120);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wid,wid);
        mDialog.getWindow().setContentView(layout,params);
    }

    /**
     * 显示加载过程中的progressDialog(例如列表请求)一定要在请求方法之前调用
     * @param context
     * @return
     */
    public  static void showLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_progress_layout, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.progress_loading_layout);// 加载布局
        TextView tv = (TextView) v.findViewById(R.id.loading_tv);
        tv.setText("加载中");

        ImageView loadingImgv = (ImageView) v.findViewById(R.id.loading_imgv);
        Animation loadAnim = AnimationUtils.loadAnimation(context, R.anim.regular_loading_anim);
        loadingImgv.startAnimation(loadAnim);

        if (mDialog != null){
            mDialog.dismiss();
            mDialog = null;
        }
        mDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        mDialog.setCancelable(true);// 不可以用“返回键”取消
        mDialog.show();
        int wid = DensityUtil.dip2px(context,120);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(wid,wid);
        mDialog.getWindow().setContentView(layout,params);
    }

    /**
     * 统一关闭上面两种加载条
     */
    public static void dimissDialog(){
        if (mDialog != null){
            mDialog.dismiss();
        }
    }



}
