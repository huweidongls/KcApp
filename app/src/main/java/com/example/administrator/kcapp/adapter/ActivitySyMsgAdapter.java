package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.ActivitySyMsgEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class ActivitySyMsgAdapter extends BaseAdapter {

    private Context context;
    private List<ActivitySyMsgEntity> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ActivitySyMsgAdapter(Context context, List<ActivitySyMsgEntity> data) {
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
            view = inflater.inflate(R.layout.listview_activity_sy_msg, viewGroup, false);
            holder.img = view.findViewById(R.id.activity_sy_msg_lv_touxiang);
            holder.type = view.findViewById(R.id.activity_sy_msg_lv_type);
            holder.time = view.findViewById(R.id.activity_sy_msg_lv_time);
            holder.content = view.findViewById(R.id.activity_sy_msg_lv_content);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(data.get(i).getImgUrl()).into(holder.img);
        holder.type.setText(data.get(i).getType());
        holder.time.setText(data.get(i).getTime());
        holder.content.setText(data.get(i).getContent());

        return view;
    }

    private class ViewHolder{
        ImageView img;
        TextView type;
        TextView time;
        TextView content;
    }

}
