package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.gsonbean.SharpBean;
import com.example.administrator.kcapp.utils.Utils;
import com.example.administrator.kcapp.viewpagercard.CardFragmentPagerAdapter;
import com.example.administrator.kcapp.viewpagercard.CardItem;
import com.example.administrator.kcapp.viewpagercard.CardPagerAdapter;

import java.util.List;

/**
 * Created by a on 2018/6/10.
 */

public class FragmentSyTjHeader extends LinearLayout {

    private ViewPager viewPager;
    private LinearLayout llJingxuan;
    private CardPagerAdapter mCardAdapter;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private Context context;
    private FragmentActivity fragmentActivity;
    private FragmentSyTjHeaderListener listener;

    public FragmentSyTjHeader(Context context, FragmentActivity fragmentActivity, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.header_sy_tj, this, true);

        init();

    }

    private void init() {

        viewPager = findViewById(R.id.viewPager);
        llJingxuan = findViewById(R.id.fragment_sy_tj_lv_header_jingxuan);

        llJingxuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
            }
        });

    }

    public void setCardView(List<SharpBean.DataBean> data) {

        mCardAdapter = new CardPagerAdapter();

        for(int i = 0; i<data.size(); i++){
            mCardAdapter.addCardItem(new CardItem(data.get(i).getId()+"", data.get(i).getAvatar(), data.get(i).getCover(), data.get(i).getNickname(), data.get(i).getTitle(),
                    data.get(i).getVideo(), data.get(i).getContent(), data.get(i).getComment_num()+"", data.get(i).getPraise_num()+"", data.get(i).getClassX()));
        }

        mFragmentCardAdapter = new CardFragmentPagerAdapter(fragmentActivity.getSupportFragmentManager(),
                Utils.dpToPixels(1, getContext()));

        viewPager.setAdapter(mCardAdapter);
        viewPager.setOffscreenPageLimit(3);

    }

    public interface FragmentSyTjHeaderListener{
        void onClick(View view);
    }

    public void setOnFragmentSyFxHeaderListener(FragmentSyTjHeaderListener listener){
        this.listener = listener;
    }

}
