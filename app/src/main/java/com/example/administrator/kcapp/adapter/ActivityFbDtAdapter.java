package com.example.administrator.kcapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.kcapp.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ActivityFbDtAdapter extends RecyclerView.Adapter<ActivityFbDtAdapter.ZtViewHolder> {

    private List<String> data;

    public ActivityFbDtAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ZtViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ZtViewHolder holder = new ZtViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_activity_fb_dt_zt, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ZtViewHolder holder, int position) {
        holder.zt.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ZtViewHolder extends RecyclerView.ViewHolder {

        private TextView zt;

        public ZtViewHolder(View itemView) {
            super(itemView);
            zt = itemView.findViewById(R.id.activity_sy_fb_dt_rv_zt_tv);
        }
    }

}
