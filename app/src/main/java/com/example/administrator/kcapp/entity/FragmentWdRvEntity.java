package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/15.
 */

public class FragmentWdRvEntity {

    int imgUrl;
    String text;

    public FragmentWdRvEntity() {
    }

    public FragmentWdRvEntity(int imgUrl, String text) {
        this.imgUrl = imgUrl;
        this.text = text;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
