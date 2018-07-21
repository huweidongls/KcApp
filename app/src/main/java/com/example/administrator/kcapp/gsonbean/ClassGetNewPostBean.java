package com.example.administrator.kcapp.gsonbean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2018/7/4.
 */

public class ClassGetNewPostBean {

    /**
     * data : [{"id":54,"uid":669,"title":" ","content":"uhfjjfd","type":"news","video":null,"video_time_length":null,"cover":"669153068636168861484.jpg","look_num":0,"like_num":0,"praise_num":0,"comment_num":0,"c_time":1530686393,"is_hot":0,"status":1,"cid":1,"nickname":null,"avatar":"微信图片_20180626134913.jpg","is_praise":0,"is_like":0,"class":[{"id":60,"pid":54,"cid":1,"name":"科技"},{"id":61,"pid":54,"cid":2,"name":"美术"},{"id":62,"pid":54,"cid":3,"name":"声乐"}],"images":["669153068636168861484.jpg","669153068636169662717.jpg","669153068636170278813.jpg"]},{"id":53,"uid":669,"title":" ","content":"ifjjgv","type":"video","video":"669153068629784723088.mp4","video_time_length":5,"cover":"669153068629784723088.jpg","look_num":0,"like_num":0,"praise_num":0,"comment_num":0,"c_time":1530686334,"is_hot":0,"status":1,"cid":1,"nickname":null,"avatar":"微信图片_20180626134913.jpg","is_praise":0,"is_like":0,"class":[{"id":58,"pid":53,"cid":1,"name":"科技"},{"id":59,"pid":53,"cid":2,"name":"美术"}]},{"id":52,"uid":669,"title":" ","content":"kfkjfjvkvosoksksovovikfkd","type":"video","video":"6691530686130832.mp4","video_time_length":5,"cover":"6691530686130832.jpg","look_num":0,"like_num":0,"praise_num":0,"comment_num":0,"c_time":1530686166,"is_hot":0,"status":1,"cid":1,"nickname":null,"avatar":"微信图片_20180626134913.jpg","is_praise":0,"is_like":0,"class":[{"id":55,"pid":52,"cid":1,"name":"科技"},{"id":56,"pid":52,"cid":2,"name":"美术"},{"id":57,"pid":52,"cid":3,"name":"声乐"}]}]
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
         * praise_num : 0
         * comment_num : 0
         * c_time : 1530686393
         * is_hot : 0
         * status : 1
         * cid : 1
         * nickname : null
         * avatar : 微信图片_20180626134913.jpg
         * is_praise : 0
         * is_like : 0
         * class : [{"id":60,"pid":54,"cid":1,"name":"科技"},{"id":61,"pid":54,"cid":2,"name":"美术"},{"id":62,"pid":54,"cid":3,"name":"声乐"}]
         * images : ["669153068636168861484.jpg","669153068636169662717.jpg","669153068636170278813.jpg"]
         */

        private int id;
        private int uid;
        private String title;
        private String content;
        private String type;
        private Object video;
        private Object video_time_length;
        private String cover;
        private int look_num;
        private int like_num;
        private int praise_num;
        private int comment_num;
        private int c_time;
        private int is_hot;
        private int status;
        private int cid;
        private Object nickname;
        private String avatar;
        private int is_praise;
        private int is_like;
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

        public Object getVideo() {
            return video;
        }

        public void setVideo(Object video) {
            this.video = video;
        }

        public Object getVideo_time_length() {
            return video_time_length;
        }

        public void setVideo_time_length(Object video_time_length) {
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

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public Object getNickname() {
            return nickname;
        }

        public void setNickname(Object nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
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
             * id : 60
             * pid : 54
             * cid : 1
             * name : 科技
             */

            private int id;
            private int pid;
            private int cid;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
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
