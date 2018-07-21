package com.example.administrator.kcapp.gsonbean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class PostCommentBean {

    /**
     * data : [{"id":3,"uid":1,"pid":3,"fid":0,"content":"我还是热评","praise_num":898,"c_time":1528772401,"nickname":"头发乱了。","avatar":"微信图片_20180626134913.jpg"}]
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
         * id : 3
         * uid : 1
         * pid : 3
         * fid : 0
         * content : 我还是热评
         * praise_num : 898
         * c_time : 1528772401
         * nickname : 头发乱了。
         * avatar : 微信图片_20180626134913.jpg
         */

        private int id;
        private int uid;
        private int pid;
        private int fid;
        private String content;
        private int praise_num;
        private int c_time;
        private String nickname;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(int praise_num) {
            this.praise_num = praise_num;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
