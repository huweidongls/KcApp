package com.example.administrator.kcapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivityFenleiXqAdapter;
import com.example.administrator.kcapp.adapter.FragmentSyOtherAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.entity.ActivityFenleiXqEntity;
import com.example.administrator.kcapp.eventbus.FenleiXqEvent;
import com.example.administrator.kcapp.eventbus.FragmentOtherEvent;
import com.example.administrator.kcapp.gsonbean.ClassGetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;
import com.example.administrator.kcapp.gsonbean.GetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.GetHotPostBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.view.header.ActivityFenleiXqHeader;
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
import butterknife.OnClick;

public class FenleiXqActivity extends AppCompatActivity {

    @BindView(R.id.activity_fenlei_xq_lv)
    ListView listView;
    @BindView(R.id.activity_fenlei_xq_back)
    RelativeLayout ivBack;

    private List<ClassGetNewPostBean.DataBean> dataList;
    private FragmentSyOtherAdapter adapter;

    private ActivityFenleiXqHeader xqHeader;
    private String cid;
    private String topTitle;
    private String content;
    private String cover;

    private int itemPosition;

    private String uid = "";
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenlei_xq);

        StatusBarUtils.setStatusBar(FenleiXqActivity.this, Color.parseColor("#000000"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        EventBus.getDefault().register(FenleiXqActivity.this);
        xqHeader = new ActivityFenleiXqHeader(this, null);
        xqHeader.setTop(topTitle, content, cover);

        initData();

    }

    private void initData() {

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        ViseHttp.POST("api/classify/getHotPost")
                .addParam("cid", cid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetHotPostBean getHotPostBean = gson.fromJson(data, GetHotPostBean.class);
                        if(getHotPostBean.getData().size()>0){
                            xqHeader.setHeader(getHotPostBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        if (TextUtils.isEmpty(uid)||uid.equals("")) {
            ViseHttp.POST("api/classify/getNewPost")
                    .addParam("cid", cid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            ClassGetNewPostBean classGetNewPostBean = gson.fromJson(data, ClassGetNewPostBean.class);
                            if (classGetNewPostBean.getData().size() > 0) {
                                dataList = classGetNewPostBean.getData();
                                init();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }else {
            ViseHttp.POST("api/classify/getNewPost")
                    .addParam("uid", uid)
                    .addParam("token", token)
                    .addParam("cid", cid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            ClassGetNewPostBean classGetNewPostBean = gson.fromJson(data, ClassGetNewPostBean.class);
                            if (classGetNewPostBean.getData().size() > 0) {
                                dataList = classGetNewPostBean.getData();
                                init();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }

    }

    private void init() {

        adapter = new FragmentSyOtherAdapter(this, dataList);
        adapter.setPictureClickListener(new OnItemPictureClickListener() {
            @Override
            public void onItemPictureClick(int itemPostion, int i, String url, List<String> urlList, ImageView imageView) {
                itemPosition = itemPostion;
                Intent intent = new Intent(FenleiXqActivity.this, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, itemPosition);
                intent.putExtra(Consts.START_IAMGE_POSITION, i);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                startActivity(intent);
                overridePendingTransition(R.anim.photoview_open, 0);
            }
        });
        listView.addHeaderView(xqHeader);
        listView.setAdapter(adapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent1(FragmentOtherEvent event) {
        Log.e("222", "shoudao");
        if(event.getData() != null){
            Log.e("222", "走了");
            dataList = event.getData();
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(FenleiXqEvent event){
        cid = event.getId();
        topTitle = event.getTopTitle();
        content = event.getContent();
        cover = event.getCover();
    }

    @OnClick({R.id.activity_fenlei_xq_back})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_fenlei_xq_back:
                FenleiXqActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SyFenleiActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FenleiXqActivity.this.finish();
    }

}
