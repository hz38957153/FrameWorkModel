package com.frame.model.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MrZ on 2018/2/24. 18:19
 * project delin
 * Explain url追加sign拦截器
 */
public class SignInterceptor implements Interceptor {


    @Override

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        String newUrl = "" ;
        FormBody newBody = new FormBody.Builder().build();
        Request newRequest;
        if (url.contains("login")&& !url.contains("setDeleteUser")){
            if (request.method().equals("POST")){
                newBody = setPostUrlSecret(url,request);
                newRequest = chain.request()
                        .newBuilder()
                        .method(request.method(),newBody)
                        .build();

            }
            else{
                newUrl = url + "&sign=" + setGetUrlSecret(url);
                newRequest = chain.request()
                        .newBuilder()
                        .url(HttpUrl.parse(newUrl))
                        .build();
            }
            return chain.proceed(newRequest);
        }
        else{
            return chain.proceed(request);
        }
    }

    /**
     * @param
     * @author MrZ
     * @time 2018/2/26  11:09
     * @notes get请求参数拼接
    */
    private String setGetUrlSecret(String url){
        if (!url.contains("?")){
            return "";
        }
        String s = url.substring(url.indexOf("?")+1);
        String ss [] = s.split("&");
        SortedMap<String,String> map = new TreeMap<>();
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        for (int i = 0; i < ss.length ; i++) {
            String sa [] = ss[i].split("=");
            for (int j = 0; j < sa.length ; j++) {
                if (j%2 == 0)
                    key.add(sa[j]);
                else
                    value.add(sa[j]);
            }
        }
        for (int i = 0; i < key.size() ; i++) {
            String v;
            if (value.size() != 0){
                if (i > value.size()-1)
                    v = "";
                else
                    v = value.get(i);
            }
            else{
                v = "";
            }
            map.put(key.get(i),v);
        }
        //return Constants.createSign(map);
        return "sign";
    }

    /**
     * @param
     * @author MrZ
     * @time 2018/2/26  11:10
     * @notes post请求参数拼接
    */
    private FormBody setPostUrlSecret(String url,Request request){
        StringBuilder sb = new StringBuilder();
        SortedMap<String ,String > map = new TreeMap<>();
        FormBody.Builder bodys = new FormBody.Builder();
        if (request.body() instanceof FormBody) {
            FormBody body = (FormBody) request.body();
            for (int i = 0; i < body.size(); i++) {
                map.put(body.encodedName(i),body.encodedValue(i));
            }

            for (int i = 0; i < body.size()+1; i++) {
                if (i == body.size()){
                    //bodys.add("sign",Constants.createSign(map));
                    bodys.add("sign","sign");
                }
                else{
                    bodys.add(body.encodedName(i),body.encodedValue(i));
                }
            }
        }

        return bodys.build();
    }



}
