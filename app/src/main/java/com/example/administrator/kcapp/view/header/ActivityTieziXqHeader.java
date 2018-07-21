package com.example.administrator.kcapp.view.header;

import android.content.Context;
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
import com.example.administrator.kcapp.adapter.ActivityTieziXqBiaoqianAdapter;
import com.example.administrator.kcapp.adapter.FragmentSyTjCardRvAdapter;
import com.example.administrator.kcapp.gsonbean.PostContentBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.example.administrator.kcapp.view.ninegridview.NineGridTestLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2018/7/2.
 */

public class ActivityTieziXqHeader extends LinearLayout {

    private Context context;

    private ImageView ivTouxiang;
    private TextView tvNickname;
    private TextView tvCreateTime;
    private TextView tvContent;
    private RecyclerView recyclerView;
    private JZVideoPlayerStandard jzVideoPlayerStandard;
    private NineGridTestLayout nineGridTestLayout;
    private OnItemPictureClickListener listener;

    public ActivityTieziXqHeader(Context context, @Nullable AttributeSet attrs, OnItemPictureClickListener listener) {
        super(context, attrs);
        this.context = context;
        this.listener = listener;
        LayoutInflater.from(context).inflate(R.layout.header_tiezi_xq, this, true);

        init();

    }

    private void init() {

        ivTouxiang = findViewById(R.id.activity_tiezi_xq_header_iv_touxiang);
        tvNickname = findViewById(R.id.activity_tiezi_xq_header_tv_nickname);
        tvCreateTime = findViewById(R.id.activity_tiezi_xq_header_tv_c_time);
        tvContent = findViewById(R.id.activity_tiezi_xq_header_tv_content);
        recyclerView = findViewById(R.id.activity_tiezi_xq_header_rv_biaoqian);
        jzVideoPlayerStandard = findViewById(R.id.activity_tiezi_xq_header_video);
        nineGridTestLayout = findViewById(R.id.activity_tiezi_xq_header_ninelayout);

    }

    public void setHeader(PostContentBean.DataBean data) {

        Picasso.with(context).load(Consts.QINIU_URL + data.getUser().getAvatar()).into(ivTouxiang);
        tvNickname.setText(data.getUser().getNickname());
        tvCreateTime.setText("发布: " + TimeUtil.LongFormatTime(data.getC_time() + ""));
        tvContent.setText(data.getContent());

        if (data.getClassX().size() > 0) {
            //创建LinearLayoutManager
            LinearLayoutManager manager = new LinearLayoutManager(context);
            //设置为横向滑动
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //设置
            recyclerView.setLayoutManager(manager);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < data.getClassX().size(); i++) {
                list.add("#" + data.getClassX().get(i).getName() + " ");
            }
            ActivityTieziXqBiaoqianAdapter adapter = new ActivityTieziXqBiaoqianAdapter(list);
            recyclerView.setAdapter(adapter);
        }

        if (data.getType().equals("video")) {
            jzVideoPlayerStandard.setVisibility(View.VISIBLE);
            nineGridTestLayout.setVisibility(View.GONE);
            jzVideoPlayerStandard.setUp(Consts.QINIU_URL + data.getVideo(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                    "");
            Picasso.with(context).load(Consts.QINIU_URL + data.getCover() + "-690x388").into(jzVideoPlayerStandard.thumbImageView);
        }

        if (data.getType().equals("news")) {
            jzVideoPlayerStandard.setVisibility(View.GONE);
            nineGridTestLayout.setVisibility(View.VISIBLE);
            //九宫格
            nineGridTestLayout.setListener(listener);
            nineGridTestLayout.setItemPosition(0);
            nineGridTestLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            nineGridTestLayout.setSpacing(5); //动态设置图片之间的间隔
            nineGridTestLayout.setUrlList(data.getImages());
        }

    }

}
