package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.NiurenActivity;
import com.example.administrator.kcapp.activity.VideoActivity;
import com.example.administrator.kcapp.adapter.NiurenLvAdapter;
import com.example.administrator.kcapp.entity.NiurenLvEntity;
import com.example.administrator.kcapp.eventbus.VideoEvent;
import com.example.administrator.kcapp.gsonbean.PostSharpBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/29.
 */

public class FragmentNiuren extends Fragment {

    @BindView(R.id.niuren_lv)
    ListView listView;
    @BindView(R.id.niuren_refresh)
    RefreshLayout refreshLayout;

    private List<NiurenLvEntity> data;
    private NiurenLvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_niuren, null);

        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    private void initData() {

        ViseHttp.POST("api/post/sharp")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        PostSharpBean postSharpBean = gson.fromJson(data, PostSharpBean.class);
                        if(postSharpBean.getData().size()>0){
                            init(postSharpBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private void init(final List<PostSharpBean.DataBean> data) {

        adapter = new NiurenLvAdapter(getContext(), data);

        listView.setAdapter(adapter);

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore();
            }
        });

        adapter.setItemListener(new NiurenLvAdapter.OnItemListener() {
            @Override
            public void onClick(int i, PostSharpBean.DataBean bean) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(data);
                intent.setClass(getContext(), NiurenActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });

    }

}
