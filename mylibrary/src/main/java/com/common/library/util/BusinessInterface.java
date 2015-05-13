package com.common.library.util;


import com.common.library.bean.local.NetStatusEnum;

public interface BusinessInterface {


    public void onMessageSucessCalledBack(String url, String response);
	
	public void onMessageFailedCalledBack(String url, String response);
	
	public void onError(String ur, String result);
	
	/**
	 * 无网状
	 */
	public void onNetExcep(NetStatusEnum netStatus);
	
	/**
	 * 结束方法，无论请求网络成功还是失败，都会调用此方法
	 */
	public void onFinish();
}
