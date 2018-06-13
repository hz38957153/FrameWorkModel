package com.frame.model.base.mvp;


import com.frame.model.base.ExceptionApi;
import com.frame.model.utils.util.ToastUtils;
import com.google.gson.JsonSyntaxException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.functions.Consumer;

/**
 * desc：网络访问回调异常处理类
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public abstract class ApiCallBackError<M> implements Consumer<Throwable> {

    @Override
    public void accept(Throwable e) throws Exception {
        e.printStackTrace();
        if (e instanceof ExceptionApi) {
            ToastUtils.showShort(e.getMessage());
        } else if ((e instanceof UnknownHostException)) {
            ToastUtils.showShort("网络异常");
        } else if (e instanceof JsonSyntaxException) {
            ToastUtils.showShort("数据异常");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("连接超时");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort("连接服务器失败");
        } else {
            ToastUtils.showShort("未知错误");
        }
        onFailure(e.getMessage().toString());
    }

    public abstract void onFailure(String errorMsg);
}
