package com.example.administrator.kcapp.gsonbean;

/**
 * Created by Administrator on 2018/7/3.
 */

public class UploadTokenBean {

    /**
     * data : {"toekn":"vFui0Ext_VgoncI0Asr1GQCVB6n5CbiOKoeAG4FP:v6jHc1QvCs-sKOHvNlWooi5w_JI=:eyJzY29wZSI6Inpob3VwZW5neXUiLCJkZWFkbGluZSI6MTUzMDY2Nzc0MX0=","time":86400}
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
         * toekn : vFui0Ext_VgoncI0Asr1GQCVB6n5CbiOKoeAG4FP:v6jHc1QvCs-sKOHvNlWooi5w_JI=:eyJzY29wZSI6Inpob3VwZW5neXUiLCJkZWFkbGluZSI6MTUzMDY2Nzc0MX0=
         * time : 86400
         */

        private String toekn;
        private int time;

        public String getToekn() {
            return toekn;
        }

        public void setToekn(String toekn) {
            this.toekn = toekn;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
