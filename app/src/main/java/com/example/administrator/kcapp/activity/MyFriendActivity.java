package com.example.administrator.kcapp.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.MyPagerAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.fragment.FragmentFriend;
import com.example.administrator.kcapp.fragment.FragmentPersonCj;
import com.example.administrator.kcapp.fragment.FragmentPersonDt;
import com.example.administrator.kcapp.fragment.FragmentPersonZh;
import com.example.administrator.kcapp.fragment.FragmentStudent;
import com.example.administrator.kcapp.fragment.FragmentTeacher;
import com.example.administrator.kcapp.utils.StatusBarUtils;

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

public class MyFriendActivity extends AppCompatActivity {

    @BindView(R.id.activity_my_friend_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_my_friend_tab)
    MagicIndicator mTb;
    @BindView(R.id.activity_my_friend_viewpager)
    ViewPager mVp;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friend);

        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        StatusBarUtils.setStatusBar(MyFriendActivity.this, Color.parseColor("#fdfdfd"));

        //添加标题
        initTitile();
        //添加fragment
        initFragment();
        //设置适配器
        mVp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
//        mTb.setupWithViewPager(mVp);
        init();

    }

    private void init() {

        CommonNavigator commonNavigator = new CommonNavigator(MyFriendActivity.this);
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
        mTitleList.add("好友");
        mTitleList.add("老师");
        mTitleList.add("学生");
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentFriend());
        mFragmentList.add(new FragmentTeacher());
        mFragmentList.add(new FragmentStudent());
    }

    @OnClick({R.id.activity_my_friend_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_my_friend_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyFriendActivity.this.finish();
    }
}
