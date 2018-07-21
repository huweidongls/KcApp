package com.example.administrator.kcapp.entity;

/**
 * Created by Administrator on 2018/6/19.
 */

public class ActivityFbDtZtEntity {

    String id;
    String name;

    public ActivityFbDtZtEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
