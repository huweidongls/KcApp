package com.example.administrator.kcapp.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.activity.LoginActivity;
import com.example.administrator.kcapp.activity.MyFriendActivity;
import com.example.administrator.kcapp.activity.MyQRCodeActivity;
import com.example.administrator.kcapp.activity.PersonSetActivity;
import com.example.administrator.kcapp.activity.PersonZyActivity;
import com.example.administrator.kcapp.activity.RegisterActivity;
import com.example.administrator.kcapp.activity.SyFbActivity;
import com.example.administrator.kcapp.activity.SyFbDtActivity;
import com.example.administrator.kcapp.adapter.FragmentWdNewRvAdapter;
import com.example.administrator.kcapp.adapter.FragmentWdRvAdapter;
import com.example.administrator.kcapp.entity.FragmentWdRvEntity;
import com.example.administrator.kcapp.utils.Consts;
import com.example.administrator.kcapp.utils.L;
import com.example.administrator.kcapp.utils.ToastUtil;
import com.squareup.picasso.Picasso;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Administrator on 2018/6/6 0006.
 */

public class FragmentWd extends Fragment {

    @BindView(R.id.fragment_wd_rv)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_wd_touxiang)
    ImageView ivTouxiang;
    @BindView(R.id.fragment_wd_tv_nickname)
    TextView tvNickname;
    @BindView(R.id.fragment_wd_tv_person_zy)
    TextView tvPersonZy;
    @BindView(R.id.fragment_wd_settings)
    ImageView ivSet;
    @BindView(R.id.fragment_wd_rl_friend)
    RelativeLayout rlFriend;

    private List<FragmentWdRvEntity> data;
    private FragmentWdNewRvAdapter adapter;

    private String uid;
    private String token = "";

    public static final int REQUEST_CODE = 110;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wd, null);
        ButterKnife.bind(this, view);

        init();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences pref = getContext().getSharedPreferences(Consts.FILENAME, MODE_PRIVATE);
        uid = L.decrypt(pref.getString("uid", ""), Consts.LKEY);
        token = L.decrypt(pref.getString("token", ""), Consts.LKEY);
        String avatar = L.decrypt(pref.getString("avatar", ""), Consts.LKEY);

        if (TextUtils.isEmpty(uid) || uid.equals("")) {
            Picasso.with(getContext()).load(R.drawable.meitoux).into(ivTouxiang);
            tvNickname.setText("");
            tvPersonZy.setText("点击登录即可发表评论及同步已喜欢视频");
        } else {
            if (TextUtils.isEmpty(avatar) || avatar.equals("")) {
                Picasso.with(getContext()).load(R.drawable.meitoux).into(ivTouxiang);
            } else {
                Picasso.with(getContext()).load(Consts.QINIU_URL + avatar).into(ivTouxiang);
            }
            tvNickname.setText(L.decrypt(pref.getString("username", ""), Consts.LKEY));
            tvPersonZy.setText("查看个人主页 >");
        }
    }

    private void init() {

        data = new ArrayList<>();
        data.add(new FragmentWdRvEntity(R.drawable.add_friend, "加好友"));
        data.add(new FragmentWdRvEntity(R.drawable.saoyisao, "扫一扫"));
        data.add(new FragmentWdRvEntity(R.drawable.erweima, "我的二维码"));
        data.add(new FragmentWdRvEntity(R.drawable.wd_bz, "需要帮助"));
        data.add(new FragmentWdRvEntity(R.drawable.wd_aq, "账号安全"));

        recyclerView.getItemAnimator().setChangeDuration(300);
        recyclerView.getItemAnimator().setMoveDuration(300);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FragmentWdNewRvAdapter(data);
        adapter.setOnItemListener(new FragmentWdNewRvAdapter.onItemListener() {
            @Override
            public void onClick(int i) {
                Intent intent = new Intent();
                switch (i) {
                    case 0:

                        break;
                    case 1:
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            intent.setClass(getContext(), CaptureActivity.class);
                            startActivityForResult(intent, REQUEST_CODE);
                        } else {
                            //申请权限
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            ToastUtil.showShort(getContext(), "拍照或SD卡权限被禁用，请在权限管理修改");
                        }
                        break;
                    case 2:
                        if (TextUtils.isEmpty(uid) || uid.equals("")) {
                            ToastUtil.showShort(getContext(), "请登录");
                            intent.setClass(getContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            //跳转到我的二维码
                            intent.setClass(getContext(), MyQRCodeActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                }
            }
        });
//        adapter.setLists(data);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();

                    addFriend(result);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * 扫二维码添加好友
     *
     * @param result
     */
    private void addFriend(String result) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("请选择分组");
        final String[] type = {"好友", "老师", "学生"};
        //    设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'好友' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(type, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "type为：" + type[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

//        ViseHttp.POST("")
//                .addParam("uid", uid)
//                .addParam("token", token)
//                .addParam("fuid", result)
//                .request(new ACallback<String>() {
//                    @Override
//                    public void onSuccess(String data) {
//
//                    }
//
//                    @Override
//                    public void onFail(int errCode, String errMsg) {
//
//                    }
//                });

    }

    @OnClick({R.id.fragment_wd_touxiang, R.id.fragment_wd_settings, R.id.fragment_wd_tv_person_zy, R.id.fragment_wd_rl_friend})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.fragment_wd_touxiang:
                if (TextUtils.isEmpty(uid)) {
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtil.showShort(getContext(), "设置头像");
                }
                break;
            case R.id.fragment_wd_settings:
                intent.setClass(getContext(), PersonSetActivity.class);
                startActivity(intent);
                break;
            case R.id.fragment_wd_tv_person_zy:
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(getContext(), "请登录");
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    //跳转到个人主页
                    intent.setClass(getContext(), PersonZyActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment_wd_rl_friend:
                if (TextUtils.isEmpty(uid) || uid.equals("")) {
                    ToastUtil.showShort(getContext(), "请登录");
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    //跳转到我的好友页面
                    intent.setClass(getContext(), MyFriendActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
