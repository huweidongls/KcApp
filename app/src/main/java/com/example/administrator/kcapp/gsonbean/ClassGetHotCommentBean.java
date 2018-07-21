package com.example.administrator.kcapp.gsonbean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/6/29.
 */

public class ClassGetHotCommentBean {

    /**
     * data : [{"pid":3,"type":"news","cid":3,"video":"1530011937282.mp4 ","p_title":"程序员修改bug","cover":"1530010943158.jpg","praise_num":898,"c_content":"我还是热评","comment_time":1528772401,"nickname":"头发乱了。","avatar":"微信图片_20180626134913.jpg","class":[{"id":25,"name":"科技"},{"id":26,"name":"美术"},{"id":27,"name":"声乐"}],"images":["1530011430660.jpg"]},{"pid":1,"type":"video","cid":4,"video":"1530011937282.mp4 ","p_title":"程序员修改bug1231231","cover":"1530010943158.jpg","praise_num":465,"c_content":"我依然是热评","comment_time":1528772401,"nickname":"头发乱了。","avatar":"微信图片_20180626134913.jpg","class":[{"id":1,"name":"科技"},{"id":20,"name":"美术"},{"id":21,"name":"声乐"}],"images":["1530011430660.jpg"]},{"pid":2,"type":"read","cid":2,"video":"1530011937282.mp4 ","p_title":"程序员修改bug","cover":"1530010943158.jpg","praise_num":90,"c_content":"我也是热评","comment_time":1528772401,"nickname":"头发乱了。","avatar":"微信图片_20180626134913.jpg","class":[{"id":22,"name":"科技"},{"id":23,"name":"美术"},{"id":24,"name":"声乐"}],"images":["1530011430660.jpg"]}]
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
         * pid : 3
         * type : news
         * cid : 3
         * video : 1530011937282.mp4
         * p_title : 程序员修改bug
         * cover : 1530010943158.jpg
         * praise_num : 898
         * c_content : 我还是热评
         * comment_time : 1528772401
         * nickname : 头发乱了。
         * avatar : 微信图片_20180626134913.jpg
         * class : [{"id":25,"name":"科技"},{"id":26,"name":"美术"},{"id":27,"name":"声乐"}]
         * images : ["1530011430660.jpg"]
         */

        private int pid;
        private String type;
        private int cid;
        private String video;
        private String p_title;
        private String cover;
        private int praise_num;
        private String c_content;
        private int comment_time;
        private String nickname;
        private String avatar;
        @SerializedName("class")
        private List<ClassBean> classX;
        private List<String> images;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getP_title() {
            return p_title;
        }

        public void setP_title(String p_title) {
            this.p_title = p_title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(int praise_num) {
            this.praise_num = praise_num;
        }

        public String getC_content() {
            return c_content;
        }

        public void setC_content(String c_content) {
            this.c_content = c_content;
        }

        public int getComment_time() {
            return comment_time;
        }

        public void setComment_time(int comment_time) {
            this.comment_time = comment_time;
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

        public List<ClassBean> getClassX() {
            return classX;
        }

        public void setClassX(List<ClassBean> classX) {
            this.classX = classX;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class ClassBean {
            /**
             * id : 25
             * name : 科技
             */

            private int id;
            private String name;

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
        }
    }
}
