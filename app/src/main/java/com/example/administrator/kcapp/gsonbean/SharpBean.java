package com.example.administrator.kcapp.gsonbean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class SharpBean {

    /**
     * data : [{"id":3,"uid":1,"title":"程序员修改bug","content":"程序员修改bug","type":"video","video":"/uploads/video/default.mp4","video_time_length":10,"cover":"/bg.jpg","look_num":156242314,"praise_num":423423,"comment_num":122,"c_time":1527734995,"is_hot":0,"status":1,"nickname":"头发乱了。","avatar":"/uploads/images/2018/05/31/201805311740414616.jpg","class":[{"id":1,"name":"科技"},{"id":2,"name":"美术"},{"id":3,"name":"声乐"}]},{"id":5,"uid":1,"title":"程序员修改bug","content":"程序员修改bug","type":"video","video":"/uploads/video/default.mp4","video_time_length":10,"cover":"/bg.jpg","look_num":113454435,"praise_num":134,"comment_num":156123,"c_time":1527734995,"is_hot":0,"status":1,"nickname":"头发乱了。","avatar":"/uploads/images/2018/05/31/201805311740414616.jpg"},{"id":6,"uid":1,"title":"程序员修改bug","content":"程序员修改bug","type":"video","video":"/uploads/video/default.mp4","video_time_length":10,"cover":"/bg.jpg","look_num":113451234,"praise_num":123432,"comment_num":23,"c_time":1527734995,"is_hot":0,"status":1,"nickname":"头发乱了。","avatar":"/uploads/images/2018/05/31/201805311740414616.jpg"},{"id":9,"uid":1,"title":"程序员修改bug","content":"程序员修改bug","type":"video","video":"/uploads/video/default.mp4","video_time_length":10,"cover":"/bg.jpg","look_num":11457686,"praise_num":6546,"comment_num":1563,"c_time":1529484179,"is_hot":0,"status":1,"nickname":"头发乱了。","avatar":"/uploads/images/2018/05/31/201805311740414616.jpg"},{"id":4,"uid":1,"title":"程序员修改bug","content":"程序员修改bug","type":"video","video":"/uploads/video/default.mp4","video_time_length":10,"cover":"/bg.jpg","look_num":11341234,"praise_num":54345,"comment_num":23,"c_time":1527734995,"is_hot":0,"status":1,"nickname":"头发乱了。","avatar":"/uploads/images/2018/05/31/201805311740414616.jpg","class":[{"id":1,"name":"科技"},{"id":2,"name":"美术"},{"id":3,"name":"声乐"}]}]
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
         * title : 程序员修改bug
         * content : 程序员修改bug
         * type : video
         * video : /uploads/video/default.mp4
         * video_time_length : 10
         * cover : /bg.jpg
         * look_num : 156242314
         * praise_num : 423423
         * comment_num : 122
         * c_time : 1527734995
         * is_hot : 0
         * status : 1
         * nickname : 头发乱了。
         * avatar : /uploads/images/2018/05/31/201805311740414616.jpg
         * class : [{"id":1,"name":"科技"},{"id":2,"name":"美术"},{"id":3,"name":"声乐"}]
         */

        private int id;
        private int uid;
        private String title;
        private String content;
        private String type;
        private String video;
        private int video_time_length;
        private String cover;
        private int look_num;
        private int praise_num;
        private int comment_num;
        private int c_time;
        private int is_hot;
        private int status;
        private String nickname;
        private String avatar;
        @SerializedName("class")
        private List<ClassBean> classX;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public int getVideo_time_length() {
            return video_time_length;
        }

        public void setVideo_time_length(int video_time_length) {
            this.video_time_length = video_time_length;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getLook_num() {
            return look_num;
        }

        public void setLook_num(int look_num) {
            this.look_num = look_num;
        }

        public int getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(int praise_num) {
            this.praise_num = praise_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getC_time() {
            return c_time;
        }

        public void setC_time(int c_time) {
            this.c_time = c_time;
        }

        public int getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(int is_hot) {
            this.is_hot = is_hot;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public static class ClassBean {
            /**
             * id : 1
             * name : 科技
             */

            private int id;
            private String name;

            public ClassBean(int id, String name) {
                this.id = id;
                this.name = name;
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
        }
    }
}
