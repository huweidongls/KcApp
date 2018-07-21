package com.example.administrator.kcapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.eventbus.VideoEvent;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoActivity extends AppCompatActivity {

    @BindView(R.id.activity_video_vp)
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @BindView(R.id.activity_video_iv_touxiang)
    ImageView ivTouxiang;
    @BindView(R.id.activity_video_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.activity_video_tv_c_time)
    TextView tvCtime;
    @BindView(R.id.activity_video_tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(VideoActivity.this);

        init();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(VideoEvent event){
        jzVideoPlayerStandard.setUp(Consts.QINIU_URL+event.getVideo(),
                JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,
                event.getTitle());
        jzVideoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VideoActivity.this.finish();
            }
        });
        Picasso.with(this).load(Consts.QINIU_URL+event.getCover()).into(jzVideoPlayerStandard.thumbImageView);
        Picasso.with(this).load(Consts.QINIU_URL+event.getAvatar()).into(ivTouxiang);
        tvNickname.setText(event.getNickname());
        tvCtime.setText("发布: "+ TimeUtil.LongFormatTime(event.getC_time()));
        tvContent.setText(event.getC_content());
    }

    private void init() {

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
        VideoActivity.this.finish();
        this.overridePendingTransition(0, R.anim.photoview_close);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(VideoActivity.class);
    }

}
