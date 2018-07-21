package com.example.administrator.kcapp.gsonbean;

/**
 * Created by Administrator on 2018/6/21.
 */

public class GetCenterAdvBean {

    /**
     * data : {"id":5,"title":"测试广告","image":"uploads/20180531/2219890e722b6a8be4502306f032b3f1.jpg"}
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
         * id : 5
         * title : 测试广告
         * image : uploads/20180531/2219890e722b6a8be4502306f032b3f1.jpg
         */

        private int id;
        private String title;
        private String image;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
