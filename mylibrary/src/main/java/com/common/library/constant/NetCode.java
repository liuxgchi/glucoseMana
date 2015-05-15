package com.common.library.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @descrption
 * @author lxc   
 * @version 创建时间：2015年1月10日 下午10:50:46 
 */
public class NetCode {
    /** 响应状态 - 服务器 - 请求成功*/
    public static final String STATUS_SERVER_SUCCESS = "100";
    /** 响应状态  - 服务器 - 程序异常*/
    public static final String STATUS_SERVER_EXCEPTION = "101";
    /** 响应状态 - 服务器 - 无需更新*/
    public static final String STATUS_SERVER_NOUPDATE = "102";
    /** 响应状态 - 服务器 - 缺少参数*/
    public static final String STATUS_SERVER_NOPARAMS = "103";
    /** 响应状态 - 服务器 - 参数结构错误*/
    public static final String STATUS_SERVER_PARAMS_STRUCT_ERROR = "104";
    /** 响应状态 - 服务器 - 版本太老,请下载最新版本*/
    public static final String STATUS_SERVER_APP_OLD = "105";

    /** 响应状态 ：用户名或者密码错误 */
    public static final String STATUS_USER_NOTEXIST = "201";
    /** 响应状态 ：个性账号已经存在 */
    public static final String STATUS_ACCOUNT_EXIST = "202";
    /** 响应状态 ：个性账号已经设置，无法重新设置*/
    public static final String STATUS_ACCOUNT_SETED = "203";
    /** 响应状态： 原始密码错误 */
    public static final String STATUS_PASSWORD_WRONG = "204";
    /** 响应状态： 账号格式错误 */
    public static final String STATUS_ACCOUNT_FORMAT_WRONG = "205";
    /** 响应状态： 没有需要同步的数据 */
    public static final String STATUS_SYNDATA_NOUPDATE = "206";
	
	/** 是否到期提示：提示*/
	public static final String EXPIRE_WARN_YES = "1";
	/** 是否到期提示：不提示*/
	public static final String EXPIRE_WARN_NO = "0";
	
	/** 账号有效：有效*/
	public static final String ACCOUNT_VALID = "1";
	/** 账号有效：无效*/
	public static final String EXPIRE_INVALID = "0";
	
	/** 登录账号类型：系统随机产生*/
	public static final String ACCOUNT_GEN = "1";
	/** 登录账号类型：系统注册账号*/
	public static final String ACCOUNT_REGISTER = "2";
	/** 登录账号类型：手机账号*/
	public static final String ACCOUNT_PHONE = "3";

    /** 错误提示工具 */
	private static Map<String, String> EORROR_TIPS = new HashMap<String, String>();
    static {
        EORROR_TIPS.put(STATUS_USER_NOTEXIST, "用户名或者密码错误");
        EORROR_TIPS.put(STATUS_ACCOUNT_EXIST, "个性账号已经存在");
        EORROR_TIPS.put(STATUS_ACCOUNT_SETED, "个性账号已经设置，无法重新设置");
        EORROR_TIPS.put(STATUS_PASSWORD_WRONG, "原始密码错误");
        EORROR_TIPS.put(STATUS_ACCOUNT_FORMAT_WRONG, "个性账号格式错误");
        EORROR_TIPS.put(STATUS_SERVER_APP_OLD, "版本太老,请下载最新版本");
    }

    /**
     * 根据编码获取错误提示信息
     * @param errorCode
     * @return
     */
    public static String getErrorTip(String errorCode) {
        return EORROR_TIPS.get(errorCode);
    }
}
