package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.kcapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ActivitySyFbDtGridAdapter extends RecyclerView.Adapter<ActivitySyFbDtGridAdapter.GridViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private static final int MAX_SIZE = 9;

    private Context context;
    private List<String> mList;

    private OnItemClickListener itemClickListener;

    public ActivitySyFbDtGridAdapter(List<String> mList, OnItemClickListener itemClickListener) {
        this.mList = mList;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        GridViewHolder viewHolder = new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_activity_sy_fb_dt_grid, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemAddClick();
            }
        });

//        viewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemClickListener.onItemRemoveClick(viewHolder.getAdapterPosition());
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        if (mList.size() >= MAX_SIZE) {
            //最多6张
            holder.ivAdd.setVisibility(View.GONE);
        } else {
            holder.ivImg.setVisibility(View.VISIBLE);
        }
        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivImg.setVisibility(View.GONE);
        } else {
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivImg.setVisibility(View.VISIBLE);
            Picasso.with(context).load("file://" + mList.get(position)).into(holder.ivImg);
        }
    }

    @Override
    public int getItemCount() {
//        return mList == null ? 0 : mList.size() + 1;
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImg;
        private ImageView ivAdd;

        public GridViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.activity_sy_fb_dt_grid_photo);
            ivAdd = itemView.findViewById(R.id.activity_sy_fb_dt_grid_add);
        }
    }

    public interface OnItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemAddClick();

        /**
         * 删除已经添加的图片接口
         *
         * @param position 删除的position
         */
        void onItemRemoveClick(int position);
    }

}
