package com.example.administrator.kcapp.viewpagercard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyTjCardRvAdapter;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData = new ArrayList<>();
    private float mBaseElevation;

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        cardView.setMaxCardElevation(5);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, View view) {
        final ImageView img = view.findViewById(R.id.fragment_sy_tj_card_img);
        ImageView touxiang = view.findViewById(R.id.fragment_sy_tj_card_touxiang);
        TextView nickname = view.findViewById(R.id.fragment_sy_tj_card_title);
        RecyclerView recyclerView = view.findViewById(R.id.fragment_sy_tj_card_rv);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(new CardItem(item.getId(), item.getAvatar(), item.getImgUrl(), item.getNickName(), item.getTitle(), item.getVideo(), item.getContent(), item.getComment_num(), item.getPraise_num(), item.getFenlei()));
                intent.setClass(view.getContext(), ShipinXqActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        if(!TextUtils.isEmpty(item.getImgUrl())){
            Picasso.with(view.getContext()).load(Consts.QINIU_URL + item.getImgUrl()+"-690x388").into(img);
        }

        Picasso.with(view.getContext()).load(Consts.QINIU_URL + item.getAvatar()).into(touxiang);

        if(!TextUtils.isEmpty(item.getNickName())){
            nickname.setText(item.getNickName());
        }

        if (item.getFenlei() != null) {
            //创建LinearLayoutManager
            LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
            //设置为横向滑动
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            //设置
            recyclerView.setLayoutManager(manager);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < item.getFenlei().size(); i++) {
                list.add("#" + item.getFenlei().get(i).getName() + " ");
            }
            FragmentSyTjCardRvAdapter adapter = new FragmentSyTjCardRvAdapter(list);
            recyclerView.setAdapter(adapter);
        }
    }

}
