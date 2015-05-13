package com.common.library.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.common.library.bean.local.NetStatusEnum;
import com.common.library.util.BusinessInterface;
import com.common.library.util.ToastUtil;


/**
 * @descrption
 * @author lxc   
 * @version 创建时间：2014年12月12日 下午11:24:31 
 */
@SuppressLint("NewApi")
public abstract  class BaseFragment extends Fragment implements BusinessInterface {
	
	protected View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
        initViews();
        initDatas();
        initBusiness();
	}


    @Override
    public void onMessageFailedCalledBack(String url, String response) {
//        提示不在这里再次处理，所有错误处理在onError中进行处理。
        if (getActivity() != null){
            ToastUtil.showToast(getActivity(), "网络异常，请稍后重试");
        }
    }

    @Override
    public void onMessageSucessCalledBack(String url, String response) {

    }

    @Override
	public void onError(String ur, String result) {
		// TODO Auto-generated method stub
        if(!TextUtils.isEmpty(result)) {
            ToastUtil.showToast(getActivity(), result);
        }
	}

	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNetExcep(NetStatusEnum netStatus) {
		/**
		* @descrption
		* @author lxc   
		* @version 创建时间：2015年1月5日 下午5:59:54 
		*/
	}


    protected abstract void initViews();

    protected  abstract void initDatas();

    protected abstract void initBusiness();

}

