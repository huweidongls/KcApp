package com.example.administrator.kcapp.adapter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public interface StartDragListener {
    //触摸imageview，开启拖动的接口
    void startDragItem(RecyclerView.ViewHolder holder);
}
