package com.example.administrator.kcapp.gsonbean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class PostContentBean {

    /**
     * data : {"id":54,"uid":669,"title":" ","content":"uhfjjfd","type":"news","video":null,"video_time_length":null,"cover":"669153068636168861484.jpg","look_num":0,"like_num":0,"praise_num":1,"comment_num":0,"c_time":1530686393,"is_hot":0,"status":1,"is_praise":0,"is_like":0,"user":{"id":669,"nickname":"18686817319","avatar":"微信图片_20180626134913.jpg"},"class":[{"id":1,"cover":"/bg.jpg","name":"科技"},{"id":2,"cover":"/bg.jpg","name":"美术"},{"id":3,"cover":"/bg.jpg","name":"声乐"}],"images":["669153068636168861484.jpg","669153068636169662717.jpg","669153068636170278813.jpg"]}
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
         * id : 54
         * uid : 669
         * title :
         * content : uhfjjfd
         * type : news
         * video : null
         * video_time_length : null
         * cover : 669153068636168861484.jpg
         * look_num : 0
         * like_num : 0
         * praise_num : 1
         * comment_num : 0
         * c_time : 1530686393
         * is_hot : 0
         * status : 1
         * is_praise : 0
         * is_like : 0
         * user : {"id":669,"nickname":"18686817319","avatar":"微信图片_20180626134913.jpg"}
         * class : [{"id":1,"cover":"/bg.jpg","name":"科技"},{"id":2,"cover":"/bg.jpg","name":"美术"},{"id":3,"cover":"/bg.jpg","name":"声乐"}]
         * images : ["669153068636168861484.jpg","669153068636169662717.jpg","669153068636170278813.jpg"]
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
        private int like_num;
        private int praise_num;
        private int comment_num;
        private int c_time;
        private int is_hot;
        private int status;
        private int is_praise;
        private int is_like;
        private UserBean user;
        @SerializedName("class")
        private List<ClassBean> classX;
        private List<String> images;

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

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
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

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public int getIs_like() {
            return is_like;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
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

        public static class UserBean {
            /**
             * id : 669
             * nickname : 18686817319
             * avatar : 微信图片_20180626134913.jpg
             */

            private int id;
            private String nickname;
            private String avatar;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

        public static class ClassBean {
            /**
             * id : 1
             * cover : /bg.jpg
             * name : 科技
             */

            private int id;
            private String cover;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
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
