package com.example.administrator.kcapp.eventbus;

import com.example.administrator.kcapp.entity.ActivityFbDtZtEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class FbDtSelectZtEvent {
    List<ActivityFbDtZtEntity> data;

    public FbDtSelectZtEvent(List<ActivityFbDtZtEntity> data) {
        this.data = data;
    }

    public List<ActivityFbDtZtEntity> getData() {
        return data;
    }

    public void setData(List<ActivityFbDtZtEntity> data) {
        this.data = data;
    }
}
