package com.frame.model.network;

import android.util.Log;

import com.frame.model.base.Constants;
import com.frame.model.utils.util.StringUtils;
import com.frame.model.utils.util.ToastUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MrZ on 2018/1/4. 16:47
 * project delin
 * Explain token拦截器
 */

public class TokenInterceptor implements Interceptor {

    private boolean index = true;
    private int tokenIndex = 0;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Log.i("response.code=========", response.code() + "");
        Log.i("response.url==========", response.request().url() + "");
        String newToken = response.header("userToken");
        if (!StringUtils.isEmpty(newToken)) {
            /*BaseData.getInstance().setToken(newToken);
            CacheUtils.getInstance().put("token",newToken);
            SPUtils.getInstance().put("token",newToken);*/
        }
        if (isTokenExpired(response, chain)) {//根据和服务端的约定判断token过期
            Log.i("isTokenExpired---------", "静默自动刷新Token,然后重新请求数据");
            //同步请求方式，获取最新的Token
            getNewToken();

            //使用新的Token，创建新的请求
            Request newRequest = chain.request()
                    .newBuilder()
                    .header("userToken", "token")
                    .build();
            //重新请求
            return chain.proceed(newRequest);
        }
        return response;
    }

    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isTokenExpired(Response response, Chain chain) {

        if (response.code() == 401) {
            return true;
        } else if (response.code() == 402) {
            //TODO
           /* Constant.loginClearData(false);
            if (BaseData.getInstance().IS_LOGIN()){
                ToastUtils.showShort("登陆失效，请重新登陆");
            }*/
        }
        return false;
    }

    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
    private synchronized void getNewToken() {

        try {
            if (StringUtils.isEmpty("token")){
                Constants.loginClearData(false);
                ToastUtils.showShort("登陆失效，请重新登陆");
            }
            else{
                /*ITokenService apiService = RxService.RETROFIT.createService(ITokenService.class);
                Call<Object> call = apiService.loadToken("User/getNewUserToken", BaseData.getInstance().getToken());
                call.execute();*/
            }

        } catch (Exception ex) {
            Log.e("error------", ex.getMessage() + "获取token失败");
        }
    }
}


