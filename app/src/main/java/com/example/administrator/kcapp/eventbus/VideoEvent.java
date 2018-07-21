package com.example.administrator.kcapp.eventbus;

/**
 * Created by Administrator on 2018/6/28.
 */

public class VideoEvent {
    private String id;
    private String title;
    private String cover;
    private String video;
    private String avatar;
    private String nickname;
    private String c_time;
    private String c_content;
    private String comment_num;

    public VideoEvent(String id, String title, String cover, String video, String avatar, String nickname, String c_time, String c_content, String comment_num) {
        this.id = id;
        this.title = title;
        this.cover = cover;
        this.video = video;
        this.avatar = avatar;
        this.nickname = nickname;
        this.c_time = c_time;
        this.c_content = c_content;
        this.comment_num = comment_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getC_time() {
        return c_time;
    }

    public void setC_time(String c_time) {
        this.c_time = c_time;
    }

    public String getC_content() {
        return c_content;
    }

    public void setC_content(String c_content) {
        this.c_content = c_content;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }
}
