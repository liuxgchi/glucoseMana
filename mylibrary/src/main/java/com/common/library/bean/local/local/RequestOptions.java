package com.common.library.bean.local.local;
/**
 * @descrption
 * @author lxc   
 * @version 创建时间：2015年1月10日 下午11:03:58 
 */
public class RequestOptions {

	private boolean timeOutToast;
	
	private Object tag;
	
	private boolean errorToast;

	public boolean isTimeOutToast() {
		return timeOutToast;
	}

	public void setTimeOutToast(boolean timeOutToast) {
		this.timeOutToast = timeOutToast;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	public boolean isErrorToast() {
		return errorToast;
	}

	public void setErrorToast(boolean errorToast) {
		this.errorToast = errorToast;
	}

	
	
}
