package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.entity.FragmentSyOtherEntity;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.ClassGetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.ClassGetNewPostBean;
import com.example.administrator.kcapp.gsonbean.GetHotCommentBean;
import com.example.administrator.kcapp.gsonbean.MemberPraiseBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.Like;
import com.example.administrator.kcapp.utils.Praise;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.Utils;
import com.example.administrator.kcapp.view.ninegridview.NineGridTestLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentSyOtherAdapter extends BaseAdapter {

    private Context context;
    private List<ClassGetNewPostBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;
    private FragmentSyOtherListener listener;

    private OnItemPictureClickListener listener1;

    private String uid;
    private String token;
    private Intent intent;

    public FragmentSyOtherAdapter(Context context, List<ClassGetNewPostBean.DataBean> data) {
        intent = new Intent();
        SharedPreferences pref = context.getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setPictureClickListener(OnItemPictureClickListener listener1){
        this.listener1 = listener1;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_fragment_sy_other, null);
            holder.llPinglun = view.findViewById(R.id.fragment_sy_other_lv_p);
            holder.ivTouxiang = view.findViewById(R.id.fragment_sy_other_lv_touxiang);
            holder.tvNickName = view.findViewById(R.id.fragment_sy_other_lv_nickname);
            holder.tvCreatTime = view.findViewById(R.id.fragment_sy_other_lv_creattime);
            holder.tvPingLun = view.findViewById(R.id.fragment_sy_other_lv_pinglun);
            holder.ivCover = view.findViewById(R.id.fragment_sy_other_lv_iv);
            holder.tvTitle = view.findViewById(R.id.fragment_sy_other_lv_title);
            holder.rv = view.findViewById(R.id.fragment_sy_other_lv_rv_biaoqian);
            holder.llContent = view.findViewById(R.id.fragment_sy_other_lv_content);
            holder.tvZanNum = view.findViewById(R.id.fragment_sy_other_lv_tv_zan_num);
            holder.tvPlNum = view.findViewById(R.id.fragment_sy_other_lv_tv_pl_num);
            holder.tvLikeNum = view.findViewById(R.id.fragment_sy_other_lv_tv_like_num);
            holder.nineGridTestLayout = view.findViewById(R.id.fragment_sy_other_lv_nineTestlayout);
            holder.video = view.findViewById(R.id.fragment_sy_other_lv_content_video);
            holder.ivLikeImg = view.findViewById(R.id.fragment_sy_other_lv_tv_like_img);
            holder.ivZanImg = view.findViewById(R.id.fragment_sy_other_lv_tv_zan_img);
            holder.llLike = view.findViewById(R.id.fragment_sy_other_lv_heart);
            holder.llZan = view.findViewById(R.id.fragment_sy_other_lv_ll_zan);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getAvatar()).into(holder.ivTouxiang);
        holder.tvNickName.setText((String) data.get(i).getNickname());
        holder.tvCreatTime.setText("发布: " + TimeUtil.LongFormatTime(String.valueOf(data.get(i).getC_time())));
        holder.tvPingLun.setText(data.get(i).getContent());
        Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getCover()).into(holder.ivCover);
        holder.tvTitle.setText(data.get(i).getTitle());

        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyOtherRvBiaoqianAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(i).getClassX().size() > 0) {
            holder.rv.setLayoutManager(manager);
            for (int a = 0; a < data.get(i).getClassX().size(); a++) {
                list.add("#" + data.get(i).getClassX().get(a).getName() + " ");
            }
            adapter = new FragmentSyOtherRvBiaoqianAdapter(list);
            holder.rv.setAdapter(adapter);
        }

        holder.llPinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getId() + "", data, i));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

//        holder.llContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(context, ShipinXqActivity.class);
//                context.startActivity(intent);
//            }
//        });

        if (data.get(i).getType().equals("video")) {
            holder.video.setVisibility(View.VISIBLE);
            holder.nineGridTestLayout.setVisibility(View.GONE);
            holder.video.setUp(Consts.QINIU_URL + data.get(i).getVideo(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                    "");
            Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getCover() + "-690x388").into(holder.video.thumbImageView);
        }

        if (data.get(i).getType().equals("news")) {
            holder.video.setVisibility(View.GONE);
            holder.nineGridTestLayout.setVisibility(View.VISIBLE);
            //九宫格
            holder.nineGridTestLayout.setListener(listener1);
            holder.nineGridTestLayout.setItemPosition(i);
            holder.nineGridTestLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            holder.nineGridTestLayout.setSpacing(5); //动态设置图片之间的间隔
            holder.nineGridTestLayout.setUrlList(data.get(i).getImages());
        }

        //喜欢、评论、赞的逻辑
        holder.tvZanNum.setText(data.get(i).getPraise_num() + "");
        holder.tvLikeNum.setText(data.get(i).getLike_num() + "");
        holder.tvPlNum.setText(data.get(i).getLook_num() + "");
        Picasso.with(context).load(data.get(i).getIs_like() == 0 ? R.drawable.heart : R.drawable.heart1).into(holder.ivLikeImg);
        Picasso.with(context).load(data.get(i).getIs_praise() == 0 ? R.drawable.zan : R.drawable.zan1).into(holder.ivZanImg);
        holder.llZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    if (data.get(i).getIs_praise() == 0) {
                        Picasso.with(context).load(R.drawable.zan1).into(holder.ivZanImg);
                        int zanNum = Integer.valueOf(holder.tvZanNum.getText().toString()) + 1;
                        holder.tvZanNum.setText(zanNum + "");
                        data.get(i).setPraise_num(zanNum);
                        data.get(i).setIs_praise(1);
                        Praise.praise(context, uid, token, data.get(i).getId() + "");
                    } else {
                        Picasso.with(context).load(R.drawable.zan).into(holder.ivZanImg);
                        int zanNum = Integer.valueOf(holder.tvZanNum.getText().toString()) - 1;
                        holder.tvZanNum.setText(zanNum + "");
                        data.get(i).setPraise_num(zanNum);
                        data.get(i).setIs_praise(0);
                        Praise.cancelPraise(context, uid, token, data.get(i).getId() + "");
                    }
                }
            }
        });
        holder.llLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    if (data.get(i).getIs_like() == 0) {
                        Picasso.with(context).load(R.drawable.heart1).into(holder.ivLikeImg);
                        int likeNum = Integer.valueOf(holder.tvLikeNum.getText().toString()) + 1;
                        holder.tvLikeNum.setText(likeNum + "");
                        data.get(i).setLike_num(likeNum);
                        data.get(i).setIs_like(1);
                        Like.like(context, uid, token, data.get(i).getId()+"");
                    } else {
                        Picasso.with(context).load(R.drawable.heart).into(holder.ivLikeImg);
                        int likeNum = Integer.valueOf(holder.tvLikeNum.getText().toString()) - 1;
                        holder.tvLikeNum.setText(likeNum + "");
                        data.get(i).setLike_num(likeNum);
                        data.get(i).setIs_like(0);
                        Like.cancelLike(context, uid, token, data.get(i).getId()+"");
                    }
                }
            }
        });

        return view;
    }

    /**
     * 设置回调接口
     *
     * @param listener
     */
    public void setOnSyOtherListClickListener(FragmentSyOtherListener listener) {
        this.listener = listener;
    }

    private class ViewHolder {
        LinearLayout llPinglun;
        ImageView ivTouxiang;
        TextView tvNickName;
        TextView tvCreatTime;
        TextView tvPingLun;
        ImageView ivCover;
        TextView tvTitle;
        RecyclerView rv;
        LinearLayout llContent;
        TextView tvZanNum;
        NineGridTestLayout nineGridTestLayout;
        JZVideoPlayerStandard video;
        TextView tvLikeNum;
        TextView tvPlNum;
        ImageView ivLikeImg;
        ImageView ivZanImg;
        LinearLayout llLike;
        LinearLayout llZan;
    }

    /**
     * 按钮点击事件的回调接口
     * type 1为评论
     */
    public interface FragmentSyOtherListener {
        void onClick(int position, int type);
    }

}
