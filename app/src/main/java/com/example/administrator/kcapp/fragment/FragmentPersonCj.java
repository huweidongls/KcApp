package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.WebCjYzActivity;
import com.example.administrator.kcapp.activity.WebZhTreeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/7/6.
 */

public class FragmentPersonCj extends Fragment {

    @BindView(R.id.fragment_person_cj_tv_jinru)
    TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_cj, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.fragment_person_cj_tv_jinru})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.fragment_person_cj_tv_jinru:
                intent.setClass(getContext(), WebZhTreeActivity.class);
                startActivity(intent);
                break;
        }
    }

}
