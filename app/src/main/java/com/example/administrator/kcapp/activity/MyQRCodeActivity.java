package com.example.administrator.kcapp.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyQRCodeActivity extends AppCompatActivity {

    @BindView(R.id.activity_my_code_back)
    RelativeLayout ivBack;
    @BindView(R.id.activity_my_code_iv_img)
    ImageView imageView;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);

        StatusBarUtils.setStatusBar(MyQRCodeActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        init();

    }

    private void init() {

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);

        if (TextUtils.isEmpty(uid)) {
            return;
        }
        Bitmap mBitmap = CodeUtils.createImage(uid, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        imageView.setImageBitmap(mBitmap);

    }

    @OnClick({R.id.activity_my_code_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_my_code_back:
                MyQRCodeActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyQRCodeActivity.this.finish();
    }
}
