package com.frame.model.network;

import android.os.Environment;

import com.frame.model.base.URLRoot;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MrZ on 2017/10/11. 10:42
 * project delin
 * Explain RX&Retrofit
 */

public enum RxService {
    RETROFIT;
    private Retrofit mRetrofit;
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位秒
    private static final int CONN_TIMEOUT = 50;//连接超时时间,单位秒

    /**
     *  @author MrZ
     *  @time   2018/6/12
     *  @uptime   2018/6/12
     *  @describe 
     */
    public Retrofit createRetrofit() {
        File httpCacheDirectory = new File(Environment.getExternalStorageDirectory(), "HttpCache");//这里为了方便直接把文件放在了SD卡根目录的HttpCache中，一般放在context.getCacheDir()中
        int cacheSize = 10 * 1024 * 1024;//设置缓存文件大小为10M
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        if(mRetrofit == null){
            OkHttpClient client = new OkHttpClient.Builder()//初始化一个client,不然retrofit会自己默认添加一个
                    .addInterceptor(new SignInterceptor())
                    .addInterceptor(new HeadInterceptor())
                    .addInterceptor(new TokenInterceptor())
                   // .addNetworkInterceptor(new REWRITE_CACHE_CONTROL_INTERCEPTOR())
                    .connectTimeout(CONN_TIMEOUT, TimeUnit.MINUTES)//设置连接时间为50s
                    .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)//设置读取时间为一分钟
                    //.cache(cache)
                    .build();

            mRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(URLRoot.BASE_API)
                    .addConverterFactory(GsonConverterFactory.create())//返回值为Gson的支持(以实体类返回)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//返回值为Oservable<T>的支持
                    .build();
        }

        return mRetrofit;
    }



    /**
     *  @author MrZ
     *  @time   2018/6/12
     *  @uptime   2018/6/12
     *  @describe 
     */
    public <T> T createService(final Class<T> service) {
        validateServiceInterface(service);
        return (T) RxService.RETROFIT.createRetrofit().create(service);
    }

    /**
     *  @author MrZ
     *  @time 2018/6/12  18:25
     *  @uptime 2018/6/12  18:25
     *  @describe
     */
    public <T> void validateServiceInterface(Class<T> service) {
        if (service == null) {
            //Toast.ShowToast("服务接口不能为空！");
        }
        if (!service.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

}
