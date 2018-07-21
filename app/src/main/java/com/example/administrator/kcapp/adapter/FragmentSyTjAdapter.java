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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.FenleiXqActivity;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.eventbus.FenleiXqEvent;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.ClassHotPostBean;
import com.example.administrator.kcapp.gsonbean.FollowClassBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.Utils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class FragmentSyTjAdapter extends BaseAdapter {

    private Context context;
    private List<ClassHotPostBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;
    private FragmentSyTjListener listener;

    private String uid;
    private String token;

    public FragmentSyTjAdapter(Context context, List<ClassHotPostBean.DataBean> data) {
        SharedPreferences pref = context.getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.listview_fragment_sy_tj, null);
            holder.ivShipin = view.findViewById(R.id.fragment_sy_tj_lv_iv1);
            holder.iv2 = view.findViewById(R.id.fragment_sy_tj_lv_iv2);
            holder.iv3 = view.findViewById(R.id.fragment_sy_tj_lv_iv3);
            holder.textView = view.findViewById(R.id.fragment_sy_tj_lv_title);
            holder.guanzhu = view.findViewById(R.id.fragment_sy_tj_lv_guanzhu);
            holder.fenxiang = view.findViewById(R.id.fragment_sy_tj_lv_fenxiang);
            holder.fenxiang1 = view.findViewById(R.id.fragment_sy_tj_lv_fenxiang1);
            holder.fenxiang2 = view.findViewById(R.id.fragment_sy_tj_lv_fenxiang2);
            holder.rlShipin1 = view.findViewById(R.id.fragment_sy_tj_lv_rl_shipin1);
            holder.rlShipin2 = view.findViewById(R.id.fragment_sy_tj_lv_rl_shipin2);
            holder.rv1 = view.findViewById(R.id.fragment_sy_tj_lv_rv1);
            holder.rv2 = view.findViewById(R.id.fragment_sy_tj_lv_rv2);
            holder.rv3 = view.findViewById(R.id.fragment_sy_tj_lv_rv3);
            holder.title1 = view.findViewById(R.id.fragment_sy_tj_lv_title1);
            holder.title2 = view.findViewById(R.id.fragment_sy_tj_lv_title2);
            holder.title3 = view.findViewById(R.id.fragment_sy_tj_lv_title3);
            holder.touxiang = view.findViewById(R.id.fragment_sy_tj_lv_touxiang);
            holder.time1 = view.findViewById(R.id.fragment_sy_tj_lv_time1);
            holder.time2 = view.findViewById(R.id.fragment_sy_tj_lv_time2);
            holder.time3 = view.findViewById(R.id.fragment_sy_tj_lv_time3);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(data.get(i).getName());//设置分类标题

        //设置分类标签
        List<String> l = new ArrayList<>();
        FragmentSyTjCardRvAdapter adapter;
        LinearLayoutManager manager;
        Log.e("234", i + "");
        if(data.get(i).getSub() != null&&data.get(i).getSub().size()>0){
            if(data.get(i).getSub().get(0).getClassX() != null&&data.get(i).getSub().get(0).getClassX().size()>0){
                for (int a = 0; a < data.get(i).getSub().get(0).getClassX().size(); a++) {
                    l.add("#" + data.get(i).getSub().get(0).getClassX().get(a).getName() + " ");
                }
                adapter = new FragmentSyTjCardRvAdapter(l);
                //创建LinearLayoutManager
                manager = new LinearLayoutManager(view.getContext());
                //设置为横向滑动
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                //设置
                holder.rv1.setLayoutManager(manager);
                holder.rv1.setAdapter(adapter);
            }

            l.clear();
            if(data.get(i).getSub().get(1).getClassX() != null&&data.get(i).getSub().get(1).getClassX().size()>0){
                for (int a = 0; a < data.get(i).getSub().get(1).getClassX().size(); a++) {
                    l.add("#" + data.get(i).getSub().get(1).getClassX().get(a).getName() + " ");
                }
                adapter = new FragmentSyTjCardRvAdapter(l);
                //创建LinearLayoutManager
                manager = new LinearLayoutManager(view.getContext());
                //设置为横向滑动
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                //设置
                holder.rv2.setLayoutManager(manager);
                holder.rv2.setAdapter(adapter);
            }

            l.clear();
            if(data.get(i).getSub().get(2).getClassX() != null&&data.get(i).getSub().get(2).getClassX().size()>0){
                for (int a = 0; a < data.get(i).getSub().get(2).getClassX().size(); a++) {
                    l.add("#" + data.get(i).getSub().get(2).getClassX().get(a).getName() + " ");
                }
                adapter = new FragmentSyTjCardRvAdapter(l);
                //创建LinearLayoutManager
                manager = new LinearLayoutManager(view.getContext());
                //设置为横向滑动
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                //设置
                holder.rv3.setLayoutManager(manager);
                holder.rv3.setAdapter(adapter);
            }

            //设置title
            holder.title1.setText(data.get(i).getSub().get(0).getTitle());
            holder.title2.setText(data.get(i).getSub().get(1).getTitle());
            holder.title3.setText(data.get(i).getSub().get(2).getTitle());

            //设置头像
            Glide.with(context).load(Consts.QINIU_URL + data.get(i).getSub().get(0).getAvatar()).into(holder.touxiang);

            //设置三张video图片
            Glide.with(context).load(Consts.QINIU_URL + data.get(i).getSub().get(0).getCover()).into(holder.ivShipin);
            holder.ivShipin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getSub().get(0).getId() + ""));
                    intent.setClass(context, TieziXqActivity.class);
                    context.startActivity(intent);
                }
            });
            holder.rlShipin1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getSub().get(1).getId() + ""));
                    intent.setClass(context, TieziXqActivity.class);
                    context.startActivity(intent);
                }
            });
            holder.rlShipin2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(i).getSub().get(2).getId() + ""));
                    intent.setClass(context, TieziXqActivity.class);
                    context.startActivity(intent);
                }
            });
            Glide.with(context).load(Consts.QINIU_URL + data.get(i).getSub().get(1).getCover()).into(holder.iv2);
            Glide.with(context).load(Consts.QINIU_URL + data.get(i).getSub().get(2).getCover()).into(holder.iv3);

            //设置时间
            holder.time1.setText(Utils.secToTime(data.get(i).getSub().get(0).getVideo_time_length()));
            holder.time2.setText(Utils.secToTime(data.get(i).getSub().get(1).getVideo_time_length()));
            holder.time3.setText(Utils.secToTime(data.get(i).getSub().get(2).getVideo_time_length()));
        }

        //关注
        final Intent intent = new Intent();
        holder.guanzhu.setText(data.get(i).getIs_follow() == 0 ? "+关注" : "已关注");

        holder.guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    ViseHttp.POST("api/member/followClass")
                            .addParam("uid", uid)
                            .addParam("cid", data.get(i).getId() + "")
                            .addParam("token", token)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data1) {
                                    Gson gson = new Gson();
                                    FollowClassBean followClassBean = gson.fromJson(data1, FollowClassBean.class);
                                    if (followClassBean.getCode() == 200) {
                                        if (followClassBean.getData() == 1) {
                                            ToastUtil.showShort(context, "关注成功!");
                                            holder.guanzhu.setText("已关注");
                                            data.get(i).setIs_follow(1);
                                        } else if (followClassBean.getData() == 0) {
                                            ToastUtil.showShort(context, "取关成功!");
                                            holder.guanzhu.setText("+关注");
                                            data.get(i).setIs_follow(0);
                                        }
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    ToastUtil.showShort(context, "网络请求失败!");
                                }
                            });
                }
            }
        });
        holder.fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i, holder.fenxiang);
            }
        });
        holder.fenxiang1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i, holder.fenxiang1);
            }
        });
        holder.fenxiang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i, holder.fenxiang2);
            }
        });

        return view;
    }

    /**
     * 设置回调接口
     *
     * @param listener
     */
    public void setOnSyTjListClickListener(FragmentSyTjListener listener) {
        this.listener = listener;
    }

    private class ViewHolder {
        ImageView ivShipin;
        ImageView iv2;
        ImageView iv3;
        TextView textView;
        TextView guanzhu;
        ImageView fenxiang;
        ImageView fenxiang1;
        ImageView fenxiang2;
        RelativeLayout rlShipin1;
        RelativeLayout rlShipin2;
        RecyclerView rv1;
        RecyclerView rv2;
        RecyclerView rv3;
        TextView title1;
        TextView title2;
        TextView title3;
        ImageView touxiang;
        TextView time1;
        TextView time2;
        TextView time3;
    }

    /**
     * 按钮点击事件的回调接口
     */
    public interface FragmentSyTjListener {
        void onClick(int position, View view);
    }

}
