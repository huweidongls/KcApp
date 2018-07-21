package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/15.
 */

public class FragmentWdRvRvEntity {

    int imgUrl;
    String name;
    String money;

    public FragmentWdRvRvEntity() {
    }

    public FragmentWdRvRvEntity(int imgUrl, String name, String money) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.money = money;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
