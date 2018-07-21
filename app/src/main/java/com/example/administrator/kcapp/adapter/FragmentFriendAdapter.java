package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */

public class FragmentFriendAdapter extends RecyclerView.Adapter<FragmentFriendAdapter.ViewHolder> {

    private Context context;
    private List<String> data;

    public FragmentFriendAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_friend, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTouxiang;
        private TextView tvNickname;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTouxiang = itemView.findViewById(R.id.fragment_friend_rv_iv_touxiang);
            tvNickname = itemView.findViewById(R.id.fragment_friend_rv_tv_nickname);
        }
    }

}
