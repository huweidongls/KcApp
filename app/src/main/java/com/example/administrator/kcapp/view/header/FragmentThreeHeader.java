package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.utils.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class FragmentThreeHeader extends LinearLayout {

    private Banner banner;
    private TextView tvZhekou;
    private TextView tvZhekou1;

    private List<Integer> images = new ArrayList<>();

    public FragmentThreeHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.header_three_kc, this, true);

        init();

    }

    private void init() {

        tvZhekou = findViewById(R.id.fragment_three_header_tv_top_zhekou);
        tvZhekou1 = findViewById(R.id.fragment_three_header_tv_top_zhekou1);

        tvZhekou.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG ); //中间横线
        tvZhekou.getPaint().setAntiAlias(true);// 抗锯齿
        tvZhekou1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG ); //中间横线
        tvZhekou1.getPaint().setAntiAlias(true);// 抗锯齿

        images.add(R.drawable.img1);
        images.add(R.drawable.img1);
        images.add(R.drawable.img1);

        banner = findViewById(R.id.fragment_three_header_banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

}
