package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.FenleiXqActivity;
import com.example.administrator.kcapp.entity.SyDragListviewEntity;
import com.example.administrator.kcapp.eventbus.FenleiXqEvent;
import com.example.administrator.kcapp.gsonbean.GetClassBean;
import com.example.administrator.kcapp.utils.Consts;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Administrator on 2018/6/12 0012.
 */

public class SyFenleiAdapter extends RecyclerView.Adapter<SyFenleiAdapter.MyViewHolder> {

    StartDragListener draglistener;
    Vibrator vibrator;
    private Context context;
    //set接口回调
    public void setDraglistener(StartDragListener draglistener) {
        this.draglistener = draglistener;
    }

    private OnItemClickListener listener;

    List<GetClassBean.DataBean> list;
    public SyFenleiAdapter(List<GetClassBean.DataBean> list) {
        this.list=list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_sy_drag, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Picasso.with(context).load(Consts.BASE_URL+list.get(position).getIco()).into(holder.img);
        holder.title.setText(list.get(position).getName());
        holder.content.setText(list.get(position).getContent());
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,100};   // 停止
                vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
                return true;
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, FenleiXqActivity.class);
                EventBus.getDefault().postSticky(new FenleiXqEvent(list.get(position).getId()+"", list.get(position).getName(), list.get(position).getContent(), list.get(position).getCover()));
                context.startActivity(intent);
            }
        });
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView title;
        TextView content;
        RelativeLayout relativeLayout;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.sy_fenlei_lv_img);
            title = itemView.findViewById(R.id.sy_fenlei_lv_title);
            content = itemView.findViewById(R.id.sy_fenlei_lv_content);
            relativeLayout = itemView.findViewById(R.id.sy_fenlei_lv_rl);
            icon = itemView.findViewById(R.id.sy_fenlei_lv_icon);
        }

    }

    public interface OnItemClickListener{
        void onClick(String title);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
