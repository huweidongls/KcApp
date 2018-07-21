package com.example.administrator.kcapp.eventbus;

import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/6.
 */

public class FragmentOtherEvent {
    List<ClassGetNewPostBean.DataBean> data;

    public FragmentOtherEvent(List<ClassGetNewPostBean.DataBean> data) {
        this.data = data;
    }

    public List<ClassGetNewPostBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ClassGetNewPostBean.DataBean> data) {
        this.data = data;
    }
}
