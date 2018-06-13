package com.frame.model.network;

import android.provider.Settings;
import android.util.Log;

import com.frame.model.base.BaseActivity;
import com.frame.model.base.Constants;
import com.frame.model.utils.util.AppUtils;
import com.frame.model.utils.util.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by MrZ on 2018/1/24. 16:30
 * project delin
 * Explain 头信息拦截器
 */

public class HeadInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();

        long t1 = System.nanoTime();//请求发起的时间
        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.name(i) + "=" + body.value(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Log.i("NET_RELATIVE",
                        String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                        request.url(), chain.connection(), request.headers(), sb.toString()));
            }
        } else {
            Log.i("NET_RELATIVE",String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }
        //重新请求
        Response response = chain.proceed(initHead(null,chain));
        isTokenExpired(response,chain);
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.i("NET_RELATIVE",
                String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d,
                        response.headers()
                ));
        return response;
    }

    /**
     *  @author MrZ
     *  @time   2018/6/12
     *  @uptime   2018/6/12
     *  @describe 初始化头信息
     */
    public Request initHead(File file, Chain chain) {
        Request.Builder mBuilder = chain.request().newBuilder();
        Map<String, String> map = new HashMap<String, String>();
//        HeaderUtil.setHeader(map);//ydc 移植注释
        if (file != null) {
            //String mSize = MD5Util.MD5(file.length() + "");
            String mSize ="10";
            //SharedUtil.setFileSize(mSize);//andy.fang 文件大小MD5保存头信息，方便后台校验
            map.put("filesize", mSize);//上传下载文件的MD5值 不需要每个地方都加，在需要的地方加
        }
        Set keys = map.keySet();
        if (keys != null) {
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = (String) map.get(key);
                /*if(StrUtil.isNotBlank(key)&& StrUtil.isNotBlank(value)){
                    mBuilder.addHeader(key, value);
                }*/
            }
        }

        //系统级请求参数
        mBuilder.header("userToken", "token");// 移植注释
        mBuilder.header("version", AppUtils.getAppVersionCode()+"");
        mBuilder.header("appID",1+"");
        mBuilder.header("androidId", Settings.System.getString(BaseActivity.mContext.getContentResolver(), Settings.System.ANDROID_ID));
//        mBuilder.addHeader("format", "JSON");
//        mBuilder.addHeader("appKey", "00001");

        Request mRequest = mBuilder.build();
        return mRequest;
    }

    private void isTokenExpired(Response response, Chain chain) {

        if (response.code() == 401) {

        } else if (response.code() == 402) {
            Constants.loginClearData(false);
            ToastUtils.showShort("登陆失效，请重新登陆");
        }
        else if(response.code() == 500){
            ToastUtils.showShort("服务器内部错误");
        }
        else if(response.code() == 502){
            ToastUtils.showShort("服务器内部错误");
        }
    }

}
