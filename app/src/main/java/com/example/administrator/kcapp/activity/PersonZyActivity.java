package com.example.administrator.kcapp.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.MyPagerAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.fragment.FragmentGzDt;
import com.example.administrator.kcapp.fragment.FragmentGzZp;
import com.example.administrator.kcapp.fragment.FragmentPersonCj;
import com.example.administrator.kcapp.fragment.FragmentPersonDt;
import com.example.administrator.kcapp.fragment.FragmentPersonZh;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.squareup.picasso.Picasso;

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

public class PersonZyActivity extends AppCompatActivity {

    @BindView(R.id.activity_person_zy_iv_back)
    RelativeLayout ivBack;
    @BindView(R.id.activity_person_zy_tab)
    MagicIndicator mTb;
    @BindView(R.id.activity_person_zy_viewpager)
    ViewPager mVp;
    @BindView(R.id.activity_person_zy_iv_touxiang)
    ImageView ivTouxiang;
    @BindView(R.id.activity_person_zy_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.activity_person_zy_tv_create_time)
    TextView tvCtime;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_zy);

        StatusBarUtils.setStatusBar(PersonZyActivity.this, Color.parseColor("#ffffff"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        initView();
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

    private void initView() {

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        String nickname = L.decrypt(pref.getString("nickname", ""), Consts.LKEY);
        String avatar = L.decrypt(pref.getString("avatar", ""), Consts.LKEY);
        String ctime = L.decrypt(pref.getString("c_time", ""), Consts.LKEY);
        Picasso.with(PersonZyActivity.this).load(Consts.QINIU_URL+avatar).into(ivTouxiang);
        tvNickname.setText(nickname);
        tvCtime.setText("");

    }

    private void init() {

        CommonNavigator commonNavigator = new CommonNavigator(PersonZyActivity.this);
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
        mTitleList.add("动态");
        mTitleList.add("智慧");
        mTitleList.add("成就");
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new FragmentPersonDt());
        mFragmentList.add(new FragmentPersonZh());
        mFragmentList.add(new FragmentPersonCj());
    }

    @OnClick({R.id.activity_person_zy_iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_person_zy_iv_back:
                PersonZyActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PersonZyActivity.this.finish();
    }
}
