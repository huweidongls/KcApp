package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.gsonbean.PostCommentBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ActivityTieziXqAdapter extends BaseAdapter {

    private Context context;
    private List<PostCommentBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ActivityTieziXqAdapter(Context context, List<PostCommentBean.DataBean> data) {
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
            view = inflater.inflate(R.layout.listview_activity_tiezi_xq, viewGroup, false);
            holder.ivTouxiang = view.findViewById(R.id.activity_tiezi_xq_lv_iv_touxiang);
            holder.tvNickname = view.findViewById(R.id.activity_tiezi_xq_lv_tv_nickname);
            holder.tvContent = view.findViewById(R.id.activity_tiezi_xq_lv_tv_content);
            holder.tvZanNum = view.findViewById(R.id.activity_tiezi_xq_lv_tv_zan_num);
            holder.tvHuifu = view.findViewById(R.id.activity_tiezi_xq_lv_tv_huifu);
            holder.tvCreateTime = view.findViewById(R.id.activity_tiezi_xq_lv_tv_c_time);
            holder.ivZan = view.findViewById(R.id.activity_tiezi_xq_lv_iv_zan);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(Consts.QINIU_URL+data.get(i).getAvatar()).into(holder.ivTouxiang);
        holder.tvNickname.setText(data.get(i).getNickname());
        holder.tvZanNum.setText(data.get(i).getPraise_num()+"");
        holder.tvContent.setText(data.get(i).getContent());
        holder.tvCreateTime.setText(TimeUtil.LongFormatTime(data.get(i).getC_time()+""));

        return view;
    }

    private class ViewHolder{

        private ImageView ivTouxiang;
        private TextView tvNickname;
        private TextView tvContent;
        private TextView tvZanNum;
        private TextView tvHuifu;
        private TextView tvCreateTime;
        private ImageView ivZan;

    }

}
