package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.FenleiXqActivity;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.activity.SyFenleiActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyTjCardRvAdapter;
import com.example.administrator.kcapp.eventbus.FenleiXqEvent;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.FollowClassBean;
import com.example.administrator.kcapp.gsonbean.FollowClassFxBean;
import com.example.administrator.kcapp.gsonbean.GetHotClassBean;
import com.example.administrator.kcapp.gsonbean.GetPostRankBean;
import com.example.administrator.kcapp.gsonbean.GetTopAdvBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.example.administrator.kcapp.utils.Utils;
import com.example.administrator.kcapp.viewpagercard.CardFragmentPagerAdapter;
import com.example.administrator.kcapp.viewpagercard.CardFxPagerAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class FragmentSyFxHeader extends LinearLayout {

    private ViewPager viewPager;
    private CardFxPagerAdapter mCardAdapter;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private Context context;
    private FragmentActivity fragmentActivity;
    private FragmentSyFxHeaderListener listener;

    private ImageView ivShipin;
    private RelativeLayout rlShipin1;
    private RelativeLayout rlShipin2;

    private ImageView ivGuanggao;

    private ImageView iv2;
    private ImageView iv3;

    private ImageView ivTouxiang;

    private TextView title1;
    private TextView title2;
    private TextView title3;

    private RecyclerView rv1;
    private RecyclerView rv2;
    private RecyclerView rv3;

    private TextView time1;
    private TextView time2;
    private TextView time3;

    private ImageView ivClass1;
    private ImageView ivClass2;
    private ImageView ivClass3;

    private TextView tvClassName1;
    private TextView tvClassName2;
    private TextView tvClassName3;

    private TextView tvClassContent1;
    private TextView tvClassContent2;
    private TextView tvClassContent3;

    private RelativeLayout rlLeibie1;
    private RelativeLayout rlLeibie2;
    private RelativeLayout rlLeibie3;

    private TextView tvGz1;
    private TextView tvGz2;
    private TextView tvGz3;

    private String uid;
    private String token;

    private RelativeLayout rlAll;

    public FragmentSyFxHeader(Context context, FragmentActivity fragmentActivity, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.fragmentActivity = fragmentActivity;
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.header_sy_fx, this, true);

        init();

    }

    private void init() {

        viewPager = findViewById(R.id.fragment_sy_fx_lv_header_viewPager);
        ivShipin = findViewById(R.id.fragment_sy_fx_lv_header_iv1);
        iv2 = findViewById(R.id.fragment_sy_fx_lv_header_iv2);
        iv3 = findViewById(R.id.fragment_sy_fx_lv_header_iv3);
        rlShipin1 = findViewById(R.id.fragment_sy_fx_lv_header_shipin1);
        rlShipin2 = findViewById(R.id.fragment_sy_fx_lv_header_shipin2);
        ivGuanggao = findViewById(R.id.fragment_sy_fx_lv_header_guanggao);
        ivTouxiang = findViewById(R.id.fragment_sy_fx_lv_header_touxiang);
        title1 = findViewById(R.id.fragment_sy_fx_lv_header_title1);
        title2 = findViewById(R.id.fragment_sy_fx_lv_header_title2);
        title3 = findViewById(R.id.fragment_sy_fx_lv_header_title3);
        rv1 = findViewById(R.id.fragment_sy_fx_lv_header_rv1);
        rv2 = findViewById(R.id.fragment_sy_fx_lv_header_rv2);
        rv3 = findViewById(R.id.fragment_sy_fx_lv_header_rv3);
        time1 = findViewById(R.id.fragment_sy_fx_lv_header_time1);
        time2 = findViewById(R.id.fragment_sy_fx_lv_header_time2);
        time3 = findViewById(R.id.fragment_sy_fx_lv_header_time3);
        ivClass1 = findViewById(R.id.fragment_sy_fx_lv_header_leibie1_img);
        ivClass2 = findViewById(R.id.fragment_sy_fx_lv_header_leibie2_img);
        ivClass3 = findViewById(R.id.fragment_sy_fx_lv_header_leibie3_img);
        tvClassName1 = findViewById(R.id.fragment_sy_fx_lv_header_leibie1_tv1);
        tvClassName2 = findViewById(R.id.fragment_sy_fx_lv_header_leibie2_tv1);
        tvClassName3 = findViewById(R.id.fragment_sy_fx_lv_header_leibie3_tv1);
        tvClassContent1 = findViewById(R.id.fragment_sy_fx_lv_header_leibie1_tv2);
        tvClassContent2 = findViewById(R.id.fragment_sy_fx_lv_header_leibie2_tv2);
        tvClassContent3 = findViewById(R.id.fragment_sy_fx_lv_header_leibie3_tv2);
        rlLeibie1 = findViewById(R.id.fragment_sy_fx_lv_header_leibie1);
        rlLeibie2 = findViewById(R.id.fragment_sy_fx_lv_header_leibie2);
        rlLeibie3 = findViewById(R.id.fragment_sy_fx_lv_header_leibie3);
        tvGz1 = findViewById(R.id.fragment_sy_fx_lv_header_leibie1_guanzhu);
        tvGz2 = findViewById(R.id.fragment_sy_fx_lv_header_leibie2_guanzhu);
        tvGz3 = findViewById(R.id.fragment_sy_fx_lv_header_leibie3_guanzhu);
        rlAll = findViewById(R.id.fragment_sy_fx_lv_header_chakanquanbu);

    }

    /**
     * 热门类别
     *
     * @param data
     */
    public void setHotClass(final List<GetHotClassBean.DataBean> data) {
        final Intent intent = new Intent();
        rlAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setClass(context, SyFenleiActivity.class);
                context.startActivity(intent);
            }
        });
        SharedPreferences pref = context.getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        tvGz1.setText(data.get(0).getIs_follow() == 0 ? "+关注" : "已关注");
        tvGz2.setText(data.get(1).getIs_follow() == 0 ? "+关注" : "已关注");
        tvGz3.setText(data.get(2).getIs_follow() == 0 ? "+关注" : "已关注");

        tvGz1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    ViseHttp.POST("api/member/followClass")
                            .addParam("uid", uid)
                            .addParam("cid", data.get(0).getCid() + "")
                            .addParam("token", token)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data1) {
                                    Gson gson = new Gson();
                                    FollowClassFxBean followClassBean = gson.fromJson(data1, FollowClassFxBean.class);
                                    if (followClassBean.getCode() == 200) {
                                        if (followClassBean.getData() == 1) {
                                            ToastUtil.showShort(context, "关注成功!");
                                            tvGz1.setText("已关注");
                                            data.get(0).setIs_follow(1);
                                        } else if (followClassBean.getData() == 0) {
                                            ToastUtil.showShort(context, "取关成功!");
                                            tvGz1.setText("+关注");
                                            data.get(0).setIs_follow(0);
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
        tvGz2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    ViseHttp.POST("api/member/followClass")
                            .addParam("uid", uid)
                            .addParam("cid", data.get(1).getCid() + "")
                            .addParam("token", token)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data1) {
                                    Gson gson = new Gson();
                                    FollowClassBean followClassBean = gson.fromJson(data1, FollowClassBean.class);
                                    if (followClassBean.getCode() == 200) {
                                        if (followClassBean.getData() == 1) {
                                            ToastUtil.showShort(context, "关注成功!");
                                            tvGz2.setText("已关注");
                                            data.get(1).setIs_follow(1);
                                        } else if (followClassBean.getData() == 0) {
                                            ToastUtil.showShort(context, "取关成功!");
                                            tvGz2.setText("+关注");
                                            data.get(1).setIs_follow(0);
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
        tvGz3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(context, "请登录!");
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    ViseHttp.POST("api/member/followClass")
                            .addParam("uid", uid)
                            .addParam("cid", data.get(2).getCid() + "")
                            .addParam("token", token)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data1) {
                                    Gson gson = new Gson();
                                    FollowClassBean followClassBean = gson.fromJson(data1, FollowClassBean.class);
                                    if (followClassBean.getCode() == 200) {
                                        if (followClassBean.getData() == 1) {
                                            ToastUtil.showShort(context, "关注成功!");
                                            tvGz3.setText("已关注");
                                            data.get(2).setIs_follow(1);
                                        } else if (followClassBean.getData() == 0) {
                                            ToastUtil.showShort(context, "取关成功!");
                                            tvGz3.setText("+关注");
                                            data.get(2).setIs_follow(0);
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

        Picasso.with(context).load(Consts.BASE_URL + data.get(0).getIco()).into(ivClass1);
        Picasso.with(context).load(Consts.BASE_URL + data.get(1).getIco()).into(ivClass2);
        Picasso.with(context).load(Consts.BASE_URL + data.get(2).getIco()).into(ivClass3);

        tvClassName1.setText(data.get(0).getName());
        tvClassName2.setText(data.get(1).getName());
        tvClassName3.setText(data.get(2).getName());

        tvClassContent1.setText(data.get(0).getContent());
        tvClassContent2.setText(data.get(1).getContent());
        tvClassContent3.setText(data.get(2).getContent());

        rlLeibie1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, FenleiXqActivity.class);
                EventBus.getDefault().postSticky(new FenleiXqEvent(data.get(0).getId() + "", data.get(0).getName(), data.get(0).getContent(), data.get(0).getCover()));
                context.startActivity(intent);
            }
        });
        rlLeibie2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, FenleiXqActivity.class);
                EventBus.getDefault().postSticky(new FenleiXqEvent(data.get(1).getId() + "", data.get(1).getName(), data.get(1).getContent(), data.get(1).getCover()));
                context.startActivity(intent);
            }
        });
        rlLeibie3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, FenleiXqActivity.class);
                EventBus.getDefault().postSticky(new FenleiXqEvent(data.get(2).getId() + "", data.get(2).getName(), data.get(2).getContent(), data.get(2).getCover()));
                context.startActivity(intent);
            }
        });

    }

    /**
     * 排行
     *
     * @param data
     */
    public void setRankings(final List<GetPostRankBean.DataBean> data) {
        final Intent intent = new Intent();
        Picasso.with(context).load(Consts.QINIU_URL + data.get(0).getCover()).into(ivShipin);
        Picasso.with(context).load(Consts.QINIU_URL + data.get(1).getCover()).into(iv2);
        Picasso.with(context).load(Consts.QINIU_URL + data.get(2).getCover()).into(iv3);
        Picasso.with(context).load(Consts.QINIU_URL + data.get(0).getAvatar()).into(ivTouxiang);
        title1.setText(data.get(0).getTitle());
        title2.setText(data.get(1).getTitle());
        title3.setText(data.get(2).getTitle());

        ivShipin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getId() + ""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        rlShipin1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(1).getId() + ""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        rlShipin2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(2).getId() + ""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyTjCardRvAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(0).getClassX() != null) {
            rv1.setLayoutManager(manager);
            for (int i = 0; i < data.get(0).getClassX().size(); i++) {
                list.add("#" + data.get(0).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rv1.setAdapter(adapter);
        }

        manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (data.get(1).getClassX() != null) {
            rv2.setLayoutManager(manager);
            list.clear();
            for (int i = 0; i < data.get(1).getClassX().size(); i++) {
                list.add("#" + data.get(1).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rv2.setAdapter(adapter);
        }

        manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (data.get(2).getClassX() != null) {
            rv3.setLayoutManager(manager);
            list.clear();
            for (int i = 0; i < data.get(2).getClassX().size(); i++) {
                list.add("#" + data.get(2).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rv3.setAdapter(adapter);
        }

        time1.setText(Utils.secToTime(data.get(0).getVideo_time_length()));
        time2.setText(Utils.secToTime(data.get(1).getVideo_time_length()));
        time3.setText(Utils.secToTime(data.get(2).getVideo_time_length()));

    }

    /**
     * 广告
     *
     * @param imgUrl
     */
    public void setAdvertisement(String imgUrl) {
        Picasso.with(context).load(Consts.BASE_URL + imgUrl).into(ivGuanggao);
    }

    public void setCardView(List<GetTopAdvBean.DataBean> data) {

        mCardAdapter = new CardFxPagerAdapter();
        for (int i = 0; i < data.size(); i++) {
            mCardAdapter.addCardItem(data.get(i));
        }
        mFragmentCardAdapter = new CardFragmentPagerAdapter(fragmentActivity.getSupportFragmentManager(),
                Utils.dpToPixels(1, getContext()));

        viewPager.setAdapter(mCardAdapter);
        viewPager.setOffscreenPageLimit(3);

    }

    public interface FragmentSyFxHeaderListener {
        void onClick(View view);
    }

    public void setOnFragmentSyFxHeaderListener(FragmentSyFxHeaderListener listener) {
        this.listener = listener;
    }

}
