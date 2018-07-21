package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentWdRvRvEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class FragmentWdRvRvAdapter extends RecyclerView.Adapter<FragmentWdRvRvAdapter.FragmentWdRvRvViewHolder> {

    private Context context;
    private List<FragmentWdRvRvEntity> data;

    public FragmentWdRvRvAdapter(Context context, List<FragmentWdRvRvEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public FragmentWdRvRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FragmentWdRvRvViewHolder holder = new FragmentWdRvRvViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_fragment_wd_rv_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FragmentWdRvRvViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class FragmentWdRvRvViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private TextView money;

        public FragmentWdRvRvViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.fragment_wd_rv_rv_img);
            name = itemView.findViewById(R.id.fragment_wd_rv_rv_text);
            money = itemView.findViewById(R.id.fragment_wd_rv_rv_money);

        }
    }

}
