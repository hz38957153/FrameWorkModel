package com.frame.model.base;

/**
 * desc：异常类
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public class ExceptionApi extends RuntimeException {

    private String mMessage;

    public ExceptionApi(String reason) {
        super(reason);
        this.mMessage = reason;
    }
    public String getmMessage() {
        return mMessage;
    }
}
