package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentWdRvEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/7/4.
 */

public class FragmentWdNewRvAdapter extends RecyclerView.Adapter<FragmentWdNewRvAdapter.ViewHolder> {

    private List<FragmentWdRvEntity> data;
    private Context context;
    private onItemListener listener;

    public void setOnItemListener(onItemListener listener){
        this.listener = listener;
    }

    public FragmentWdNewRvAdapter(List<FragmentWdRvEntity> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_wd_new_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(context).load(data.get(position).getImgUrl()).into(holder.img);
        holder.tv.setText(data.get(position).getText());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView tv;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fragment_wd_new_rv_img);
            tv = itemView.findViewById(R.id.fragment_wd_new_rv_tv);
            rl = itemView.findViewById(R.id.fragment_wd_new_rv_rl);
        }
    }

    public interface onItemListener{
        void onClick(int i);
    }

}
