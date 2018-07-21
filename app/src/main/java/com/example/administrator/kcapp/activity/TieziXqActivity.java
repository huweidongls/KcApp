package com.example.administrator.kcapp.activity;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.ActivityTieziXqAdapter;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.eventbus.FragmentOtherEvent;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;
import com.example.administrator.kcapp.gsonbean.MemberPraiseBean;
import com.example.administrator.kcapp.gsonbean.PostCommentBean;
import com.example.administrator.kcapp.gsonbean.PostContentBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.Like;
import com.example.administrator.kcapp.utils.Praise;
import com.example.administrator.kcapp.utils.SoftKeyBoardListener;
import com.example.administrator.kcapp.utils.StatusBarUtils;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.view.header.ActivityTieziXqHeader;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TieziXqActivity extends AppCompatActivity {

    @BindView(R.id.activity_tiezi_xq_lv)
    ListView listView;
    @BindView(R.id.activity_tiezi_xq_tv_ispl)
    TextView tvIspl;
    @BindView(R.id.activity_tiezi_xq_back)
    RelativeLayout ivBack;
    @BindView(R.id.activity_tiezi_xq_ll_like)
    LinearLayout llLike;
    @BindView(R.id.activity_tiezi_xq_iv_like)
    ImageView ivLike;
    @BindView(R.id.activity_tiezi_xq_tv_like)
    TextView tvLike;
    @BindView(R.id.activity_tiezi_xq_ll_pl)
    LinearLayout llPl;
    @BindView(R.id.activity_tiezi_xq_tv_pl)
    TextView tvPl;
    @BindView(R.id.activity_tiezi_xq_ll_zan)
    LinearLayout llZan;
    @BindView(R.id.activity_tiezi_xq_iv_zan)
    ImageView ivZan;
    @BindView(R.id.activity_tiezi_xq_tv_zan)
    TextView tvZan;

    private ActivityTieziXqAdapter adapter;

    private ActivityTieziXqHeader tieziXqHeader;
    private String pid = "";

    private int itemPosition;

    private String uid = "";
    private String token = "";
    private PostContentBean postContentBean;

    private List<ClassGetNewPostBean.DataBean> data;
    private int i;

    /**
     * popupwindow相关
     */
    private Button btn_submit;
    private ImageView btn_back;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiezi_xq);

        StatusBarUtils.setStatusBar(TieziXqActivity.this, Color.parseColor("#39374D"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);
        EventBus.getDefault().register(TieziXqActivity.this);

        tieziXqHeader = new ActivityTieziXqHeader(TieziXqActivity.this, null, new OnItemPictureClickListener() {
            @Override
            public void onItemPictureClick(int itemPostion, int i, String url, List<String> urlList, ImageView imageView) {
                itemPosition = itemPostion;
                Intent intent = new Intent(TieziXqActivity.this, ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, itemPosition);
                intent.putExtra(Consts.START_IAMGE_POSITION, i);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                startActivity(intent);
                overridePendingTransition(R.anim.photoview_open, 0);
            }
        });

        initData();

    }

    private void initData() {

        //注册软键盘的监听
        SoftKeyBoardListener.setListener(TieziXqActivity.this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void keyBoardHide(int height) {
//                        Toast.makeText(TieziXqActivity.this,
//                                "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

        SharedPreferences pref = getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        if (TextUtils.isEmpty(uid) || uid.equals("")) {
            ViseHttp.POST("api/post/content")
                    .addParam("id", pid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            postContentBean = gson.fromJson(data, PostContentBean.class);
                            if (postContentBean.getData() != null) {
                                tieziXqHeader.setHeader(postContentBean.getData());
                                Picasso.with(TieziXqActivity.this).load(postContentBean.getData().getIs_like() == 0 ? R.drawable.heart2 : R.drawable.heart3).into(ivLike);
                                Picasso.with(TieziXqActivity.this).load(postContentBean.getData().getIs_praise() == 0 ? R.drawable.zan2 : R.drawable.zan3).into(ivZan);
                                tvLike.setText(postContentBean.getData().getLike_num() + "");
                                tvPl.setText(postContentBean.getData().getLook_num() + "");
                                tvZan.setText(postContentBean.getData().getPraise_num() + "");
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        } else {
            ViseHttp.POST("api/post/content")
                    .addParam("uid", uid)
                    .addParam("token", token)
                    .addParam("id", pid)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            Gson gson = new Gson();
                            postContentBean = gson.fromJson(data, PostContentBean.class);
                            if (postContentBean.getData() != null) {
                                tieziXqHeader.setHeader(postContentBean.getData());
                                Picasso.with(TieziXqActivity.this).load(postContentBean.getData().getIs_like() == 0 ? R.drawable.heart2 : R.drawable.heart3).into(ivLike);
                                Picasso.with(TieziXqActivity.this).load(postContentBean.getData().getIs_praise() == 0 ? R.drawable.zan2 : R.drawable.zan3).into(ivZan);
                                tvLike.setText(postContentBean.getData().getLike_num() + "");
                                tvPl.setText(postContentBean.getData().getLook_num() + "");
                                tvZan.setText(postContentBean.getData().getPraise_num() + "");
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });
        }

        ViseHttp.POST("api/post/comment")
                .addParam("pid", pid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        PostCommentBean postCommentBean = gson.fromJson(data, PostCommentBean.class);
                        initListView(postCommentBean.getData());
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(TieziXqActivity.this, "请登录!");
                    intent.setClass(TieziXqActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (postContentBean.getData().getIs_praise() == 0) {
                        Picasso.with(TieziXqActivity.this).load(R.drawable.zan1).into(ivZan);
                        int zanNum = Integer.valueOf(tvZan.getText().toString()) + 1;
                        tvZan.setText(zanNum + "");
                        postContentBean.getData().setIs_praise(1);
                        if (data != null) {
                            data.get(i).setIs_praise(1);
                            data.get(i).setPraise_num(zanNum);
                        }
                        Praise.praise(TieziXqActivity.this, uid, token, pid);
                    } else {
                        Picasso.with(TieziXqActivity.this).load(R.drawable.zan).into(ivZan);
                        int zanNum = Integer.valueOf(tvZan.getText().toString()) - 1;
                        tvZan.setText(zanNum + "");
                        postContentBean.getData().setIs_praise(0);
                        if (data != null) {
                            data.get(i).setIs_praise(0);
                            data.get(i).setPraise_num(zanNum);
                        }
                        Praise.cancelPraise(TieziXqActivity.this, uid, token, pid);
                    }
                }
            }
        });

        llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(TieziXqActivity.this, "请登录!");
                    intent.setClass(TieziXqActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (postContentBean.getData().getIs_like() == 0) {
                        Picasso.with(TieziXqActivity.this).load(R.drawable.heart1).into(ivLike);
                        int likeNum = Integer.valueOf(tvLike.getText().toString()) + 1;
                        tvLike.setText(likeNum + "");
                        postContentBean.getData().setIs_like(1);
                        if (data != null) {
                            data.get(i).setIs_like(1);
                            data.get(i).setLike_num(likeNum);
                        }
                        Like.like(TieziXqActivity.this, uid, token, pid);
                    } else {
                        Picasso.with(TieziXqActivity.this).load(R.drawable.heart).into(ivLike);
                        int likeNum = Integer.valueOf(tvLike.getText().toString()) - 1;
                        tvLike.setText(likeNum + "");
                        postContentBean.getData().setIs_like(0);
                        if (data != null) {
                            data.get(i).setIs_like(0);
                            data.get(i).setLike_num(likeNum);
                        }
                        Like.cancelLike(TieziXqActivity.this, uid, token, pid);
                    }
                }
            }
        });

        llPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupCommnet(1, 1);
            }
        });

    }

    private void initListView(List<PostCommentBean.DataBean> data) {

        if (data.size() == 0 || data == null) {
            tvIspl.setVisibility(View.VISIBLE);
        } else {
            tvIspl.setVisibility(View.GONE);
        }

        adapter = new ActivityTieziXqAdapter(TieziXqActivity.this, data);
        listView.addHeaderView(tieziXqHeader);
        listView.setAdapter(adapter);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(TieziXqEvent event) {
        pid = event.getPid();
        if (data == null && event.getData() != null) {
            data = event.getData();
            i = event.getI();
            Log.e("222", data.size() + "::" + i);
        }
    }

    @OnClick({R.id.activity_tiezi_xq_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_tiezi_xq_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().postSticky(new FragmentOtherEvent(data));
        TieziXqActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeAllStickyEvents();
        EventBus.getDefault().unregister(TieziXqActivity.class);
    }

    /**
     * show soft input
     */
    private void popupInputMethodWindow() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }.start();
        //
    }

    /**
     * show comment popupwindow（弹出评论的popupWindow）
     */
    private void showPopupCommnet(final int pid, final int type) {// pe表示是评论还是举报1.代表评论。2.代表举报
        View view = LayoutInflater.from(TieziXqActivity.this).inflate(
                R.layout.comment_popupwindow, null);
        final EditText inputComment = view
                .findViewById(R.id.comment);

        btn_submit = view.findViewById(R.id.submit_comment);
        btn_back = view.findViewById(R.id.btn_back);
        if (type == 1) {
            btn_submit.setText("提交");
            inputComment.setHint("请输入你的评论...");
        }
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.bg_activity_fb_dt_zt));

        // 设置弹出窗体需要软键盘
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        ColorDrawable cd = new ColorDrawable(0x000000);
//        popupWindow.setBackgroundDrawable(cd);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.4f;
        getWindow().setAttributes(params);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.update();
        popupInputMethodWindow();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_submit.setClickable(false);
                String comment1 = inputComment.getText().toString().trim();
                if (comment1.length() <= 0) {
                    if (type == 1) {
                        Toast.makeText(TieziXqActivity.this, "评论内容不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TieziXqActivity.this, "有非法内容",
                                Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                String comment2 = null;
                try {
                    comment2 = URLEncoder.encode(comment1, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String finalComment = comment2;
                popupWindow.dismiss();

                Toast.makeText(TieziXqActivity.this, finalComment,
                        Toast.LENGTH_SHORT).show();
                // 提交评论
                // submitComment(finalComment, pid);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

}
