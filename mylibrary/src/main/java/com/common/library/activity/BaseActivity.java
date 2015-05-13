package com.common.library.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;

import com.common.library.bean.local.NetStatusEnum;
import com.common.library.util.BusinessInterface;
import com.common.library.util.ToastUtil;


/**
 * @descrption 
 * @author lxc   
 * @version 创建时间：2014年12月11日 下午6:42:45 
 */
public abstract  class BaseActivity extends FragmentActivity implements BusinessInterface {
	
//	protected TitleBar titleBar;
//    public BitmapHelper mBitmapUtils;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

        initViews();
        initDatas();
        initBusiness();
	}

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    public abstract void  initViews();

    public abstract  void initDatas();

    public abstract  void initBusiness();
	
	@Override
	public void onMessageSucessCalledBack(String url, String response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessageFailedCalledBack(String url, String response) {
		// TODO Auto-generated method stub
        ToastUtil.showToast(this, response);
//        DialogUtil.dimissDialog();
	}

	@Override
	public void onError(String ur, String result) {
		// TODO Auto-generated method stub
        //调整位置　
//        String errorTip = NetCode.getErrorTip(result);
//        if(!TextUtils.isEmpty(errorTip)) {
//            ToastUtil.showToast(this, errorTip);
//        }
//        DialogUtil.dimissDialog();
	}


	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetExcep(NetStatusEnum netStatus) {
//        DialogUtil.dimissDialog();
	}
	
	
	protected void setOnClickListener(View.OnClickListener listener,int ... id){
		for (int i : id) {
			View v = findViewById(i);
			v.setOnClickListener(listener);
		}
	}
	
	
}

