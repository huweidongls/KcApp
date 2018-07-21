package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyTjCardRvAdapter;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.GetHotPostBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.example.administrator.kcapp.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class ActivityFenleiXqHeader extends LinearLayout {

    private Context context;

    private TextView topTitle;
    private TextView topContent;
    private ImageView ivTop;

    private ImageView ivHeader;
    private TextView tvTime;
    private ImageView ivTouxiang;
    private TextView tvTitle;
    private RecyclerView rv;

    public ActivityFenleiXqHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.header_fenlei_xq, this, true);
        
        init();
        
    }

    private void init() {

        topTitle = findViewById(R.id.activity_fenlei_xq_lv_header_top_tv);
        topContent = findViewById(R.id.activity_fenlei_xq_lv_header_top_content);
        ivTop = findViewById(R.id.activity_fenlei_xq_lv_header_top_iv);
        ivHeader = findViewById(R.id.fragment_sy_fx_lv_header_iv1);
        tvTime = findViewById(R.id.fragment_sy_fx_lv_header_time);
        ivTouxiang = findViewById(R.id.fragment_sy_fx_lv_header_touxiang);
        tvTitle = findViewById(R.id.fragment_sy_fx_lv_header_tv_title);
        rv = findViewById(R.id.fragment_sy_fx_lv_header_rv);

    }

    public void setTop(String title, String content, String cover){

        topTitle.setText(title);
        topContent.setText(content);
        Picasso.with(context).load(Consts.BASE_URL+cover).into(ivTop);

    }

    public void setHeader(final List<GetHotPostBean.DataBean> data){

        Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getCover()).into(ivHeader);
        ivHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getId()+""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getAvatar()).into(ivTouxiang);
        tvTitle.setText(data.get(0).getTitle());
        tvTime.setText(Utils.secToTime(data.get(0).getVideo_time_length()));

        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyTjCardRvAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(0).getClassX().size() >0) {
            rv.setLayoutManager(manager);
            for (int i = 0; i < data.get(0).getClassX().size(); i++) {
                list.add("#" + data.get(0).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rv.setAdapter(adapter);
        }

    }

}
