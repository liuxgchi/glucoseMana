package com.common.library.bean.local;

public class RequestURL {
	/** 请求地址 */
	private String url;
	/** 超时时间 */
	private Integer timeout;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
}
