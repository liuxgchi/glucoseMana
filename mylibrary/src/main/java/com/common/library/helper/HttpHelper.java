package com.common.library.helper;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.common.library.R;
import com.common.library.bean.local.NetStatusEnum;
import com.common.library.bean.local.RequestURL;
import com.common.library.cache.Configuration;
import com.common.library.constant.NetCode;
import com.common.library.util.BusinessInterface;
import com.common.library.util.LogUtil;
import com.common.library.util.NetUtil;
import com.common.library.util.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.NameValuePair;

import java.lang.reflect.Field;
import java.util.List;

/**
 * des:http请求帮助类
 * Created by lxc on 2015/2/1.
 * package:com.httcm.net.
 */
public class HttpHelper {

    private static HttpHelper instance = null;

    public static synchronized HttpHelper getInstance(){
        if (instance == null){
            instance = new HttpHelper();
        }
        return  instance;
    }


    public void postData(final Context context,final BusinessInterface businessInterface,final String key,final RequestParams params){
        postData(context,businessInterface,key,params,true);
    }


    /**
     * 发送post请求
     * @param businessInterface
     * @param params
     */
    public void postData(final Context context,final BusinessInterface businessInterface,final String key,final RequestParams params,boolean isShowToast){
        if (!NetUtil.isNetworkConnected(context)){
            if (isShowToast){
                ToastUtil.showToast(context, R.string.no_net);
            }
            businessInterface.onNetExcep(NetStatusEnum.ON_NO_NET);
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        final RequestURL requestURL = Configuration.getInstance(context).getRequestURL(key);
        httpUtils.configSoTimeout(requestURL.getTimeout());
        httpUtils.send(HttpRequest.HttpMethod.POST,
                Configuration.getInstance(context).getServer()+requestURL.getUrl(),
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtil.outLogDetail("apiName-->" + generateRequestUrl(requestURL.getUrl(), params) + '\n' + "response result: " + responseInfo.result);
                        JSONObject jsonObject = JSONObject.parseObject(responseInfo.result);
                        if (jsonObject.getString("status").equals(NetCode.STATUS_SERVER_SUCCESS)) {
                            businessInterface.onMessageSucessCalledBack(key,jsonObject.getString("data"));
                        }else{
                            businessInterface.onError(key,jsonObject.getString("status"));
                        }
                    }
                    @Override
                       public void onFailure(HttpException error, String msg) {
                        businessInterface.onMessageFailedCalledBack(key,context.getResources().getString(R.string.net_excep));
                    }
                });
    }

    private String generateRequestUrl(String apiName,RequestParams params){
        Class requestParams = params.getClass();
        StringBuffer sb = new StringBuffer();
        try {
            Field field =  requestParams.getDeclaredField("bodyParams");
            if (field == null){
                return "";
            }
            field.setAccessible(true);
            List<NameValuePair> bodyParams = (List<NameValuePair>) field.get(params);
            for(NameValuePair nameValuePair : bodyParams){
                    if (TextUtils.isEmpty(sb.toString())){
                        sb.append(apiName+"?"+nameValuePair.getName()+"="+nameValuePair.getValue());
                    }else{
                        sb.append("&"+nameValuePair.getName()+"="+nameValuePair.getValue());
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
