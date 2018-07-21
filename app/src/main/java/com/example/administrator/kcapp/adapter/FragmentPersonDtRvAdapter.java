package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.gsonbean.MyPostBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.TimeUtil;
import com.example.administrator.kcapp.view.ninegridview.NineGridTestLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by Administrator on 2018/7/6.
 */

public class FragmentPersonDtRvAdapter extends RecyclerView.Adapter<FragmentPersonDtRvAdapter.ViewHolder> {

    private List<MyPostBean.DataBean> data;
    private Context context;

    private OnItemPictureClickListener listener1;

    public void setPictureClickListener(OnItemPictureClickListener listener1){
        this.listener1 = listener1;
    }

    public FragmentPersonDtRvAdapter(List<MyPostBean.DataBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fragment_person_dt_rv, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(Consts.QINIU_URL+data.get(position).getAvatar()).into(holder.ivTouxiang);
        holder.tvNickname.setText(data.get(position).getNickname());
        holder.tvCreateTime.setText(TimeUtil.LongFormatTime(data.get(position).getC_time()+""));
        holder.tvContent.setText(data.get(position).getContent());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        FragmentSyOtherRvBiaoqianAdapter adapter;
        List<String> list = new ArrayList<>();
        if (data.get(position).getClassX().size() > 0) {
            holder.recyclerView.setLayoutManager(manager);
            for (int a = 0; a < data.get(position).getClassX().size(); a++) {
                list.add("#" + data.get(position).getClassX().get(a).getName() + " ");
            }
            adapter = new FragmentSyOtherRvBiaoqianAdapter(list);
            holder.recyclerView.setAdapter(adapter);
        }
        if (data.get(position).getType().equals("video")) {
            holder.jzVideoPlayerStandard.setVisibility(View.VISIBLE);
            holder.nineGridTestLayout.setVisibility(View.GONE);
            holder.jzVideoPlayerStandard.setUp(Consts.QINIU_URL + data.get(position).getVideo(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                    "");
            Picasso.with(context).load(Consts.QINIU_URL + data.get(position).getCover() + "-690x388").into(holder.jzVideoPlayerStandard.thumbImageView);
        }

        if (data.get(position).getType().equals("news")) {
            holder.jzVideoPlayerStandard.setVisibility(View.GONE);
            holder.nineGridTestLayout.setVisibility(View.VISIBLE);
            //九宫格
            holder.nineGridTestLayout.setListener(listener1);
            holder.nineGridTestLayout.setItemPosition(position);
            holder.nineGridTestLayout.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
            holder.nineGridTestLayout.setSpacing(5); //动态设置图片之间的间隔
            holder.nineGridTestLayout.setUrlList(data.get(position).getImages());
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivTouxiang;
        private TextView tvNickname;
        private TextView tvCreateTime;
        private TextView tvContent;
        private RecyclerView recyclerView;
        private NineGridTestLayout nineGridTestLayout;
        private JZVideoPlayerStandard jzVideoPlayerStandard;

        public ViewHolder(View itemView) {
            super(itemView);
            ivTouxiang = itemView.findViewById(R.id.fragment_person_dt_iv_touxiang);
            tvNickname = itemView.findViewById(R.id.fragment_person_dt_tv_nickname);
            tvCreateTime = itemView.findViewById(R.id.fragment_person_dt_tv_c_time);
            tvContent = itemView.findViewById(R.id.fragment_person_dt_tv_content);
            recyclerView = itemView.findViewById(R.id.fragment_person_dt_rv_biaoqian);
            nineGridTestLayout = itemView.findViewById(R.id.fragment_person_dt_ninegrid);
            jzVideoPlayerStandard = itemView.findViewById(R.id.fragment_person_dt_video);
        }
    }

}
