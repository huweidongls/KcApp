package com.example.administrator.kcapp.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.NiurenActivity;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyTjAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.gsonbean.ClassHotPostBean;
import com.example.administrator.kcapp.gsonbean.SharpBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.WeiboDialogUtils;
import com.example.administrator.kcapp.view.header.FragmentSyTjHeader;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class FragmentSyTj extends BaseFragment {

    @BindView(R.id.fragment_sy_tj_lv)
    ListView listView;
    @BindView(R.id.fragment_sy_tj_refreshLayout)
    RefreshLayout refreshLayout;

    private FragmentSyTjAdapter adapter;

    private FragmentSyTjHeader fragmentSyTjHeader;

    private boolean mShowingFragments = false;

    private String uid = "";
    private String token = "";
    private String id;

    private Dialog loadingDialog;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_sy_tj, null);
//        ButterKnife.bind(this, view);
//
//        fragmentSyTjHeader = new FragmentSyTjHeader(getContext(), getActivity(), null);
//        id = getArguments().getString("id");
//
////        initData(id);
//
//        return view;
//    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sy_tj, null);
        ButterKnife.bind(this, view);
        fragmentSyTjHeader = new FragmentSyTjHeader(getContext(), getActivity(), null);
        id = getArguments().getString("id");
        return view;
    }

    @Override
    public void initData() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        initData(id);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);
    }

    private void initData(String id) {

        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        ViseHttp.POST("api/index/sharp")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        SharpBean sharpBean = gson.fromJson(data, SharpBean.class);
                        if(sharpBean.getData().size()>0){
                            fragmentSyTjHeader.setCardView(sharpBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("222", "网络请求失败！");
                    }
                });

        if (TextUtils.isEmpty(uid)||uid.equals("")) {
            ViseHttp.POST("api/index/classHotPost")
                    .addParam("cid", id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("111", data + "");
                            Gson gson = new Gson();
                            ClassHotPostBean classHotPostBean = gson.fromJson(data, ClassHotPostBean.class);
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            initListView(classHotPostBean.getData());
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                        }
                    });
        }else {
            ViseHttp.POST("api/index/classHotPost")
                    .addParam("uid", uid)
                    .addParam("token", token)
                    .addParam("cid", id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Log.e("111", data + "");
                            Gson gson = new Gson();
                            ClassHotPostBean classHotPostBean = gson.fromJson(data, ClassHotPostBean.class);
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            initListView(classHotPostBean.getData());
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                        }
                    });
        }

    }

    private void initListView(List<ClassHotPostBean.DataBean> data){

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000);//传入false表示加载失败
            }
        });

        listView.setFocusable(false);

        listView.addHeaderView(fragmentSyTjHeader);

        fragmentSyTjHeader.setOnFragmentSyFxHeaderListener(new FragmentSyTjHeader.FragmentSyTjHeaderListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.fragment_sy_tj_lv_header_jingxuan:
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.selectNiu();
                        break;
                }
            }
        });

        adapter = new FragmentSyTjAdapter(getContext(), data);
        listView.setAdapter(adapter);

        adapter.setOnSyTjListClickListener(new FragmentSyTjAdapter.FragmentSyTjListener() {
            @Override
            public void onClick(int position, View view) {
                //adapter回调
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
