package com.example.administrator.kcapp.gsonbean;

/**
 * Created by Administrator on 2018/6/27.
 */

public class LoginBean {

    /**
     * data : {"id":669,"username":"18686817319","password":"","nickname":"","avatar":"","sex":"","birth_year":"","birth_month":"","birth_day":"","sign":"","level_id":"","exp":"","praise_num":"","teacher_num":"","tel":"18686817319","c_time":"","status":1,"is_shop":"","token":"d3daa56bb0fb1b1662ae6e77a7193338e79cdac7"}
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
         * id : 669
         * username : 18686817319
         * password :
         * nickname :
         * avatar :
         * sex :
         * birth_year :
         * birth_month :
         * birth_day :
         * sign :
         * level_id :
         * exp :
         * praise_num :
         * teacher_num :
         * tel : 18686817319
         * c_time :
         * status : 1
         * is_shop :
         * token : d3daa56bb0fb1b1662ae6e77a7193338e79cdac7
         */

        private int id;
        private String username;
        private String password;
        private String nickname;
        private String avatar;
        private String sex;
        private String birth_year;
        private String birth_month;
        private String birth_day;
        private String sign;
        private String level_id;
        private String exp;
        private String praise_num;
        private String teacher_num;
        private String tel;
        private String c_time;
        private int status;
        private String is_shop;
        private String token;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBirth_year() {
            return birth_year;
        }

        public void setBirth_year(String birth_year) {
            this.birth_year = birth_year;
        }

        public String getBirth_month() {
            return birth_month;
        }

        public void setBirth_month(String birth_month) {
            this.birth_month = birth_month;
        }

        public String getBirth_day() {
            return birth_day;
        }

        public void setBirth_day(String birth_day) {
            this.birth_day = birth_day;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getLevel_id() {
            return level_id;
        }

        public void setLevel_id(String level_id) {
            this.level_id = level_id;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getTeacher_num() {
            return teacher_num;
        }

        public void setTeacher_num(String teacher_num) {
            this.teacher_num = teacher_num;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIs_shop() {
            return is_shop;
        }

        public void setIs_shop(String is_shop) {
            this.is_shop = is_shop;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
