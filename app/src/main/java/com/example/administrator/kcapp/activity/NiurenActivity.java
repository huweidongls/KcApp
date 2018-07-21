package com.example.administrator.kcapp.activity;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.player.PlayerConfig;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.eventbus.VideoEvent;
import com.example.administrator.kcapp.fangdy.DataUtil;
import com.example.administrator.kcapp.fangdy.DouYinAdapter;
import com.example.administrator.kcapp.fangdy.DouYinController;
import com.example.administrator.kcapp.fangdy.VideoBean;
import com.example.administrator.kcapp.gsonbean.PostSharpBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class NiurenActivity extends AppCompatActivity {

    private IjkVideoView mIjkVideoView;
    private DouYinController mDouYinController;
    @BindView(R.id.activity_niuren_vvp)
    VerticalViewPager mVerticalViewPager;
    @BindView(R.id.activity_niuren_rl_back)
    RelativeLayout rlBack;
    private DouYinAdapter mDouYinAdapter;
    private List<VideoBean> mVideoList;
    private List<View> mViews = new ArrayList<>();
    private List<PostSharpBean.DataBean> data;
    private int mCurrentPosition;
    private int mPlayingPosition;

    private String uid = "";
    private String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niuren);

        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(NiurenActivity.this);

        setStatusBarTransparent();
        init();

    }

    private void init() {

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = com.example.administrator.kcapp.utils.L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = com.example.administrator.kcapp.utils.L.decrypt(pref.getString("token", ""), Consts.LKEY);

        mIjkVideoView = new IjkVideoView(this);
        PlayerConfig config = new PlayerConfig.Builder().setLooping().build();
        mIjkVideoView.setPlayerConfig(config);
        mDouYinController = new DouYinController(NiurenActivity.this);
        mIjkVideoView.setVideoController(mDouYinController);
        mVideoList = DataUtil.getDouYinVideoList();
//        for (VideoBean item : mVideoList) {
//            View view = LayoutInflater.from(this).inflate(R.layout.item_douyin, null);
//            ImageView imageView = view.findViewById(R.id.thumb);
//            Glide.with(this).load(item.getThumb()).into(imageView);
//            mViews.add(view);
//        }
        if(data != null){
            for (PostSharpBean.DataBean item : data) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_douyin, null);
                ImageView imageView = view.findViewById(R.id.thumb);
                Glide.with(this).load(Consts.QINIU_URL+item.getCover()).into(imageView);
                mViews.add(view);
            }
        }

        mDouYinAdapter = new DouYinAdapter(mViews);
        mVerticalViewPager.setAdapter(mDouYinAdapter);

        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e(TAG, "mCurrentId == " + position + ", positionOffset == " + positionOffset +
//                        ", positionOffsetPixels == " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (mPlayingPosition == mCurrentPosition) return;
                if (state == VerticalViewPager.SCROLL_STATE_IDLE) {
                    mIjkVideoView.release();
                    ViewParent parent = mIjkVideoView.getParent();
                    if (parent != null && parent instanceof FrameLayout) {
                        ((FrameLayout) parent).removeView(mIjkVideoView);
                    }
                    startPlay();
                }
            }
        });
        //自动播放第一条
        startPlay();

    }

    private void startPlay() {
        View view = mViews.get(mCurrentPosition);
        FrameLayout frameLayout = view.findViewById(R.id.container);
        ImageView imageView = view.findViewById(R.id.thumb);
        mDouYinController.getThumb().setImageDrawable(imageView.getDrawable());
        mDouYinController.setCurrentPosition(mCurrentPosition);
        mDouYinController.setData(data.get(mCurrentPosition), uid, token);
        frameLayout.addView(mIjkVideoView);
        mIjkVideoView.setUrl(Consts.QINIU_URL+data.get(mCurrentPosition).getVideo());
        mIjkVideoView.setScreenScale(IjkVideoView.SCREEN_SCALE_CENTER_CROP);
        mIjkVideoView.start();
        mPlayingPosition = mCurrentPosition;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(List<PostSharpBean.DataBean> data){
        this.data = data;
    }

    /**
     * 把状态栏设成透明
     */
    private void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = NiurenActivity.this.getWindow().getDecorView();
//            decorView.setOnApplyWindowInsetsListener((v, insets) -> {
//                WindowInsets defaultInsets = v.onApplyWindowInsets(insets);
//                return defaultInsets.replaceSystemWindowInsets(
//                        defaultInsets.getSystemWindowInsetLeft(),
//                        0,
//                        defaultInsets.getSystemWindowInsetRight(),
//                        defaultInsets.getSystemWindowInsetBottom());
//            });
            decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                    WindowInsets defaultInsets = view.onApplyWindowInsets(windowInsets);
                    return defaultInsets.replaceSystemWindowInsets(
                            defaultInsets.getSystemWindowInsetLeft(),
                            0,
                            defaultInsets.getSystemWindowInsetRight(),
                            defaultInsets.getSystemWindowInsetBottom());
                }
            });
            ViewCompat.requestApplyInsets(decorView);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
    }

    @OnClick({R.id.activity_niuren_rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.activity_niuren_rl_back:
                NiurenActivity.this.finish();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIjkVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIjkVideoView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIjkVideoView.release();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(NiurenActivity.class);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NiurenActivity.this.finish();
    }
}
