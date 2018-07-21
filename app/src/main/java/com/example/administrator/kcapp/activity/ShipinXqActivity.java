package com.example.administrator.kcapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.viewpagercard.CardItem;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ShipinXqActivity extends AppCompatActivity {

    @BindView(R.id.activity_shipin_vp)
    JZVideoPlayerStandard jzVideoPlayerStandard;
    @BindView(R.id.activity_shipin_xq_tv_title)
    TextView tvTitle;
    @BindView(R.id.activity_shipin_xq_tv_content)
    TextView tvContent;
    @BindView(R.id.activity_shipin_xq_tv_comment)
    TextView tvComment;
    @BindView(R.id.activity_shipin_xq_tv_praise)
    TextView tvPraise;
    @BindView(R.id.activity_shipin_xq_touxiang)
    ImageView ivTouxiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipin_xq);

        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(ShipinXqActivity.this);

        init();

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(CardItem event){
        Log.e("222", event.getVideo()+"");
        jzVideoPlayerStandard.setUp(Consts.QINIU_URL+event.getVideo(),
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                event.getTitle());
        jzVideoPlayerStandard.backButton.setVisibility(View.VISIBLE);
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShipinXqActivity.this.finish();
            }
        });
        Picasso.with(this).load(Consts.QINIU_URL+event.getImgUrl()).into(jzVideoPlayerStandard.thumbImageView);
        tvTitle.setText(event.getTitle());
        tvContent.setText(event.getContent());
        tvComment.setText(event.getComment_num());
        tvPraise.setText(event.getPraise_num());
        Picasso.with(this).load(Consts.QINIU_URL+event.getAvatar()).into(ivTouxiang);
    }

    private void init() {



    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
        ShipinXqActivity.this.finish();
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
        EventBus.getDefault().unregister(ShipinXqActivity.class);
    }
}
