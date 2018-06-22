package com.frame.model.bean;

/**
 * desc：
 * Author：MrZ
 * CreateDate：2018/6/21
 * UpdateDate：2018/6/21
 * github：https://github.com/hz38957153
 */
public class MessageResponseBean {


    /**
     * type : send_message
     * response : {"message":"djdjdjfjfj","name":"Mr.Z","head_img":"/public/upload/2018-04-11/20221523428772.jpg","user_id":"2022"}
     */

    private String type;
    private ResponseBean response;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * message : djdjdjfjfj
         * name : Mr.Z
         * head_img : /public/upload/2018-04-11/20221523428772.jpg
         * user_id : 2022
         */

        private String message;
        private String name;
        private String head_img;
        private String user_id;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHead_img() {
            return head_img;
        }

        public void setHead_img(String head_img) {
            this.head_img = head_img;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
