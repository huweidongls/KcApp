package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.ActivityFbDtZtEntity;
import com.example.administrator.kcapp.gsonbean.GetClassBean;
import com.example.administrator.kcapp.utils.Consts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class ActivityFbDtZtAdapter extends BaseAdapter {

    private Context context;
    private List<GetClassBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    private OnSelectListener listener;

    public ActivityFbDtZtAdapter(Context context, List<GetClassBean.DataBean> data) {
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
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_activity_fb_select_zt, viewGroup, false);
            holder.tv = view.findViewById(R.id.activity_sy_fb_dt_zt_lv1_tv);
            holder.img = view.findViewById(R.id.activity_sy_fb_dt_zt_lv1_img);
            holder.name = view.findViewById(R.id.activity_sy_fb_dt_zt_lv1_name);
            holder.select = view.findViewById(R.id.activity_sy_fb_dt_zt_lv1_select);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        if(i == 0){
            holder.tv.setText("全部");
            holder.tv.setVisibility(View.VISIBLE);
        }else {
            holder.tv.setVisibility(View.GONE);
        }
//        else if(i == 3){
//            holder.tv.setText("全部");
//            holder.tv.setVisibility(View.VISIBLE);
//        }else {
//            holder.tv.setVisibility(View.GONE);
//        }

        Picasso.with(context).load(Consts.BASE_URL+data.get(i).getIco()).into(holder.img);

        holder.name.setText(data.get(i).getName());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.select.getText().equals("选择")){
                    holder.select.setText("已选择");
                    holder.select.setBackgroundResource(R.drawable.bg_activity_fb_dt_zt_selector);
                    holder.select.setTextColor(Color.parseColor("#989898"));
                    listener.onClick(i, true);
                }else {
                    holder.select.setText("选择");
                    holder.select.setBackgroundResource(R.drawable.bg_fragment_sy_tj_guanzhu);
                    holder.select.setTextColor(Color.parseColor("#333333"));
                    listener.onClick(i, false);
                }
            }
        });

        return view;
    }

    private class ViewHolder{
        TextView tv;
        ImageView img;
        TextView name;
        TextView select;
    }

    public interface OnSelectListener{
        void onClick(int i, boolean isSelect);
    }

    public void setOnSelectListener(OnSelectListener listener){
        this.listener = listener;
    }

}
