package com.frame.model.base;

/**
 * Created by MrZ on 2017/10/10. 17:54
 * project delin
 * Explain 返回接口的基类
 */

public class BaseFeed {

    /**
     * status : {"code":200,"message":"成功","sysTime":1508312562}
     */

    private StatusBean status;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public static class StatusBean {
        /**
         * code : 200
         * message : 成功
         * sysTime : 1508312562
         */

        private int code;
        private String message;
        private int sysTime;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getSysTime() {
            return sysTime;
        }

        public void setSysTime(int sysTime) {
            this.sysTime = sysTime;
        }
    }
}
