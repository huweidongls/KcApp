package com.example.administrator.kcapp.fangdy;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dueeeke.videoplayer.controller.BaseVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.dueeeke.videoplayer.util.L;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.adapter.PopCommentRvAdapter;
import com.example.administrator.kcapp.gsonbean.PostCommentBean;
import com.example.administrator.kcapp.gsonbean.PostSharpBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.Like;
import com.example.administrator.kcapp.utils.Praise;
import com.example.administrator.kcapp.utils.SoftKeyBoardListener;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 抖音
 * Created by xinyu on 2018/1/6.
 */

public class DouYinController extends BaseVideoController {

    private List<PostSharpBean.DataBean> mData;
    private Context context;
    private ImageView thumb;
    private int currentPosition;
    private ImageView ivTouxiang;
    private TextView tvNickname;
    private TextView tvCreateTime;
    private TextView tvContent;
    private LinearLayout llLike;
    private ImageView ivLike;
    private TextView tvLikeNum;
    private LinearLayout llPl;
    private TextView tvPlNum;
    private LinearLayout llZan;
    private ImageView ivZan;
    private TextView tvZanNum;

    private PopupWindow popupWindow;

    /**
     * popupwindow相关
     */
    private Button btn_submit;
    private ImageView btn_back;
    private PopupWindow popupWindowhf;

    public DouYinController(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DouYinController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_douyin_controller;
    }

    @Override
    protected void initView() {
        super.initView();
        thumb = controllerView.findViewById(R.id.iv_thumb);
        ivTouxiang = controllerView.findViewById(R.id.activity_niuren_iv_touxiang);
        tvNickname = controllerView.findViewById(R.id.activity_niuren_tv_nickname);
        tvCreateTime = controllerView.findViewById(R.id.activity_niuren_tv_c_time);
        tvContent = controllerView.findViewById(R.id.activity_niuren_tv_content);
        llLike = controllerView.findViewById(R.id.activity_niuren_ll_like);
        ivLike = controllerView.findViewById(R.id.activity_niuren_iv_like);
        tvLikeNum = controllerView.findViewById(R.id.activity_niuren_tv_like_num);
        llPl = controllerView.findViewById(R.id.activity_niuren_ll_pl);
        tvPlNum = controllerView.findViewById(R.id.activity_niuren_tv_pl_num);
        llZan = controllerView.findViewById(R.id.activity_niuren_ll_zan);
        ivZan = controllerView.findViewById(R.id.activity_niuren_iv_zan);
        tvZanNum = controllerView.findViewById(R.id.activity_niuren_tv_zan_num);
    }

    public void setData(final PostSharpBean.DataBean data, final String uid, final String token) {
        final Intent intent = new Intent();
        Glide.with(context).load(R.drawable.heart2).into(ivLike);
        Glide.with(context).load(R.drawable.niuzan).into(ivZan);
        Glide.with(context).load(Consts.QINIU_URL + data.getAvatar()).into(ivTouxiang);
        tvNickname.setText(data.getNickname());
        tvCreateTime.setText("发布: " + TimeUtil.LongFormatTime(data.getC_time() + ""));
        tvContent.setText(data.getContent());
        tvLikeNum.setText(data.getLike_num() + "");
        tvPlNum.setText(data.getComment_num() + "");
        tvZanNum.setText(data.getPraise_num() + "");

        llLike.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    Glide.with(context).load(R.drawable.heart3).into(ivLike);
                    int likeNum = Integer.valueOf(tvLikeNum.getText().toString());
                    tvLikeNum.setText(likeNum + 1 + "");
                    Like.like(context, uid, token, data.getId() + "");
                }
            }
        });
        llZan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    Glide.with(context).load(R.drawable.niuzan1).into(ivZan);
                    int zanNum = Integer.valueOf(tvZanNum.getText().toString());
                    tvZanNum.setText(zanNum + 1 + "");
                    Praise.praise(context, uid, token, data.getId() + "");
                }
            }
        });
        llPl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopComment(data.getAvatar(), data.getId()+"");
            }
        });
        //注册软键盘的监听
        SoftKeyBoardListener.setListener((Activity) context,
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
                        if (popupWindowhf != null) {
                            popupWindowhf.dismiss();
                        }
                    }
                });

    }

    private void showPopComment(String touUrl, String pid){
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_niuren_comment, null);
        final RecyclerView recyclerView = view.findViewById(R.id.pop_niuren_comment_rv_pl);
        RelativeLayout rlFbpl = view.findViewById(R.id.pop_niuren_comment_rl_fbpl);
        ImageView ivTouxiang = view.findViewById(R.id.pop_niuren_comment_iv_touxiang);
        ImageView ivBack = view.findViewById(R.id.pop_niuren_comment_iv_back);
        //设置头像
        Glide.with(context).load(Consts.QINIU_URL+touUrl).into(ivTouxiang);
        //加载评论列表
        final LinearLayoutManager manager = new LinearLayoutManager(context);
        ViseHttp.POST("api/post/comment")
                .addParam("pid", pid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
                        PostCommentBean postCommentBean = gson.fromJson(data, PostCommentBean.class);
                        PopCommentRvAdapter adapter = new PopCommentRvAdapter(postCommentBean.getData());
                        manager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindow.update();

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        rlFbpl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupCommnet(1, 1);
            }
        });

    }

    @Override
    public void setPlayState(int playState) {
        super.setPlayState(playState);

        switch (playState) {
            case IjkVideoView.STATE_IDLE:
                L.e("STATE_IDLE");
                thumb.setVisibility(VISIBLE);
                break;
            case IjkVideoView.STATE_PLAYING:
                L.e("STATE_PLAYING");
                thumb.setVisibility(GONE);
                break;
            case IjkVideoView.STATE_PREPARED:
                L.e("STATE_PREPARED");
                break;
        }
    }

    public ImageView getThumb() {
        return thumb;
    }

    public void setCurrentPosition(int i) {
        this.currentPosition = i;
    }

    /**
     * show soft input
     */
    private void popupInputMethodWindow() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }.start();
        //
    }

    /**
     * show comment popupwindow（弹出发表评论的popupWindow）
     */
    private void showPopupCommnet(final int pid, final int type) {// pe表示是评论还是举报1.代表评论。2.代表举报
        View view = LayoutInflater.from(context).inflate(
                R.layout.comment_popupwindow, null);
        final EditText inputComment = view
                .findViewById(R.id.comment);

        btn_submit = view.findViewById(R.id.submit_comment);
        btn_back = view.findViewById(R.id.btn_back);
        if (type == 1) {
            btn_submit.setText("提交");
            inputComment.setHint("请输入你的评论...");
        }
        popupWindowhf = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        popupWindowhf.setTouchable(true);
        popupWindowhf.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindowhf.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindowhf.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(
//                R.drawable.bg_activity_fb_dt_zt));

        // 设置弹出窗体需要软键盘
        popupWindowhf.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        // 再设置模式，和Activity的一样，覆盖，调整大小。
        popupWindowhf.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupWindowhf.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//        ColorDrawable cd = new ColorDrawable(0x000000);
//        popupWindow.setBackgroundDrawable(cd);
//        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.4f;
//        getWindow().setAttributes(params);
        // 设置popWindow的显示和消失动画
        popupWindowhf.setAnimationStyle(R.style.mypopwindow_anim_style);
        popupWindowhf.update();
        popupInputMethodWindow();
        popupWindowhf.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
//                WindowManager.LayoutParams params = getWindow().getAttributes();
//                params.alpha = 1f;
//                getWindow().setAttributes(params);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // btn_submit.setClickable(false);
                String comment1 = inputComment.getText().toString().trim();
                if (comment1.length() <= 0) {
                    if (type == 1) {
                        Toast.makeText(context, "评论内容不能为空",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "有非法内容",
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
                popupWindowhf.dismiss();

                Toast.makeText(context, finalComment,
                        Toast.LENGTH_SHORT).show();
                // 提交评论
                // submitComment(finalComment, pid);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowhf.dismiss();
            }
        });
    }

}
