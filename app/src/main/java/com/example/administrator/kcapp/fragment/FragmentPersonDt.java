package com.example.administrator.kcapp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.ImagePreviewActivity;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.adapter.FragmentPersonDtRvAdapter;
import com.example.administrator.kcapp.gsonbean.MyPostBean;
import com.example.administrator.kcapp.interfaces.OnItemPictureClickListener;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/7/5.
 */

public class FragmentPersonDt extends Fragment {

    @BindView(R.id.fragment_person_dt_rv)
    RecyclerView recyclerView;

    private List<Integer> data;
    private FragmentPersonDtRvAdapter adapter;

    private String uid = "";
    private String token = "";

    private int itemPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_dt, null);

        ButterKnife.bind(this, view);

        initData();

        return view;
    }

    private void initData() {

        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);

        ViseHttp.POST("api/member/myPost")
                .addParam("uid", uid)
                .addParam("token", token)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Intent intent = new Intent();
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            String msg = jsonObject.getString("msg");
                            if (code == 200) {
                                Gson gson = new Gson();
                                MyPostBean myPostBean = gson.fromJson(data, MyPostBean.class);
                                if (myPostBean.getData() != null) {
                                    init(myPostBean.getData());
                                }
                            }else if(msg.equals("登录已过期")){
                                ToastUtil.showShort(getContext(), "登录失效，请重新登录!");
                                intent.setClass(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });

    }

    private void init(List<MyPostBean.DataBean> data) {

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new FragmentPersonDtRvAdapter(data);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setPictureClickListener(new OnItemPictureClickListener() {
            @Override
            public void onItemPictureClick(int itemPostion, int i, String url, List<String> urlList, ImageView imageView) {
                itemPosition = itemPostion;
                Intent intent = new Intent(getContext(), ImagePreviewActivity.class);
                intent.putStringArrayListExtra("imageList", (ArrayList<String>) urlList);
                intent.putExtra(Consts.START_ITEM_POSITION, itemPosition);
                intent.putExtra(Consts.START_IAMGE_POSITION, i);
//                ActivityOptions compat = ActivityOptions.makeSceneTransitionAnimation(getActivity(), imageView, imageView.getTransitionName());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.photoview_open, 0);
            }
        });

    }
}
