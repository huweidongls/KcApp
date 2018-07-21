package com.example.administrator.kcapp.eventbus;

import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class TieziXqEvent {

    String pid;
    List<ClassGetNewPostBean.DataBean> data;
    int i;

    public TieziXqEvent(String pid) {
        this.pid = pid;
    }

    public TieziXqEvent(String pid, List<ClassGetNewPostBean.DataBean> data, int i) {
        this.pid = pid;
        this.data = data;
        this.i = i;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<ClassGetNewPostBean.DataBean> getData() {
        return data;
    }

    public void setData(List<ClassGetNewPostBean.DataBean> data) {
        this.data = data;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
