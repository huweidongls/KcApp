package com.example.administrator.kcapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivityFbDtAdapter;
import com.example.administrator.kcapp.adapter.ActivitySyFbDtGridAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.eventbus.FbDtSelectZtEvent;
import com.example.administrator.kcapp.gsonbean.SavePostBean;
import com.example.administrator.kcapp.gsonbean.UploadTokenBean;
import com.example.administrator.kcapp.utils.ACache;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.WeiboDialogUtils;
import com.google.gson.Gson;
import com.library.flowlayout.FlowLayoutManager;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SyFbDtActivity extends AppCompatActivity {

    @BindView(R.id.activity_sy_fb_dt_back)
    RelativeLayout ivBack;
    @BindView(R.id.activity_sy_fb_dt_tv_xuanzezhuti)
    TextView tvSelectZt;
    @BindView(R.id.activity_sy_fb_dt_rv_zt)
    RecyclerView recyclerView;
    @BindView(R.id.activity_sy_fb_dt_tj)
    ImageView ivAdd;
    @BindView(R.id.activity_sy_fb_dt_rv_img)
    RecyclerView rvGrid;
    @BindView(R.id.activity_sy_fb_dt_sc)
    TextView tvSc;
    @BindView(R.id.activity_sy_fb_dt_et_content)
    EditText etContent;
    @BindView(R.id.activity_sy_fb_dt_tv_text_num)
    TextView tvTextNum;

    private List<String> data;
    private ActivityFbDtAdapter adapter;

    /**
     * 七牛token
     */
    private String token = "";
    /**
     * 用户token
     */
    private String userToken = "";
    private String uid = "";
    /**
     * 用户选择的分类
     */
    private String cid = "";

    private UploadManager uploadManager;

    /**
     * 选择的图片集合
     */
    private List<String> mList;
    private ActivitySyFbDtGridAdapter gridAdapter;

    private Dialog loadingDialog;

    private List<String> scList;

    private static final int REQUEST_CODE = 0x00000011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_fb_dt);

        StatusBarUtils.setStatusBar(SyFbDtActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(SyFbDtActivity.this);

        initInfo();
        init();

    }

    private void initInfo() {

        Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();
        uploadManager = new UploadManager(config);

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        userToken = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        final ACache aCache = ACache.get(SyFbDtActivity.this);
        if (aCache.getAsString("token") == null) {
            ViseHttp.POST("api/base/uploadToken")
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            UploadTokenBean uploadTokenBean = gson.fromJson(data, UploadTokenBean.class);
                            if (uploadTokenBean.getData() != null) {
                                token = uploadTokenBean.getData().getToekn();
                                aCache.put("token", token, ACache.TIME_HOUR * 23);
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
            token = aCache.getAsString("token");
        }

    }

    private void init() {

        if (data != null) {
            adapter = new ActivityFbDtAdapter(data);
            FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
            recyclerView.setLayoutManager(flowLayoutManager);
            recyclerView.setAdapter(adapter);
        }

        mList = new ArrayList<>();
        GridLayoutManager manager = new GridLayoutManager(SyFbDtActivity.this, 5);
        rvGrid.setLayoutManager(manager);
        gridAdapter = new ActivitySyFbDtGridAdapter(mList, new ActivitySyFbDtGridAdapter.OnItemClickListener() {
            @Override
            public void onItemAddClick() {
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
//                        .setSelected(selected) // 把已选的图片传入默认选中。
                        .start(SyFbDtActivity.this, REQUEST_CODE); // 打开相册
            }

            @Override
            public void onItemRemoveClick(int position) {

            }
        });

        rvGrid.setAdapter(gridAdapter);

        etContent.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {

        private CharSequence temp;
        private int editStart ;
        private int editEnd ;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvTextNum.setText(temp.length()+"/500");
            if(temp.length()>500){
                ToastUtil.showShort(SyFbDtActivity.this, "您输入的字数已经超过了限制");
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FbDtSelectZtEvent event) {
        Log.e("333", "123" + event.getData().size() + event.getData().get(0));
        List<String> data = new ArrayList<>();
        for (int i = 0; i < event.getData().size(); i++) {
            data.add(event.getData().get(i).getName());
            if (i == event.getData().size() - 1) {
                cid = cid + event.getData().get(i).getId();
            } else {
                cid = cid + event.getData().get(i).getId() + ",";
            }
        }
        Log.e("222", cid);
        adapter = new ActivityFbDtAdapter(data);
        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        recyclerView.setLayoutManager(flowLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.activity_sy_fb_dt_back, R.id.activity_sy_fb_dt_tv_xuanzezhuti, R.id.activity_sy_fb_dt_tj, R.id.activity_sy_fb_dt_sc})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_sy_fb_dt_back:
                SyFbDtActivity.this.finish();
                break;
            case R.id.activity_sy_fb_dt_tv_xuanzezhuti:
                intent.setClass(SyFbDtActivity.this, FbSelectZtActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_sy_fb_dt_tj:
//                //限数量的多选(比喻最多9张)
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(false)  //设置是否单选
//                        .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
////                        .setSelected(selected) // 把已选的图片传入默认选中。
//                        .start(SyFbDtActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.activity_sy_fb_dt_sc:
                post();
                break;
        }
    }

    /**
     * 图片上传七牛与发布动态
     */
    private void post() {

        loadingDialog = WeiboDialogUtils.createLoadingDialog(SyFbDtActivity.this, "发布中...");

        scList = new ArrayList<>();

        Observable.fromIterable(mList)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        String str = value.substring(value.lastIndexOf("."));
                        final String name = uid + System.currentTimeMillis() + (int) (Math.random() * 100000) + str;
                        uploadManager.put(value, name, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                                        if (info.isOK()) {
                                            Log.i("qiniu", "Upload Success");
                                            scList.add(name);
                                            if (mList.size() == scList.size()) {
                                                Log.e("qiniu", "上传成功");
                                                Gson gson = new Gson();
                                                String imageList = gson.toJson(scList);
                                                Log.e("qiniu", imageList);
                                                postImg();
                                            }
                                        } else {
                                            Log.i("qiniu", "Upload Fail");
                                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                        }
//                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    }
                                }, null);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        Log.e("qiniu", "上传成功");
//                        Gson gson = new Gson();
//                        String imageList = gson.toJson(scList);
//                        Log.e("qiniu", imageList);
                    }
                });

    }

    private void postImg() {

        String images = "";

        for (int i = 0; i < scList.size(); i++) {
            if (i == scList.size() - 1) {
                images = images + scList.get(i);
            } else {
                images = images + scList.get(i) + ",";
            }
        }

        ViseHttp.POST("api/member/savePost")
                .addParam("title", " ")
                .addParam("content", etContent.getText().toString())
                .addParam("type", "news")
                .addParam("cover", scList.get(0))
                .addParam("images", images)
                .addParam("cid", cid)
                .addParam("uid", uid)
                .addParam("token", userToken)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        SavePostBean savePostBean = gson.fromJson(data, SavePostBean.class);
                        Log.e("qiniu", savePostBean.getData() + "::" + savePostBean.getMsg() + "::" + savePostBean.getCode() + "");
                        if (savePostBean.getCode() == 200) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            ToastUtil.showShort(SyFbDtActivity.this, "发布成功");
                            SyFbDtActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("222", "网络请求失败！");
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            mList.addAll(data.getStringArrayListExtra(ImageSelector.SELECT_RESULT));
            gridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SyFbDtActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SyFbDtActivity.class);
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#373c3d"));
        }
    }

}
