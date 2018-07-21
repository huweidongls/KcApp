package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.gsonbean.PostCommentBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/7/11.
 */

public class PopCommentRvAdapter extends RecyclerView.Adapter<PopCommentRvAdapter.ViewHolder> {

    private Context context;
    private List<PostCommentBean.DataBean> data;

    public PopCommentRvAdapter(List<PostCommentBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_pop_comment, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(Consts.QINIU_URL+data.get(position).getAvatar()).into(holder.ivTouxiang);
        holder.tvNickname.setText(data.get(position).getNickname());
        holder.tvZanNum.setText(data.get(position).getPraise_num()+"");
        holder.tvContent.setText(data.get(position).getContent());
        holder.tvCreateTime.setText(TimeUtil.LongFormatTime(data.get(position).getC_time()+""));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTouxiang;
        private TextView tvNickname;
        private TextView tvZanNum;
        private ImageView ivZan;
        private TextView tvContent;
        private TextView tvHf;
        private TextView tvCreateTime;
        private ImageView ivDian;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTouxiang = itemView.findViewById(R.id.pop_niuren_comment_rv_iv_touxiang);
            tvNickname = itemView.findViewById(R.id.pop_niuren_comment_rv_tv_nickname);
            tvZanNum = itemView.findViewById(R.id.pop_niuren_comment_rv_tv_zan_num);
            ivZan = itemView.findViewById(R.id.pop_niuren_comment_rv_iv_zan);
            tvContent = itemView.findViewById(R.id.pop_niuren_comment_rv_tv_content);
            tvHf = itemView.findViewById(R.id.pop_niuren_comment_rv_tv_hf);
            tvCreateTime = itemView.findViewById(R.id.pop_niuren_comment_rv_tv_c_time);
            ivDian = itemView.findViewById(R.id.pop_niuren_comment_rv_iv_dian);
        }
    }

}
