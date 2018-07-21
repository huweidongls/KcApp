package com.example.administrator.kcapp.gsonbean;

/**
 * Created by Administrator on 2018/7/6.
 */

public class MemberLikeBean {

    /**
     * data : {"sta":1,"like_num":1}
     * code : 200
     * msg : success
     */

    private DataBean data;
    private int code;
    private String msg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * sta : 1
         * like_num : 1
         */

        private int sta;
        private int like_num;

        public int getSta() {
            return sta;
        }

        public void setSta(int sta) {
            this.sta = sta;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }
    }
}
