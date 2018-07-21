package com.example.administrator.kcapp.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.MainActivity;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivitySyMsgAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.entity.ActivitySyMsgEntity;
import com.example.administrator.kcapp.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SyMsgActivity extends AppCompatActivity {

    @BindView(R.id.activity_sy_msg_lv)
    ListView listView;
    @BindView(R.id.activity_sy_msg_back)
    RelativeLayout ivBack;

    private List<ActivitySyMsgEntity> data;
    private ActivitySyMsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_msg);

        StatusBarUtils.setStatusBar(SyMsgActivity.this, Color.parseColor("#f6f6f6"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        
        init();

    }

    private void init() {

        data = new ArrayList<>();
        data.add(new ActivitySyMsgEntity(R.drawable.touxiang, "关注通知", "3天前", "鉴于你在德嘉专升本店铺购买了音乐课，提示你10月24日上课。即可收到TA最新的评论和喜欢咯~！"));
        data.add(new ActivitySyMsgEntity(R.drawable.touxiang, "官方通知", "3天前", "鉴于你在德嘉专升本店铺购买了音乐课，提示你10月24日上课。即可收到TA最新的评论和喜欢咯~！"));
        data.add(new ActivitySyMsgEntity(R.drawable.touxiang, "官方通知", "3天前", "鉴于你在德嘉专升本店铺购买了音乐课，提示你10月24日上课。即可收到TA最新的评论和喜欢咯~！"));
        data.add(new ActivitySyMsgEntity(R.drawable.touxiang, "官方通知", "3天前", "鉴于你在德嘉专升本店铺购买了音乐课，提示你10月24日上课。即可收到TA最新的评论和喜欢咯~！"));
        adapter = new ActivitySyMsgAdapter(this, data);
        listView.setAdapter(adapter);

    }

    @OnClick({R.id.activity_sy_msg_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_sy_msg_back:
                SyMsgActivity.this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SyMsgActivity.this.finish();
    }

}
