package com.example.administrator.kcapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.administrator.kcapp.gsonbean.MemberLikeBean;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/7/6.
 */

public class Like {

    public static void like(final Context context, String uid, String token, String pid) {
        ViseHttp.POST("api/member/likePost")
                .addParam("uid", uid)
                .addParam("token", token)
                .addParam("pid", pid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            if (code == 200) {
                                ToastUtil.showShort(context, "喜欢成功");
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

    public static void cancelLike(final Context context, String uid, String token, String pid) {
        ViseHttp.POST("api/member/cancelLikePost")
                .addParam("uid", uid)
                .addParam("token", token)
                .addParam("pid", pid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Log.e("222", data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            int code = jsonObject.getInt("code");
                            if (code == 200) {
                                ToastUtil.showShort(context, "取消喜欢");
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

}
