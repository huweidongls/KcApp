package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.activity.TieziXqActivity;
import com.example.administrator.kcapp.adapter.FragmentSyTjCardRvAdapter;
import com.example.administrator.kcapp.eventbus.TieziXqEvent;
import com.example.administrator.kcapp.gsonbean.ClassHotPostBean;
import com.example.administrator.kcapp.gsonbean.GetHotPostBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.Utils;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentSyOtherHeader extends LinearLayout {

    private Context context;

    private ImageView ivShipin;
    private RelativeLayout rlShipin1;
    private RelativeLayout rlShipin2;

    private ImageView iv2;
    private ImageView iv3;

    //近期热门
    private TextView tvHeaderTime1;
    private TextView tvHeaderTime2;
    private TextView tvHeaderTime3;

    private ImageView ivTouxiang;

    private TextView tvHeaderTitle1;
    private TextView tvHeaderTitle2;
    private TextView tvHeaderTitle3;

    private RecyclerView rvHeader1;
    private RecyclerView rvHeader2;
    private RecyclerView rvHeader3;

    //最近更新
//    private ImageView ivOther1;
//    private ImageView ivOther2;
//    private ImageView ivOther3;
//
//    private TextView tvOtherTime1;
//    private TextView tvOtherTime2;
//    private TextView tvOtherTime3;
//
//    private ImageView ivOtherTouxiang;
//
//    private TextView tvOtherTitle1;
//    private TextView tvOtherTitle2;
//    private TextView tvOtherTitle3;
//
//    private RecyclerView rvOther1;
//    private RecyclerView rvOther2;
//    private RecyclerView rvOther3;
//
//    private RelativeLayout rlOtherShipin1;
//    private RelativeLayout rlOtherShipin2;

    private FragmentSyOtherHeaderListener listener;

    public FragmentSyOtherHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.header_sy_other, this, true);

        init();

    }

    private void init() {

        ivShipin = findViewById(R.id.fragment_sy_other_lv_header_iv1);
        rlShipin1 = findViewById(R.id.fragment_sy_other_lv_header_rl_shipin1);
        rlShipin2 = findViewById(R.id.fragment_sy_other_lv_header_rl_shipin2);
        iv2 = findViewById(R.id.fragment_sy_other_lv_header_iv2);
        iv3 = findViewById(R.id.fragment_sy_other_lv_header_iv3);
        tvHeaderTime1 = findViewById(R.id.fragment_sy_other_lv_header_time1);
        tvHeaderTime2 = findViewById(R.id.fragment_sy_other_lv_header_time2);
        tvHeaderTime3 = findViewById(R.id.fragment_sy_other_lv_header_time3);
        ivTouxiang = findViewById(R.id.fragment_sy_other_lv_header_touxiang);
        tvHeaderTitle1 = findViewById(R.id.fragment_sy_other_lv_header_tv_title1);
        tvHeaderTitle2 = findViewById(R.id.fragment_sy_other_lv_header_tv_title2);
        tvHeaderTitle3 = findViewById(R.id.fragment_sy_other_lv_header_tv_title3);
        rvHeader1 = findViewById(R.id.fragment_sy_other_lv_header_rv1);
        rvHeader2 = findViewById(R.id.fragment_sy_other_lv_header_rv2);
        rvHeader3 = findViewById(R.id.fragment_sy_other_lv_header_rv3);

//        ivOther1 = findViewById(R.id.fragment_sy_other1_lv_header_iv1);
//        ivOther2 = findViewById(R.id.fragment_sy_other1_lv_header_iv2);
//        ivOther3 = findViewById(R.id.fragment_sy_other1_lv_header_iv3);
//        tvOtherTime1 = findViewById(R.id.fragment_sy_other1_lv_header_tv_time1);
//        tvOtherTime2 = findViewById(R.id.fragment_sy_other1_lv_header_tv_time2);
//        tvOtherTime3 = findViewById(R.id.fragment_sy_other1_lv_header_tv_time3);
//        ivOtherTouxiang = findViewById(R.id.fragment_sy_other1_lv_header_touxiang);
//        tvOtherTitle1 = findViewById(R.id.fragment_sy_other1_lv_header_tv_title1);
//        tvOtherTitle2 = findViewById(R.id.fragment_sy_other1_lv_header_tv_title2);
//        tvOtherTitle3 = findViewById(R.id.fragment_sy_other1_lv_header_tv_title3);
//        rvOther1 = findViewById(R.id.fragment_sy_other1_lv_header_rv1);
//        rvOther2 = findViewById(R.id.fragment_sy_other1_lv_header_rv2);
//        rvOther3 = findViewById(R.id.fragment_sy_other1_lv_header_rv3);
//        rlOtherShipin1 = findViewById(R.id.fragment_sy_other1_lv_header_rl_shipin1);
//        rlOtherShipin2 = findViewById(R.id.fragment_sy_other1_lv_header_rl_shipin2);

    }

    /**
     * 最近更新
     * @param data
     */
//    public void setZuijingengxin(final List<ClassHotPostBean.DataBean> data){
//
//        if(data.get(0).getSub().size()>0){
//
//            rlOtherShipin1.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent();
//                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getSub().get(1).getId()+""));
//                    intent.setClass(context, TieziXqActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            rlOtherShipin2.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent();
//                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getSub().get(2).getId()+""));
//                    intent.setClass(context, TieziXqActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//
//            Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getSub().get(0).getCover()).into(ivOther1);
//            ivOther1.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent();
//                    EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getSub().get(0).getId()+""));
//                    intent.setClass(context, TieziXqActivity.class);
//                    context.startActivity(intent);
//                }
//            });
//            Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getSub().get(1).getCover()).into(ivOther2);
//            Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getSub().get(2).getCover()).into(ivOther3);
//
//            tvOtherTime1.setText(Utils.secToTime(data.get(0).getSub().get(0).getVideo_time_length()));
//            tvOtherTime2.setText(Utils.secToTime(data.get(0).getSub().get(1).getVideo_time_length()));
//            tvOtherTime3.setText(Utils.secToTime(data.get(0).getSub().get(2).getVideo_time_length()));
//
//            Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getSub().get(0).getAvatar()).into(ivOtherTouxiang);
//
//            tvOtherTitle1.setText(data.get(0).getSub().get(0).getTitle());
//            tvOtherTitle2.setText(data.get(0).getSub().get(1).getTitle());
//            tvOtherTitle3.setText(data.get(0).getSub().get(2).getTitle());
//
//            //创建LinearLayoutManager
//            LinearLayoutManager manager = new LinearLayoutManager(context);
//            //设置为横向滑动
//            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            FragmentSyTjCardRvAdapter adapter;
//            List<String> list = new ArrayList<>();
//            if (data.get(0).getSub().get(0).getClassX().size() >0) {
//                rvOther1.setLayoutManager(manager);
//                for (int i = 0; i < data.get(0).getSub().get(0).getClassX().size(); i++) {
//                    list.add("#" + data.get(0).getSub().get(0).getClassX().get(i).getName() + " ");
//                }
//                adapter = new FragmentSyTjCardRvAdapter(list);
//                rvOther1.setAdapter(adapter);
//            }
//
//            manager = new LinearLayoutManager(context);
//            //设置为横向滑动
//            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            if (data.get(0).getSub().get(0).getClassX().size() >0) {
//                rvOther2.setLayoutManager(manager);
//                list.clear();
//                for (int i = 0; i < data.get(0).getSub().get(1).getClassX().size(); i++) {
//                    list.add("#" + data.get(0).getSub().get(1).getClassX().get(i).getName() + " ");
//                }
//                adapter = new FragmentSyTjCardRvAdapter(list);
//                rvOther2.setAdapter(adapter);
//            }
//
//            manager = new LinearLayoutManager(context);
//            //设置为横向滑动
//            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            if (data.get(0).getSub().get(0).getClassX().size() >0) {
//                rvOther3.setLayoutManager(manager);
//                list.clear();
//                for (int i = 0; i < data.get(0).getSub().get(2).getClassX().size(); i++) {
//                    list.add("#" + data.get(0).getSub().get(2).getClassX().get(i).getName() + " ");
//                }
//                adapter = new FragmentSyTjCardRvAdapter(list);
//                rvOther3.setAdapter(adapter);
//            }
//        }
//
//    }

    /**
     * 近期热门
     * @param data
     */
    public void setHotPost(final List<GetHotPostBean.DataBean> data){
        final Intent intent = new Intent();

        ivShipin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(0).getId()+""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });
        rlShipin1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(1).getId()+""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });
        rlShipin2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new TieziXqEvent(data.get(2).getId()+""));
                intent.setClass(context, TieziXqActivity.class);
                context.startActivity(intent);
            }
        });

        Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getCover()).into(ivShipin);
        Picasso.with(context).load(Consts.QINIU_URL+data.get(1).getCover()).into(iv2);
        Picasso.with(context).load(Consts.QINIU_URL+data.get(2).getCover()).into(iv3);

        tvHeaderTime1.setText(Utils.secToTime(data.get(0).getVideo_time_length()));
        tvHeaderTime2.setText(Utils.secToTime(data.get(1).getVideo_time_length()));
        tvHeaderTime3.setText(Utils.secToTime(data.get(2).getVideo_time_length()));

        Picasso.with(context).load(Consts.QINIU_URL+data.get(0).getAvatar()).into(ivTouxiang);

        tvHeaderTitle1.setText(data.get(0).getTitle());
        tvHeaderTitle2.setText(data.get(1).getTitle());
        tvHeaderTitle3.setText(data.get(2).getTitle());

        //创建LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyTjCardRvAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(0).getClassX() != null) {
            rvHeader1.setLayoutManager(manager);
            for (int i = 0; i < data.get(0).getClassX().size(); i++) {
                list.add("#" + data.get(0).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rvHeader1.setAdapter(adapter);
        }

        manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (data.get(1).getClassX() != null) {
            rvHeader2.setLayoutManager(manager);
            list.clear();
            for (int i = 0; i < data.get(1).getClassX().size(); i++) {
                list.add("#" + data.get(1).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rvHeader2.setAdapter(adapter);
        }

        manager = new LinearLayoutManager(context);
        //设置为横向滑动
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        if (data.get(2).getClassX() != null) {
            rvHeader3.setLayoutManager(manager);
            list.clear();
            for (int i = 0; i < data.get(2).getClassX().size(); i++) {
                list.add("#" + data.get(2).getClassX().get(i).getName() + " ");
            }
            adapter = new FragmentSyTjCardRvAdapter(list);
            rvHeader3.setAdapter(adapter);
        }

    }

    public interface FragmentSyOtherHeaderListener{
        void onClick(View view);
    }

    public void setOnFragmentSyOtherHeaderListener(FragmentSyOtherHeaderListener listener){
        this.listener = listener;
    }

}
