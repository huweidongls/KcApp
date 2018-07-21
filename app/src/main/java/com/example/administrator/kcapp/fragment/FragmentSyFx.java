package com.example.administrator.kcapp.fragment;

import android.app.Dialog;
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

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.adapter.FragmentSyFxAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.gsonbean.GetCenterAdvBean;
import com.example.administrator.kcapp.gsonbean.GetHotClassBean;
import com.example.administrator.kcapp.gsonbean.GetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.GetPostRankBean;
import com.example.administrator.kcapp.gsonbean.GetTopAdvBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.WeiboDialogUtils;
import com.example.administrator.kcapp.view.header.FragmentSyFxHeader;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/8 0008.
 */

public class FragmentSyFx extends BaseFragment {

    @BindView(R.id.fragment_sy_fx_lv)
    ListView listView;
    @BindView(R.id.fragment_sy_fx_refreshLayout)
    RefreshLayout refreshLayout;

    private FragmentSyFxAdapter adapter;

    private FragmentSyFxHeader syFxHeader;

    private String uid = "";
    private String token = "";
    private Dialog loadingDialog;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_sy_fx, null);
//        ButterKnife.bind(this, view);
//
//        syFxHeader = new FragmentSyFxHeader(getContext(), getActivity(), null);
//
////        initData();
//
//        return view;
//    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sy_fx, null);
        ButterKnife.bind(this, view);
        syFxHeader = new FragmentSyFxHeader(getContext(), getActivity(), null);
        return view;
    }

    @Override
    public void initData() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        initData1();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);
    }

    private void initData1() {

        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        //获取幻灯片
        ViseHttp.POST("api/find/getTopAdv")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetTopAdvBean getTopAdvBean = gson.fromJson(data, GetTopAdvBean.class);
                        syFxHeader.setCardView(getTopAdvBean.getData());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        //获取广告
        ViseHttp.POST("api/find/getCenterAdv")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetCenterAdvBean getCenterAdvBean = gson.fromJson(data, GetCenterAdvBean.class);
                        syFxHeader.setAdvertisement(getCenterAdvBean.getData().getImage());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        //获取热帖排行
        ViseHttp.POST("api/find/getPostRank")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetPostRankBean getPostRankBean = gson.fromJson(data, GetPostRankBean.class);
                        syFxHeader.setRankings(getPostRankBean.getData());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        if (TextUtils.isEmpty(uid)||uid.equals("")) {
            //获取热门类别
            ViseHttp.POST("api/find/getHotClass")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            GetHotClassBean getHotClassBean = gson.fromJson(data, GetHotClassBean.class);
                            syFxHeader.setHotClass(getHotClassBean.getData());
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
            //已登录状态传uid与token获取是否关注
            ViseHttp.POST("api/find/getHotClass")
                    .addParam("uid", uid)
                    .addParam("token", token)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            GetHotClassBean getHotClassBean = gson.fromJson(data, GetHotClassBean.class);
                            syFxHeader.setHotClass(getHotClassBean.getData());
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }

        //获取热门评论
        ViseHttp.POST("api/find/getHotComment")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetHotCommentBean getHotCommentBean = gson.fromJson(data, GetHotCommentBean.class);
                        WeiboDialogUtils.closeDialog(loadingDialog);
                        if (getHotCommentBean.getData() != null) {
                            init(getHotCommentBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        WeiboDialogUtils.closeDialog(loadingDialog);
                    }
                });

    }

    private void init(List<GetHotCommentBean.DataBean> data) {

        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        listView.addHeaderView(syFxHeader);
        adapter = new FragmentSyFxAdapter(getContext(), data);

        listView.setAdapter(adapter);

    }
}
