package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ShipinXqActivity;
import com.example.administrator.kcapp.adapter.FragmentGzZpAllgzAdapter;
import com.example.administrator.kcapp.adapter.FragmentGzZpLvAdapter;
import com.example.administrator.kcapp.entity.FragmentGzZpAllgzEntity;
import com.example.administrator.kcapp.entity.FragmentGzZpLvEntity;
import com.example.administrator.kcapp.view.header.FragmentGzZpHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class FragmentGzZp extends Fragment {

    @BindView(R.id.fragment_gz_zp_lv)
    ListView listView;

    private List<FragmentGzZpAllgzEntity> data;
    private FragmentGzZpAllgzAdapter allgzAdapter;

    private List<FragmentGzZpLvEntity> data1;
    private FragmentGzZpLvAdapter lvAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gz_zp, null);
        ButterKnife.bind(this, view);

        initListView();

        return view;
    }

    private void initListView() {

        data1 = new ArrayList<>();
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 1));
        data1.add(new FragmentGzZpLvEntity("", 2));
        data1.add(new FragmentGzZpLvEntity("", 2));
        lvAdapter = new FragmentGzZpLvAdapter(getContext(), data1);
        listView.addHeaderView(new FragmentGzZpHeader(getContext(), null));
        listView.setAdapter(lvAdapter);

        lvAdapter.setItemClick(new FragmentGzZpLvAdapter.ItemClick() {
            @Override
            public void onClick(int position, String type) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ShipinXqActivity.class);
                startActivity(intent);
            }
        });

    }

}
