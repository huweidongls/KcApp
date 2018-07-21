package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.PlActivity;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.entity.ActivityFenleiXqEntity;
import com.example.administrator.kcapp.gsonbean.GetHotCommentBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class ActivityFenleiXqAdapter extends BaseAdapter {

    private Context context;
    private List<GetHotCommentBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    public ActivityFenleiXqAdapter(Context context, List<GetHotCommentBean.DataBean> data) {
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
            view = inflater.inflate(R.layout.listview_activity_fenlei_xq, viewGroup, false);
            holder.ivTouxiang = view.findViewById(R.id.activity_fenlei_xq_lv_touxiang);
            holder.nickName = view.findViewById(R.id.activity_fenlei_xq_lv_nickname);
            holder.tvTime = view.findViewById(R.id.activity_fenlei_xq_lv_tv_time);
            holder.tvPinglun = view.findViewById(R.id.activity_fenlei_xq_lv_pinglun);
            holder.iv = view.findViewById(R.id.activity_fenlei_xq_lv_iv);
            holder.tvTitle = view.findViewById(R.id.activity_fenlei_xq_lv_title);
            holder.tvZanNum = view.findViewById(R.id.activity_fenlei_xq_lv_zan_num);
            holder.rv = view.findViewById(R.id.activity_fenlei_xq_lv_rv_biaoqian);
            holder.llPinglun = view.findViewById(R.id.activity_fenlei_xq_lv_p);
            holder.llXq = view.findViewById(R.id.activity_fenlei_xq_lv_content);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(Consts.BASE_URL+data.get(i).getAvatar()).into(holder.ivTouxiang);
        holder.nickName.setText(data.get(i).getNickname());
        holder.tvTime.setText("发布: "+ TimeUtil.LongFormatTime(String.valueOf(data.get(i).getComment_time())));
        holder.tvPinglun.setText(data.get(i).getC_content());
        Picasso.with(context).load(Consts.BASE_URL+data.get(i).getCover()).into(holder.iv);
        holder.tvTitle.setText(data.get(i).getP_title());
        holder.tvZanNum.setText(data.get(i).getPraise_num()+"");

        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyOtherRvBiaoqianAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(i).getClassX().size() >0) {
            holder.rv.setLayoutManager(manager);
            for (int a = 0; a < data.get(i).getClassX().size(); a++) {
                list.add("#" + data.get(i).getClassX().get(a).getName() + " ");
            }
            adapter = new FragmentSyOtherRvBiaoqianAdapter(list);
            holder.rv.setAdapter(adapter);
        }

        holder.llPinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, PlActivity.class);
                context.startActivity(intent);
            }
        });

        holder.llXq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, ShipinXqActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private class ViewHolder{
        ImageView ivTouxiang;
        TextView nickName;
        TextView tvTime;
        TextView tvPinglun;
        ImageView iv;
        TextView tvTitle;
        TextView tvZanNum;
        RecyclerView rv;
        LinearLayout llPinglun;
        LinearLayout llXq;
    }

}
