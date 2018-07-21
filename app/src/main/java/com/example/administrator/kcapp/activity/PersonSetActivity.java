package com.example.administrator.kcapp.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonSetActivity extends AppCompatActivity {

    @BindView(R.id.activity_person_set_btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_set);

        StatusBarUtils.setStatusBar(PersonSetActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

    }

    @OnClick({R.id.activity_person_set_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_person_set_btn:
                clearSp();
                break;
        }
    }

    /**
     * 清空本地配置文件并退出APP
     */
    private void clearSp() {

        SharedPreferences.Editor editor = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
        ToastUtil.showShort(PersonSetActivity.this, "已退出登录！");
        PersonSetActivity.this.finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PersonSetActivity.this.finish();
    }

}
