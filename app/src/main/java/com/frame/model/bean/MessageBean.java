package com.frame.model.bean;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/21
 * UpdateDate：2018/6/21
 * github：https://github.com/hz38957153
 */
public class MessageBean {


    /**
     * type : send_message
     * request : {"user_id":"6666","group":"1","message":""}
     */

    private String type;
    private RequestBean request;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public static class RequestBean {
        /**
         * user_id : 6666
         * group : 1
         * message :
         */

        private String user_id;
        private String group;
        private String message;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
