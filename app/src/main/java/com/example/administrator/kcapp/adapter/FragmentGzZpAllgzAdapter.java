package com.example.administrator.kcapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentGzZpAllgzEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentGzZpAllgzAdapter extends RecyclerView.Adapter<FragmentGzZpAllgzAdapter.MyViewHolder> {

    private List<FragmentGzZpAllgzEntity> data;

    public FragmentGzZpAllgzAdapter(List<FragmentGzZpAllgzEntity> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_gz_zp_allgz, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.img.setImageResource(data.get(position).getImgUrl());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fragment_gz_zp_recyclerview_img);
        }
    }

}
