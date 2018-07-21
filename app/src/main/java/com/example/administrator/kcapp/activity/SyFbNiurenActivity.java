package com.example.administrator.kcapp.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivityFbDtAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.eventbus.FbDtSelectZtEvent;
import com.example.administrator.kcapp.gsonbean.SavePostBean;
import com.example.administrator.kcapp.gsonbean.UploadTokenBean;
import com.example.administrator.kcapp.utils.ACache;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.Utils;
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
import com.sh.shvideolibrary.VideoInputDialog;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyFbNiurenActivity extends AppCompatActivity implements VideoInputDialog.VideoCall {

    @BindView(R.id.activity_sy_fb_niuren_cover)
    ImageView ivCover;
    @BindView(R.id.activity_sy_fb_niuren_et_content)
    EditText etContent;
    @BindView(R.id.activity_sy_fb_niuren_sc)
    TextView tvSc;
    @BindView(R.id.activity_sy_fb_niuren_tv_xuanzezhuti)
    TextView tvSelectZt;
    @BindView(R.id.activity_sy_fb_niuren_rv_zt)
    RecyclerView recyclerView;
    @BindView(R.id.activity_sy_fb_niuren_tv_text_num)
    TextView tvTextNum;
    @BindView(R.id.activity_sy_fb_niuren_back)
    RelativeLayout rlBack;

    private Dialog loadingDialog;

    private String path = "";
    private String imgPath = "";

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
    private ActivityFbDtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_fb_niuren);

        StatusBarUtils.setStatusBar(SyFbNiurenActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(SyFbNiurenActivity.this);

        init();

    }

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

    TextWatcher textWatcher = new TextWatcher() {

        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvTextNum.setText(temp.length() + "/500");
            if (temp.length() > 500) {
                ToastUtil.showShort(SyFbNiurenActivity.this, "您输入的字数已经超过了限制");
            }
        }
    };

    private void init() {

        etContent.addTextChangedListener(textWatcher);

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        userToken = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        final ACache aCache = ACache.get(SyFbNiurenActivity.this);
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

        //显示视频录制控件
        VideoInputDialog.show(getSupportFragmentManager(), SyFbNiurenActivity.this, VideoInputDialog.Q720, SyFbNiurenActivity.this);

    }

    /**
     * 小视频录制回调
     *
     * @param path
     */
    @Override
    public void videoPathCall(String path) {
        this.path = path;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Video.Thumbnails.MINI_KIND);
//        Bitmap bitmap = Utils.createBitmapFromVideoPath(path, 690, 388);
        ivCover.setImageBitmap(bitmap);
        imgPath = Utils.saveImage(bitmap).getPath();
    }

    /**
     * 判断是否上传到七牛
     *
     * @param path
     */
    private void saveQiniu(String path, String imgPath) {

        loadingDialog = WeiboDialogUtils.createLoadingDialog(SyFbNiurenActivity.this, "发布中...");

        Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();
        UploadManager uploadManager = new UploadManager(config);
        String data = path;
        final String Key = uid + System.currentTimeMillis() + (int) (Math.random() * 100000) + "";

        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        Log.i("qiniu", "Upload Success" + percent);
                    }
                }, null);

        uploadManager.put(imgPath, Key + ".jpg", token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);

        uploadManager.put(data, Key + ".mp4", token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Log.i("qiniu", "Upload Success");
                            savePost(Key);
                        } else {
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, uploadOptions);

    }

    /**
     * 发布动态
     */
    private void savePost(String key) {

        File file = new File(path);
        MediaPlayer meidaPlayer = new MediaPlayer();
        try {
            meidaPlayer.setDataSource(file.getPath());
            meidaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = meidaPlayer.getDuration() / 1000;//获得了视频的时长（以毫秒为单位）

        ViseHttp.POST("api/member/savePost")
                .addParam("title", " ")
                .addParam("content", etContent.getText().toString())
                .addParam("type", "video")
                .addParam("video", key + ".mp4")
                .addParam("video_time_length", time + "")
                .addParam("cover", key + ".jpg")
                .addParam("cid", cid)
                .addParam("uid", uid)
                .addParam("token", userToken)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        SavePostBean savePostBean = gson.fromJson(data, SavePostBean.class);
                        if (savePostBean.getCode() == 200) {
                            WeiboDialogUtils.closeDialog(loadingDialog);
                            ToastUtil.showShort(SyFbNiurenActivity.this, "发布成功");
                            SyFbNiurenActivity.this.finish();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("222", "网络请求失败！");
                    }
                });
    }

    @OnClick({R.id.activity_sy_fb_niuren_sc, R.id.activity_sy_fb_niuren_tv_xuanzezhuti, R.id.activity_sy_fb_niuren_back})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_sy_fb_niuren_sc:
                saveQiniu(path, imgPath);
                break;
            case R.id.activity_sy_fb_niuren_tv_xuanzezhuti:
                intent.setClass(SyFbNiurenActivity.this, FbSelectZtActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_sy_fb_niuren_back:
                SyFbNiurenActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SyFbNiurenActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(SyFbNiurenActivity.class);
    }

}
