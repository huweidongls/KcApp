package com.example.administrator.kcapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.SyFenleiActivity;
import com.example.administrator.kcapp.activity.SyMsgActivity;
import com.example.administrator.kcapp.adapter.MyPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class FragmentGz extends Fragment {

    @BindView(R.id.fragment_gz_tl_tab)
    MagicIndicator mTb;
    @BindView(R.id.fragment_gz_viewpager)
    ViewPager mVp;
    @BindView(R.id.fragment_gz_iv_top)
    ImageView ivFenlei;
    @BindView(R.id.fragment_gz_iv_msg)
    ImageView ivMsg;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gz, null);
        ButterKnife.bind(this, view);

        //添加标题
        initTitile();
        //添加fragment
        initFragment();
        //设置适配器
        mVp.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
//        mTb.setupWithViewPager(mVp);
        init();

        return view;
    }

    private void init() {

        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitleList.get(index));
                colorTransitionPagerTitleView.getPaint().setFakeBoldText(true);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mVp.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                return indicator;
            }
        });
        mTb.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTb, mVp);

    }

    private void initTitile() {
        mTitleList = new ArrayList<>();
        mTitleList.add("作品");
        mTitleList.add("动态");
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentGzZp());
        mFragmentList.add(new FragmentGzDt());
    }

    @OnClick({R.id.fragment_gz_iv_top, R.id.fragment_gz_iv_msg})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.fragment_gz_iv_top:
                intent.setClass(getContext(), SyFenleiActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.fragment_gz_iv_msg:
                intent.setClass(getContext(), SyMsgActivity.class);
                startActivity(intent);
                break;
        }
    }

}
