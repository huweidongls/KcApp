package com.example.administrator.kcapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.activity_password_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_password_btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

    }

    @OnClick({R.id.activity_password_rl_back, R.id.activity_password_btn_login})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.activity_password_rl_back:
                SetPasswordActivity.this.finish();
                break;
            case R.id.activity_password_btn_login:
                SetPasswordActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SetPasswordActivity.this.finish();
    }

}
