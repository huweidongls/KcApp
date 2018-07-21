package com.example.administrator.kcapp.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivityFbDtZtAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.entity.ActivityFbDtZtEntity;
import com.example.administrator.kcapp.eventbus.FbDtSelectZtEvent;
import com.example.administrator.kcapp.gsonbean.GetClassBean;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FbSelectZtActivity extends AppCompatActivity {

    @BindView(R.id.activity_sy_fb_dt_zt_lv1)
    ListView listView;
    @BindView(R.id.activity_sy_fb_dt_zt_tv1)
    TextView tv1;
    @BindView(R.id.activity_sy_fb_dt_zt_tv2)
    TextView tv2;
    @BindView(R.id.activity_sy_fb_dt_zt_tv3)
    TextView tv3;
    @BindView(R.id.activity_sy_fb_dt_zt_tv4)
    TextView tv4;
    @BindView(R.id.activity_sy_fb_dt_zt_tv5)
    TextView tv5;
    @BindView(R.id.activity_sy_fb_dt_zt_save)
    TextView save;
    @BindView(R.id.activity_sy_fb_dt_zt_back)
    RelativeLayout back;

    private List<ActivityFbDtZtEntity> data;
    private ActivityFbDtZtAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_select_zt);

        StatusBarUtils.setStatusBar(FbSelectZtActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        initData();

    }

    private void initData() {

        ViseHttp.POST("api/Classify/getClass")
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        GetClassBean getClassBean = gson.fromJson(data, GetClassBean.class);
                        if(getClassBean.getData().size()>0){
                            init(getClassBean.getData());
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private void init(final List<GetClassBean.DataBean> data) {

        adapter = new ActivityFbDtZtAdapter(FbSelectZtActivity.this, data);
        listView.setAdapter(adapter);

        adapter.setOnSelectListener(new ActivityFbDtZtAdapter.OnSelectListener() {
            @Override
            public void onClick(int i, boolean isSelect) {
                if(isSelect){
                    if(tv1.getText().equals("")){
                        tv1.setText(data.get(i).getName());
                        tv1.setTag(data.get(i).getId());
                    }else if(tv2.getText().equals("")){
                        tv2.setText(data.get(i).getName());
                        tv2.setTag(data.get(i).getId());
                    }else if(tv3.getText().equals("")){
                        tv3.setText(data.get(i).getName());
                        tv3.setTag(data.get(i).getId());
                    }else if(tv4.getText().equals("")){
                        tv4.setText(data.get(i).getName());
                        tv4.setTag(data.get(i).getId());
                    }else if(tv5.getText().equals("")){
                        tv5.setText(data.get(i).getName());
                        tv5.setTag(data.get(i).getId());
                    }else {
                        ToastUtil.showShort(FbSelectZtActivity.this, "最多选择五个分类");
                    }
                }
                if(!isSelect){
                    if(tv1.getText().equals(data.get(i).getName())){
                        tv1.setText(tv2.getText());
                        tv1.setTag(tv2.getTag());
                        tv2.setText(tv3.getText());
                        tv2.setTag(tv3.getTag());
                        tv3.setText(tv4.getText());
                        tv3.setTag(tv4.getTag());
                        tv4.setText(tv5.getText());
                        tv4.setTag(tv5.getTag());
                        tv5.setText("");
                    }else if(tv2.getText().equals(data.get(i).getName())){
                        tv2.setText(tv3.getText());
                        tv2.setTag(tv3.getTag());
                        tv3.setText(tv4.getText());
                        tv3.setTag(tv4.getTag());
                        tv4.setText(tv5.getText());
                        tv4.setTag(tv5.getTag());
                        tv5.setText("");
                    }else if(tv3.getText().equals(data.get(i).getName())){
                        tv3.setText(tv4.getText());
                        tv3.setTag(tv4.getTag());
                        tv4.setText(tv5.getText());
                        tv4.setTag(tv5.getTag());
                        tv5.setText("");
                    }else if(tv4.getText().equals(data.get(i).getName())){
                        tv4.setText(tv5.getText());
                        tv4.setTag(tv5.getTag());
                        tv5.setText("");
                    }else if(tv5.getText().equals(data.get(i).getName())){
                        tv5.setText("");
                    }
                }
            }
        });

    }

    @OnClick({R.id.activity_sy_fb_dt_zt_save, R.id.activity_sy_fb_dt_zt_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_sy_fb_dt_zt_save:
                List<ActivityFbDtZtEntity> l = new ArrayList<>();
                if(!TextUtils.isEmpty(tv1.getText().toString())){
                    l.add(new ActivityFbDtZtEntity(tv1.getTag()+"", tv1.getText().toString()));
                }
                if(!TextUtils.isEmpty(tv2.getText().toString())){
                    l.add(new ActivityFbDtZtEntity(tv2.getTag()+"", tv2.getText().toString()));
                }
                if(!TextUtils.isEmpty(tv3.getText().toString())){
                    l.add(new ActivityFbDtZtEntity(tv3.getTag()+"", tv3.getText().toString()));
                }
                if(!TextUtils.isEmpty(tv4.getText().toString())){
                    l.add(new ActivityFbDtZtEntity(tv4.getTag()+"", tv4.getText().toString()));
                }
                if(!TextUtils.isEmpty(tv5.getText().toString())){
                    l.add(new ActivityFbDtZtEntity(tv5.getTag()+"", tv5.getText().toString()));
                }
                EventBus.getDefault().post(new FbDtSelectZtEvent(l));
                FbSelectZtActivity.this.finish();
                break;
            case R.id.activity_sy_fb_dt_zt_back:
                FbSelectZtActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FbSelectZtActivity.this.finish();
    }

}
