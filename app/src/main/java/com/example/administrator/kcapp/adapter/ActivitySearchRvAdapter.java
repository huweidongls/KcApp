package com.example.administrator.kcapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.entity.ActivitySearchRvEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/6/14 0014.
 */

public class ActivitySearchRvAdapter extends RecyclerView.Adapter<ActivitySearchRvAdapter.SearchViewHolder> {

    private List<ActivitySearchRvEntity> data;
    private ActivitySearchRvListener listener;

    public ActivitySearchRvAdapter(List<ActivitySearchRvEntity> data) {
        this.data = data;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_activity_search_remen, parent, false);
        SearchViewHolder holder = new SearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.textView.setText(data.get(position).getText());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(data.get(position).getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.activity_search_rv_tv);
        }
    }

    public interface ActivitySearchRvListener{
        void onClick(String text);
    }

    public void setOnItemClickListener(ActivitySearchRvListener listener){
        this.listener = listener;
    }

}
