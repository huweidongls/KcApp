package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.SearchActivity;
import com.example.administrator.kcapp.adapter.FragmentThreeKcAdapter;
import com.example.administrator.kcapp.entity.FragmentThreeKcEntity;
import com.example.administrator.kcapp.view.header.FragmentThreeHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class FragmentThree extends Fragment {

    @BindView(R.id.fragment_three_lv)
    ListView listView;
    @BindView(R.id.fragment_three_rl_sousuo)
    RelativeLayout tlSousuo;

    private FragmentThreeKcAdapter adapter;
    private List<FragmentThreeKcEntity> data = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    private void init() {

        data.add(new FragmentThreeKcEntity(R.drawable.three_kc_img, "", "", "", "", "", ""));
        data.add(new FragmentThreeKcEntity(R.drawable.three_kc_img, "", "", "", "", "", ""));
        data.add(new FragmentThreeKcEntity(R.drawable.three_kc_img, "", "", "", "", "", ""));
        data.add(new FragmentThreeKcEntity(R.drawable.three_kc_img, "", "", "", "", "", ""));
        data.add(new FragmentThreeKcEntity(R.drawable.three_kc_img, "", "", "", "", "", ""));
        adapter = new FragmentThreeKcAdapter(getContext(), data);

        listView.addHeaderView(new FragmentThreeHeader(getContext(), null));

        listView.setAdapter(adapter);

    }

    @OnClick({R.id.fragment_three_rl_sousuo})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.fragment_three_rl_sousuo:
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
        }
    }

}
