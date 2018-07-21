package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.gsonbean.PostSharpBean;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/11 0011.
 */

public class NiurenLvAdapter extends BaseAdapter {

    private Context context;
    private List<PostSharpBean.DataBean> data = new ArrayList<>();
    private LayoutInflater inflater;

    private OnItemListener listener;

    public void setItemListener(OnItemListener listener) {
        this.listener = listener;
    }

    public NiurenLvAdapter(Context context, List<PostSharpBean.DataBean> data) {
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
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_niuren, null);
            holder.imageView = view.findViewById(R.id.niuren_lv_iv);
            holder.textView1 = view.findViewById(R.id.niuren_lv_tv1);
            holder.textView2 = view.findViewById(R.id.niuren_lv_tv2);
            holder.rl = view.findViewById(R.id.niuren_lv_rl);
            holder.tvBiaoqian = view.findViewById(R.id.niuren_lv_tv_biaoqian);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(Consts.QINIU_URL + data.get(i).getCover()).into(holder.imageView);
        holder.textView1.setText(data.get(i).getTitle());
        holder.textView2.setText(Utils.secToTime(data.get(i).getVideo_time_length()));

        String str = "";
        if (data.get(i).getClassX().size() > 0) {
            for (int a = 0; a < data.get(i).getClassX().size(); a++) {
                str = str + data.get(i).getClassX().get(a).getName() + "/";
            }
        }
        holder.tvBiaoqian.setText(str);

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(i, data.get(i));
            }
        });

        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        RelativeLayout rl;
        TextView tvBiaoqian;
    }

    public interface OnItemListener {
        void onClick(int i, PostSharpBean.DataBean bean);
    }

}
