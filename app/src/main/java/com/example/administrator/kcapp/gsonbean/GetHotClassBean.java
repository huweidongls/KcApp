package com.example.administrator.kcapp.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class GetHotClassBean {

    /**
     * data : [{"num":23,"cid":1,"id":1,"name":"科技","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg","c_time":1528423380,"is_rec":1,"status":1,"is_follow":0},{"num":11,"cid":2,"id":2,"name":"美术","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg","c_time":1528423380,"is_rec":1,"status":1,"is_follow":0},{"num":10,"cid":3,"id":3,"name":"声乐","content":"这是简介","ico":"/uploads/images/2018/06/07/201806071731077383.png","cover":"/bg.jpg","c_time":1528423380,"is_rec":1,"status":1,"is_follow":0}]
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
         * num : 23
         * cid : 1
         * id : 1
         * name : 科技
         * content : 这是简介
         * ico : /uploads/images/2018/06/07/201806071731077383.png
         * cover : /bg.jpg
         * c_time : 1528423380
         * is_rec : 1
         * status : 1
         * is_follow : 0
         */

        private int num;
        private int cid;
        private int id;
        private String name;
        private String content;
        private String ico;
        private String cover;
        private int c_time;
        private int is_rec;
        private int status;
        private int is_follow;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

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

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getIs_rec() {
            return is_rec;
        }

        public void setIs_rec(int is_rec) {
            this.is_rec = is_rec;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }
    }
}
