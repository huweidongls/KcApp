package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.activity.VideoActivity;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.eventbus.VideoEvent;
import com.example.administrator.kcapp.gsonbean.GetHotCommentBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.example.administrator.kcapp.viewpagercard.CardItem;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class FragmentSyFxAdapter extends BaseAdapter {

    private Context context;
    private List<GetHotCommentBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    public FragmentSyFxAdapter(Context context, List<GetHotCommentBean.DataBean> data) {
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
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_fragment_sy_fx, null);
            holder.ivTouxiang = view.findViewById(R.id.fragment_sy_fx_lv_touxiang);
            holder.nickName = view.findViewById(R.id.fragment_sy_fx_lv_nickname);
            holder.ivCover = view.findViewById(R.id.fragment_sy_fx_lv_cover);
            holder.tvPl = view.findViewById(R.id.fragment_sy_fx_lv_pinglun);
            holder.tvTitle = view.findViewById(R.id.fragment_sy_fx_lv_title);
            holder.tvCommentTime = view.findViewById(R.id.fragment_sy_fx_lv_time);
//            holder.tvZanNum = view.findViewById(R.id.fragment_sy_fx_lv_zan_num);
            holder.rvBiaoqian = view.findViewById(R.id.fragment_sy_fx_lv_rv_biaoqian);
            holder.llContent = view.findViewById(R.id.fragment_sy_fx_lv_ll_content);
            holder.tvHuifu = view.findViewById(R.id.fragment_sy_fx_lv_huifu);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getAvatar()).into(holder.ivTouxiang);
        holder.nickName.setText(data.get(i).getNickname());
        Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getCover()).into(holder.ivCover);
        holder.tvPl.setText(data.get(i).getC_content());
        holder.tvTitle.setText(data.get(i).getP_title());
        holder.tvCommentTime.setText(TimeUtil.LongFormatTime(String.valueOf(data.get(i).getComment_time())));
//        holder.tvCommentTime.setText(TimeUtil.LongFormatTime(String.valueOf(System.currentTimeMillis()-1000000000)));
//        holder.tvZanNum.setText(data.get(i).getPraise_num()+"");

        if (data.get(i).getClassX() != null) {
            //设置分类标签
            List<String> l = new ArrayList<>();
            Log.e("234", i + "");
            for (int a = 0; a < data.get(i).getClassX().size(); a++) {
                l.add("#" + data.get(i).getClassX().get(a).getName() + " ");
            }
            FragmentSyTjCardRvAdapter adapter = new FragmentSyTjCardRvAdapter(l);
            //创建LinearLayoutManager
            LinearLayoutManager manager = new LinearLayoutManager(context);
            //设置为横向滑动
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //设置
            holder.rvBiaoqian.setLayoutManager(manager);
            holder.rvBiaoqian.setAdapter(adapter);
        }

        holder.llContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getPid() + ""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        holder.tvHuifu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getPid() + ""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        return view;
    }

    private class ViewHolder {

        ImageView ivTouxiang;
        TextView nickName;
        ImageView ivCover;
        TextView tvPl;
        TextView tvTitle;
        TextView tvCommentTime;
        //        TextView tvZanNum;
        RecyclerView rvBiaoqian;
        LinearLayout llContent;
        TextView tvHuifu;

    }

}
