package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.FragmentWdRvEntity;
import com.example.administrator.kcapp.entity.FragmentWdRvRvEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/15.
 */

public class FragmentWdRvAdapter extends RecyclerView.Adapter<FragmentWdRvAdapter.ViewHolder> {

    private Context context;
    /**
     * 消息列表数据
     */
    private List<FragmentWdRvEntity> lists;

    /**
     * 标记展开的item
     */
    private int opened = -2;

    private boolean isFirst = true;

    private Animation animOpen;
    private Animation animClose;
    private LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿

    public FragmentWdRvAdapter(Context context) {
        this.context = context;
        lists = new ArrayList<>();
    }

    /**
     * 设置列表数据
     * @param lists
     */
    public void setLists(List<FragmentWdRvEntity> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_fragment_wd_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bindView(position,lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView img;
        private TextView text;
        private LinearLayout ll;
        private RelativeLayout rl;
        private RecyclerView rv;
        private LinearLayout ll1;
        private ImageView more;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.fragment_wd_rv_img);
            text = itemView.findViewById(R.id.fragment_wd_rv_tv);
            ll = itemView.findViewById(R.id.fragment_wd_rv_ll);
            rl = itemView.findViewById(R.id.fragment_wd_rv_rl);
            rv = itemView.findViewById(R.id.fragment_wd_rv_rv);
            ll1 = itemView.findViewById(R.id.fragment_wd_rv_ll1);
            more = itemView.findViewById(R.id.fragment_wd_rv_more);
            rl.setOnClickListener(this);
        }

        /**
         * 此方法实现列表数据的绑定和item的展开/关闭
         */
        void bindView(int pos, FragmentWdRvEntity bean) {

            Picasso.with(context).load(bean.getImgUrl()).into(img);
            text.setText(bean.getText());

            List<FragmentWdRvRvEntity> data = new ArrayList<>();
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            data.add(new FragmentWdRvRvEntity());
            FragmentWdRvRvAdapter adapter = new FragmentWdRvRvAdapter(context, data);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(manager);
            rv.setAdapter(adapter);

            animOpen = AnimationUtils.loadAnimation(context, R.anim.fragment_wd_rv_open);
            animOpen.setInterpolator(interpolator);

            animClose = AnimationUtils.loadAnimation(context, R.anim.fragment_wd_rv_close);
            animClose.setInterpolator(interpolator);

            if(isFirst){
//                if(pos == 0){
//                    ll.setVisibility(View.VISIBLE);
//                    animOpen.setFillAfter(!animOpen.getFillAfter());
//                    more.startAnimation(animOpen);
//                }
                isFirst = false;
            }else{
                if (pos == opened&&pos == 0){
                    ll.setVisibility(View.VISIBLE);
//                    animOpen.setFillAfter(!animOpen.getFillAfter());
//                    more.startAnimation(animOpen);
                } else{
                    ll.setVisibility(View.GONE);
//                    more.startAnimation(animClose);
//                    animClose.setFillAfter(!animClose.getFillAfter());
                }

                if (pos == opened&&pos == 1){
                    ll1.setVisibility(View.VISIBLE);
//                    animOpen.setFillAfter(!animOpen.getFillAfter());
//                    more.startAnimation(animOpen);
                } else{
                    ll1.setVisibility(View.GONE);
//                    more.startAnimation(animClose);
//                    animClose.setFillAfter(!animClose.getFillAfter());
                }
            }

        }
        /**
         * item的点击事件
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (opened == getAdapterPosition()) {
                //当点击的item已经被展开了, 就关闭.
                opened = -1;
                notifyItemChanged(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
            }
        }
    }

}
