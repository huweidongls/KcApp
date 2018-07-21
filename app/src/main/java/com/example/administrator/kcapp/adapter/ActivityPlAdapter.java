package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.ActivityPlEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class ActivityPlAdapter extends BaseAdapter {

    private Context context;
    private List<ActivityPlEntity> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ActivityPlAdapter(Context context, List<ActivityPlEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_activity_pl, viewGroup, false);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    private class ViewHolder{

    }

}
