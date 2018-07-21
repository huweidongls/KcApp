package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.SyFbActivity;
import com.example.administrator.kcapp.activity.SyFenleiActivity;
import com.example.administrator.kcapp.activity.SyMsgActivity;
import com.example.administrator.kcapp.adapter.MyPagerAdapter;
import com.example.administrator.kcapp.eventbus.FenleiIdEvent;
import com.example.administrator.kcapp.gsonbean.GetClassBean;
import com.example.administrator.kcapp.utils.Utils;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class FragmentSy extends Fragment {

    @BindView(R.id.fragment_sy_tl_tab)
    TabLayout mTb;
    @BindView(R.id.mViewPager)
    ViewPager mVp;
    @BindView(R.id.fragment_sy_iv_top)
    ImageView imageView;
    @BindView(R.id.fragment_sy_iv_msg)
    ImageView ivMsg;
    @BindView(R.id.fragment_sy_fb)
    ImageView ivFb;

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    private String id = "";

    private FragmentSyTj fragmentSyTj;
    private FragmentSyFx fragmentSyFx;
    private FragmentSyOther fragmentSyOther;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sy, null);
        ButterKnife.bind(this, view);

        fragmentSyTj = new FragmentSyTj();
        fragmentSyFx = new FragmentSyFx();
        fragmentSyOther = new FragmentSyOther();

        init();

        return view;
    }

    private void init() {

        try {
            ViseHttp.POST("api/Classify/getClass")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            GetClassBean getClassBean = gson.fromJson(data, GetClassBean.class);
                            for (int i = 0; i<getClassBean.getData().size(); i++){
                                if(i == getClassBean.getData().size()-1){
                                    id = id + getClassBean.getData().get(i).getId();
                                }else {
                                    id = id + getClassBean.getData().get(i).getId() + ",";
                                }
                            }
                            EventBus.getDefault().postSticky(new FenleiIdEvent(id));
                            //添加标题
                            initTitile(getClassBean);
                            //添加fragment
                            initFragment(getClassBean);
                            //设置适配器
                            mVp.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList));
                            //将tablayout与fragment关联
                            mTb.setupWithViewPager(mVp);
                            mTb.getTabAt(1).select();
                            reflex(mTb);
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            Log.e("222", "网络请求失败！");
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void reflex(final TabLayout tabLayout){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
                    mTabStripField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
                    int dp10 = Utils.dip2px(tabLayout.getContext(), 10);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initTitile(GetClassBean getClassBean) {
        mTitleList = new ArrayList<>();
        mTitleList.add("发现");
        mTitleList.add("推荐");
        for (int i = 0; i<getClassBean.getData().size(); i++){
            mTitleList.add(getClassBean.getData().get(i).getName());
        }
        //设置tablayout模式
        mTb.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tablayout获取集合中的名称
        for(int i = 0; i<mTitleList.size(); i++){
            mTb.addTab(mTb.newTab().setText(mTitleList.get(i)));
        }
    }

    private void initFragment(GetClassBean getClassBean) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragmentSyTj.setArguments(bundle);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(fragmentSyFx);
        mFragmentList.add(fragmentSyTj);
        for(int i = 0; i<getClassBean.getData().size(); i++){
            mFragmentList.add(FragmentSyOther.newInstance(getClassBean.getData().get(i).getId()+""));
        }
    }

    @OnClick({R.id.fragment_sy_iv_top, R.id.fragment_sy_iv_msg, R.id.fragment_sy_fb})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.fragment_sy_iv_top:
                intent.setClass(getContext(), SyFenleiActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open, 0);
                break;
            case R.id.fragment_sy_iv_msg:
                intent.setClass(getContext(), SyMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_sy_fb:
                intent.setClass(getContext(), SyFbActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.sy_fb_open, 0);
                break;
        }
    }

}
