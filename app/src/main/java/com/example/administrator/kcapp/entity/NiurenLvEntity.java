package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class NiurenLvEntity {

    String imgUrl;
    String tv1;
    String tv2;

    public NiurenLvEntity() {
    }

    public NiurenLvEntity(String imgUrl, String tv1, String tv2) {
        this.imgUrl = imgUrl;
        this.tv1 = tv1;
        this.tv2 = tv2;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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
}
