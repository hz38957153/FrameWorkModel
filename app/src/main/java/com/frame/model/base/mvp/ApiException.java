package com.frame.model.base.mvp;

import io.reactivex.functions.Consumer;

/**
 * Created by MrZ on 2017/10/11. 14:40
 * project delin
 * Explain API网络请求异常
 */
public class ApiException extends RuntimeException implements Consumer {
    private int mErrorCode;
    public ApiException(int errorCode, String errorMessage) {
        super(errorMessage);
        mErrorCode = errorCode;
    }

    @Override
    public void accept(Object o) throws Exception {

    }
}
