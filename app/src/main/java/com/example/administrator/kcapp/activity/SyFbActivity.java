package com.example.administrator.kcapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.gsonbean.WeatherBean;
import com.example.administrator.kcapp.utils.AObserver;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.HttpUtil;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.Utils;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyFbActivity extends AppCompatActivity {

    @BindView(R.id.activity_sy_fb_back)
    RelativeLayout tlBack;
    @BindView(R.id.activity_sy_fb_niuren)
    LinearLayout ivNiuren;
    @BindView(R.id.activity_sy_fb_dt)
    LinearLayout ivDt;
    @BindView(R.id.activity_sy_fb_read)
    LinearLayout ivRead;
    @BindView(R.id.activity_sy_fb_day)
    TextView tvDay;
    @BindView(R.id.activity_sy_fb_week)
    TextView tvWeek;
    @BindView(R.id.activity_sy_fb_month)
    TextView tvMonth;
    @BindView(R.id.activity_sy_fb_weather)
    TextView tvWeather;

    private String uid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_fb);

        StatusBarUtils.setStatusBar(SyFbActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        init();

        initPermissionForCamera();

    }

    private void init() {

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);

        String url = "https://api.seniverse.com/v3/weather/now.json?key=x5ywn8nuphs54syw&location=ip&language=zh-Hans&unit=c";
        HttpUtil.getInstance().rxGet(url, new AObserver<String>() {
            @Override
            public void onNext(String value) {
                Gson gson = new Gson();
                WeatherBean weatherBean = gson.fromJson(value, WeatherBean.class);
                tvDay.setText(Utils.StringData(3));
                tvWeek.setText("星期"+Utils.StringData(4));
                tvMonth.setText(Utils.StringData(2)+"/"+Utils.StringData(1));
                tvWeather.setText(weatherBean.getResults().get(0).getLocation().getName()+": "+weatherBean.getResults().get(0).getNow().getText()+weatherBean.getResults().get(0).getNow().getTemperature()+"℃");
            }
        });

    }

    /**
     * Android 6.0以上版本，需求添加运行时权限申请；否则，可能程序崩溃
     */
    private static final int REQUEST_CODE_PERMISSION = 0x110;
    private void initPermissionForCamera() {
        int flag = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int flag1 = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int flag2 = ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        if (PackageManager.PERMISSION_GRANTED != flag||PackageManager.PERMISSION_GRANTED != flag1||PackageManager.PERMISSION_GRANTED != flag2) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
        }
    }

    @OnClick({R.id.activity_sy_fb_back, R.id.activity_sy_fb_niuren, R.id.activity_sy_fb_dt, R.id.activity_sy_fb_read})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_sy_fb_back:
                SyFbActivity.this.finish();
                overridePendingTransition(0, R.anim.sy_fb_close);
                break;
            case R.id.activity_sy_fb_niuren:
                if(TextUtils.isEmpty(uid)||uid.equals("")){
                    ToastUtil.showShort(SyFbActivity.this, "请登录!");
                    intent.setClass(SyFbActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SyFbActivity.this.finish();
                }else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            &&ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            &&ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                        intent.setClass(SyFbActivity.this, SyFbNiurenActivity.class);
                        startActivity(intent);
                        SyFbActivity.this.finish();
                    } else {
                        //申请权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
                                , Manifest.permission.RECORD_AUDIO}, 1);
                        ToastUtil.showShort(SyFbActivity.this, "拍照或SD卡权限被禁用，请在权限管理修改");
                    }
                }
                break;
            case R.id.activity_sy_fb_dt:
                if(TextUtils.isEmpty(uid)||uid.equals("")){
                    ToastUtil.showShort(SyFbActivity.this, "请登录!");
                    intent.setClass(SyFbActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SyFbActivity.this.finish();
                }else {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            &&ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        intent.setClass(SyFbActivity.this, SyFbDtActivity.class);
                        startActivity(intent);
                        SyFbActivity.this.finish();
                    } else {
                        //申请权限
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        ToastUtil.showShort(SyFbActivity.this, "拍照或SD卡权限被禁用，请在权限管理修改");
                    }
                }
                break;
            case R.id.activity_sy_fb_read:
                if(TextUtils.isEmpty(uid)||uid.equals("")){
                    ToastUtil.showShort(SyFbActivity.this, "请登录!");
                    intent.setClass(SyFbActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SyFbActivity.this.finish();
                }else {
                    intent.setClass(SyFbActivity.this, BookActivity.class);
                    startActivity(intent);
                    SyFbActivity.this.finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SyFbActivity.this.finish();
        overridePendingTransition(0, R.anim.sy_fb_close);
    }

}
