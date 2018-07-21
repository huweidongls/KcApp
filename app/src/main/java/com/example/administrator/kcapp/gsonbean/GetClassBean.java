package com.example.administrator.kcapp.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class GetClassBean {

    /**
     * data : [{"id":1,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":2,"name":"美术","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":3,"name":"声乐","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":4,"name":"IT","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":5,"name":"动漫","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":6,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":7,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":8,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"},{"id":9,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg"}]
     * code : 200
     * msg : success
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * name : 科技
         * content : 这是简介
         * ico : /uploads/images/2018/06/07/201806071731077383.png
         * cover : /bg.jpg
         */

        private int id;
        private String name;
        private String content;
        private String ico;
        private String cover;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
