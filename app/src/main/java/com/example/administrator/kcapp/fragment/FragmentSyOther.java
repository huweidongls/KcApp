package com.example.administrator.kcapp.fragment;

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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ImagePreviewActivity;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyOtherAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.entity.FragmentSyOtherEntity;
import com.example.administrator.kcapp.eventbus.FragmentOtherEvent;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.ClassGetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;
import com.example.administrator.kcapp.gsonbean.ClassHotPostBean;
import com.example.administrator.kcapp.gsonbean.GetHotPostBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.WeiboDialogUtils;
import com.example.administrator.kcapp.view.header.FragmentSyOtherHeader;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentSyOther extends BaseFragment {

    @BindView(R.id.fragment_sy_other_lv)
    ListView listView;

    private List<ClassGetNewPostBean.DataBean> dataList;
    private FragmentSyOtherAdapter adapter;

    private FragmentSyOtherHeader syOtherHeader;

    private Bundle mReenterState;
    private int itemPosition;

    private String uid = "";
    private String token = "";

    private String id;

    private Dialog loadingDialog;

    public static FragmentSyOther newInstance(String id) {
        FragmentSyOther fragmentSyOther = new FragmentSyOther();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        //传递参数
        fragmentSyOther.setArguments(bundle);
        return fragmentSyOther;

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_sy_other, null);
//        ButterKnife.bind(this, view);
//
//        if(!EventBus.getDefault().isRegistered(this)){
//            EventBus.getDefault().register(this);
//        }
//        syOtherHeader = new FragmentSyOtherHeader(getContext(), null);
//        id = getArguments().getString("id");
//
//        initData(id);
//
//        return view;
//    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sy_other, null);
        ButterKnife.bind(this, view);

        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        syOtherHeader = new FragmentSyOtherHeader(getContext(), null);
        id = getArguments().getString("id");
        return view;
    }

    @Override
    public void initData() {
        loadingDialog = WeiboDialogUtils.createLoadingDialog(getContext(), "加载中...");
        initData(id);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(FragmentOtherEvent event) {
        if(event.getData() != null){
            dataList = event.getData();
            adapter.notifyDataSetChanged();
        }
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

        ViseHttp.POST("api/classify/getHotPost")
                .addParam("cid", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetHotPostBean getHotPostBean = gson.fromJson(data, GetHotPostBean.class);
                        if (getHotPostBean.getData().size() > 0) {
                            syOtherHeader.setHotPost(getHotPostBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        if (TextUtils.isEmpty(uid) || uid.equals("")) {
            ViseHttp.POST("api/classify/getNewPost")
                    .addParam("cid", id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            ClassGetNewPostBean classGetNewPostBean = gson.fromJson(data, ClassGetNewPostBean.class);
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            if (classGetNewPostBean.getData().size() > 0) {
                                dataList = classGetNewPostBean.getData();
                                initListView();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                        }
                    });
        } else {
            ViseHttp.POST("api/classify/getNewPost")
                    .addParam("uid", uid)
                    .addParam("token", token)
                    .addParam("cid", id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            ClassGetNewPostBean classGetNewPostBean = gson.fromJson(data, ClassGetNewPostBean.class);
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            if (classGetNewPostBean.getData().size() > 0) {
                                dataList = classGetNewPostBean.getData();
                                initListView();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                        }
                    });
        }

//        ViseHttp.POST("api/classify/getHotComment")
//                .addParam("cid", id)
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//                        Gson gson = new Gson();
//                        ClassGetHotCommentBean classGetHotCommentBean = gson.fromJson(data, ClassGetHotCommentBean.class);
//                        if (classGetHotCommentBean.getData().size() > 0) {
//                            initListView(classGetHotCommentBean.getData());
//                        }
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

    }

    private void initListView() {

        adapter = new FragmentSyOtherAdapter(getContext(), dataList);
        adapter.setPictureClickListener(new OnItemPictureClickListener() {
            @Override
            public void onItemPictureClick(int itemPostion, int i, String url, List<String> urlList, ImageView imageView) {
                itemPosition = itemPostion;
                Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, itemPosition);
                intent.putExtra(Consts.START_IAMGE_POSITION, i);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });
        listView.addHeaderView(syOtherHeader);
        listView.setAdapter(adapter);

        syOtherHeader.setOnFragmentSyOtherHeaderListener(new FragmentSyOtherHeader.FragmentSyOtherHeaderListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch (view.getId()) {
                    case R.id.fragment_sy_other_lv_header_iv1:
                        intent.setClass(getContext(), ShipinXqActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.fragment_sy_other_lv_header_rl_shipin1:
                        intent.setClass(getContext(), ShipinXqActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.fragment_sy_other_lv_header_rl_shipin2:
                        intent.setClass(getContext(), ShipinXqActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(this);
    }
}
