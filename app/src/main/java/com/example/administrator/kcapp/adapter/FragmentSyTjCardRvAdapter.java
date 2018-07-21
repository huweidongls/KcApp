package com.example.administrator.kcapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.kcapp.R;

import java.util.List;

/**
 * Created by Administrator on 2018/6/20.
 */

public class FragmentSyTjCardRvAdapter extends RecyclerView.Adapter<FragmentSyTjCardRvAdapter.CardViewHolder> {

    private List<String> data;
    private Context context;

    public FragmentSyTjCardRvAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        CardViewHolder holder = new CardViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_fragment_sy_tj_card, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public CardViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.fragment_sy_tj_card_rv_tv);
        }
    }
}
