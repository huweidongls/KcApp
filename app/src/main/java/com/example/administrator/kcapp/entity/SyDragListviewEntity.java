package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class SyDragListviewEntity {

    int img;
    String title;
    String content;

    public SyDragListviewEntity() {
    }

    public SyDragListviewEntity(int img, String title, String content) {
        this.img = img;
        this.title = title;
        this.content = content;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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

}
