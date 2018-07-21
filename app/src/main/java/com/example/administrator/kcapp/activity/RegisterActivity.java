package com.example.administrator.kcapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.gsonbean.LoginBean;
import com.example.administrator.kcapp.gsonbean.RegBean;
import com.example.administrator.kcapp.gsonbean.SendSmsBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.view.CountDownTimerButton;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.activity_register_rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.activity_register_et_phonenum)
    EditText etPhoneNum;
    @BindView(R.id.activity_register_et_yanzhengma)
    EditText etYzm;
    @BindView(R.id.activity_register_btn_login)
    Button btnLogin;
    @BindView(R.id.activity_register_btn_yanzhengma)
    CountDownTimerButton btnYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        StatusBarUtils.setStatusBarTransparent(RegisterActivity.this);
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        init();

    }

    private void init() {


    }

    @OnClick({R.id.activity_register_rl_back, R.id.activity_register_btn_login, R.id.activity_register_btn_yanzhengma})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.activity_register_rl_back:
                RegisterActivity.this.finish();
                break;
            case R.id.activity_register_btn_login:
                login(etPhoneNum.getText().toString(), etYzm.getText().toString());
                break;
            case R.id.activity_register_btn_yanzhengma:
                getYzm(etPhoneNum.getText().toString());
                break;
        }
    }

    /**
     * 注册并登录
     */
    private void login(final String tel, final String yzm) {

        if (TextUtils.isEmpty(etPhoneNum.getText().toString()) || TextUtils.isEmpty(etYzm.getText().toString())) {
            ToastUtil.showShort(RegisterActivity.this, "手机或验证码不能为空！");
        } else {
            ViseHttp.POST("api/login/reg")
                    .addParam("tel", tel)
                    .addParam("code", yzm)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            RegBean regBean = gson.fromJson(data, RegBean.class);
                            if (regBean.getCode() == 200) {
                                ViseHttp.POST("api/login/login")
                                        .addParam("tel", tel)
                                        .addParam("code", yzm)
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                Gson gson1 = new Gson();
                                                LoginBean loginBean = gson1.fromJson(data, LoginBean.class);
                                                if (loginBean.getCode() == 200) {
                                                    ToastUtil.showShort(RegisterActivity.this, "登录成功！");
                                                    Log.e("222", loginBean.getData().getUsername() + "::" + loginBean.getData().getNickname() + "::" + loginBean.getData().getToken());
                                                    saveMsg(loginBean);
                                                } else {
                                                    ToastUtil.showShort(RegisterActivity.this, loginBean.getMsg());
                                                }
                                            }

                                            @Override
                                            public void onFail(int errCode, String errMsg) {
                                                ToastUtil.showShort(RegisterActivity.this, "网络请求失败！");
                                            }
                                        });
                            } else {
                                ToastUtil.showShort(RegisterActivity.this, regBean.getMsg());
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            ToastUtil.showShort(RegisterActivity.this, "网络请求失败！");
                        }
                    });
        }

    }

    /**
     * 保存登录信息
     * @param loginBean
     */
    private void saveMsg(LoginBean loginBean) {
        SharedPreferences.Editor editor = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE).edit();
        editor.putString("uid", L.encrypt(loginBean.getData().getId()+"", Consts.LKEY));
        editor.putString("username", L.encrypt(loginBean.getData().getUsername()+"", Consts.LKEY));
        editor.putString("nickname", L.encrypt(loginBean.getData().getNickname()+"", Consts.LKEY));
        editor.putString("avatar", L.encrypt(loginBean.getData().getAvatar()+"", Consts.LKEY));
        editor.putString("sex", L.encrypt(loginBean.getData().getSex()+"", Consts.LKEY));
        editor.putString("birth_year", L.encrypt(loginBean.getData().getBirth_year()+"", Consts.LKEY));
        editor.putString("birth_month", L.encrypt(loginBean.getData().getBirth_month()+"", Consts.LKEY));
        editor.putString("birth_day", L.encrypt(loginBean.getData().getBirth_day()+"", Consts.LKEY));
        editor.putString("sign", L.encrypt(loginBean.getData().getSign()+"", Consts.LKEY));
        editor.putString("level_id", L.encrypt(loginBean.getData().getLevel_id()+"", Consts.LKEY));
        editor.putString("exp", L.encrypt(loginBean.getData().getExp()+"", Consts.LKEY));
        editor.putString("praise_num", L.encrypt(loginBean.getData().getPraise_num()+"", Consts.LKEY));
        editor.putString("teacher_num", L.encrypt(loginBean.getData().getTeacher_num()+"", Consts.LKEY));
        editor.putString("tel", L.encrypt(loginBean.getData().getTel()+"", Consts.LKEY));
        editor.putString("c_time", L.encrypt(loginBean.getData().getC_time()+"", Consts.LKEY));
        editor.putString("status", L.encrypt(loginBean.getData().getStatus()+"", Consts.LKEY));
        editor.putString("is_shop", L.encrypt(loginBean.getData().getIs_shop()+"", Consts.LKEY));
        editor.putString("token", L.encrypt(loginBean.getData().getToken()+"", Consts.LKEY));
        editor.commit();
        MyApp.getInstance().setLoginNewStatus(1);
        RegisterActivity.this.finish();
    }

    /**
     * 获取手机验证码
     *
     * @param tel
     */
    private void getYzm(String tel) {

        if (TextUtils.isEmpty(etPhoneNum.getText().toString())) {
            ToastUtil.showShort(RegisterActivity.this, "手机号不能为空！");
        } else {
            ViseHttp.POST("api/base/sendSms")
                    .addParam("tel", tel)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            SendSmsBean sendSmsBean = gson.fromJson(data, SendSmsBean.class);
                            if (sendSmsBean.getCode() == 200) {
                                ToastUtil.showShort(RegisterActivity.this, "获取验证码成功！");
                            } else {
                                ToastUtil.showShort(RegisterActivity.this, "获取验证码失败！");
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {
                            ToastUtil.showShort(RegisterActivity.this, "网络请求失败！");
                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RegisterActivity.this.finish();
    }

}
