package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentThreeKcEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class FragmentThreeKcAdapter extends BaseAdapter {

    private Context context;
    private List<FragmentThreeKcEntity> data = new ArrayList<>();
    private LayoutInflater inflater;

    public FragmentThreeKcAdapter(Context context, List<FragmentThreeKcEntity> data) {
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
            view = inflater.inflate(R.layout.listview_fragment_three_kc, null);
            holder.img = view.findViewById(R.id.fragment_three_lv_iv_img);
            holder.title = view.findViewById(R.id.fragment_three_lv_title);
            holder.tv1 = view.findViewById(R.id.fragment_three_lv_tv1);
            holder.tv2 = view.findViewById(R.id.fragment_three_lv_tv2);
            holder.jiage = view.findViewById(R.id.fragment_three_lv_tv_jiage);
            holder.fukuan = view.findViewById(R.id.fragment_three_lv_tv_fukuan);
            holder.name = view.findViewById(R.id.fragment_three_lv_tv_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.img.setImageResource(data.get(i).getImgUrl());

        return view;
    }

    private class ViewHolder{
        ImageView img;
        TextView title;
        TextView tv1;
        TextView tv2;
        TextView jiage;
        TextView fukuan;
        TextView name;
    }

}
