package com.example.administrator.kcapp.viewpagercard;

import com.example.administrator.kcapp.gsonbean.SharpBean;

import java.util.List;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class CardItem {

    private String id;
    private String avatar;
    private String imgUrl;
    private String nickName;
    private String title;
    private String video;
    private String content;
    private String comment_num;
    private String praise_num;
    private List<SharpBean.DataBean.ClassBean> fenlei;

    public CardItem(String id, String avatar, String imgUrl, String nickName, String title, String video, String content, String comment_num, String praise_num, List<SharpBean.DataBean.ClassBean> fenlei) {
        this.id = id;
        this.avatar = avatar;
        this.imgUrl = imgUrl;
        this.nickName = nickName;
        this.title = title;
        this.video = video;
        this.content = content;
        this.comment_num = comment_num;
        this.praise_num = praise_num;
        this.fenlei = fenlei;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComment_num() {
        return comment_num;
    }

    public void setComment_num(String comment_num) {
        this.comment_num = comment_num;
    }

    public String getPraise_num() {
        return praise_num;
    }

    public void setPraise_num(String praise_num) {
        this.praise_num = praise_num;
    }

    public List<SharpBean.DataBean.ClassBean> getFenlei() {
        return fenlei;
    }

    public void setFenlei(List<SharpBean.DataBean.ClassBean> fenlei) {
        this.fenlei = fenlei;
    }
}
