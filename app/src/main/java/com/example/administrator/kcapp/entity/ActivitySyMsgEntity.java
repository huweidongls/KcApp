package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class ActivitySyMsgEntity {

    int imgUrl;
    String type;
    String time;
    String content;

    public ActivitySyMsgEntity() {
    }

    public ActivitySyMsgEntity(int imgUrl, String type, String time, String content) {
        this.imgUrl = imgUrl;
        this.type = type;
        this.time = time;
        this.content = content;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
