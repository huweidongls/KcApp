package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class FragmentThreeKcEntity {

    int imgUrl;
    String title;
    String tv1;
    String tv2;
    String jiage;
    String fukuan;
    String name;

    public FragmentThreeKcEntity() {
    }

    public FragmentThreeKcEntity(int imgUrl, String title, String tv1, String tv2, String jiage, String fukuan, String name) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.tv1 = tv1;
        this.tv2 = tv2;
        this.jiage = jiage;
        this.fukuan = fukuan;
        this.name = name;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTv1() {
        return tv1;
    }

    public void setTv1(String tv1) {
        this.tv1 = tv1;
    }

    public String getTv2() {
        return tv2;
    }

    public void setTv2(String tv2) {
        this.tv2 = tv2;
    }

    public String getJiage() {
        return jiage;
    }

    public void setJiage(String jiage) {
        this.jiage = jiage;
    }

    public String getFukuan() {
        return fukuan;
    }

    public void setFukuan(String fukuan) {
        this.fukuan = fukuan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
