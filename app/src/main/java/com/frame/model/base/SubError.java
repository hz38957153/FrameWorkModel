package com.frame.model.base;

import java.io.Serializable;

/**
 * desc：
 * Author：MrZ
 * CrateDate：2018/6/12
 * UpdateDate：2018/6/12
 * github：https://github.com/hz38957153
 */

public class SubError implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String message;

    public String getCode()
    {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
