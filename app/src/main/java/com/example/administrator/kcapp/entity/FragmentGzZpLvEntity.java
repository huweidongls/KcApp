package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentGzZpLvEntity {

    String name;
    int type;

    public FragmentGzZpLvEntity() {
    }

    public FragmentGzZpLvEntity(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
