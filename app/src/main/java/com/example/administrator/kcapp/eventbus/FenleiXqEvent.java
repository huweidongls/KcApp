package com.example.administrator.kcapp.eventbus;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class FenleiXqEvent {

    String id;
    String topTitle;
    String content;
    String cover;

    public FenleiXqEvent(String id, String topTitle, String content, String cover) {
        this.id = id;
        this.topTitle = topTitle;
        this.content = content;
        this.cover = cover;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
