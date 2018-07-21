package com.example.administrator.kcapp.view.header;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.adapter.FragmentGzZpAllgzAdapter;
import com.example.administrator.kcapp.entity.FragmentGzZpAllgzEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/13 0013.
 */

public class FragmentGzZpHeader extends LinearLayout {

    private Context context;
    private RecyclerView recyclerView;

    private List<FragmentGzZpAllgzEntity> data;
    private FragmentGzZpAllgzAdapter allgzAdapter;

    public FragmentGzZpHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.header_gz_zp, this, true);

        initRecyclerView();

    }

    private void initRecyclerView() {

        recyclerView = findViewById(R.id.fragment_gz_zp_recyclerview);

        data = new ArrayList<>();
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        data.add(new FragmentGzZpAllgzEntity(R.drawable.touxiang));
        allgzAdapter = new FragmentGzZpAllgzAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(allgzAdapter);

    }

}
