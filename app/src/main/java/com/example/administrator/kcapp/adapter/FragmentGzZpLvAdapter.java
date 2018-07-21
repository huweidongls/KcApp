package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentGzZpLvEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentGzZpLvAdapter extends BaseAdapter {

    private Context context;
    private List<FragmentGzZpLvEntity> data = new ArrayList<>();
    private LayoutInflater inflater;

    private ItemClick itemClick;

    public FragmentGzZpLvAdapter(Context context, List<FragmentGzZpLvEntity> data) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_fragment_gz_zp, null);
            holder.nickName = view.findViewById(R.id.fragment_gz_zp_lv_nickname);
            holder.content = view.findViewById(R.id.fragment_gz_zp_lv_content);
            holder.content1 = view.findViewById(R.id.fragment_gz_zp_lv_content1);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        if(data.get(i).getType () == 1){
            holder.content.setVisibility(View.VISIBLE);
            holder.content1.setVisibility(View.GONE);
        }else if(data.get(i).getType () == 2){
            holder.content.setVisibility(View.GONE);
            holder.content1.setVisibility(View.VISIBLE);
        }

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(i, "content");
            }
        });
        holder.content1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.onClick(i, "content");
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView nickName;
        LinearLayout content;
        ImageView content1;
    }

    public interface ItemClick{
        void onClick(int position, String type);
    }

    public void setItemClick(ItemClick itemClick){
        this.itemClick = itemClick;
    }

}
