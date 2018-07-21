package com.example.administrator.kcapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.administrator.kcapp.gsonbean.MemberPraiseBean;
import com.google.gson.Gson;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/7/5.
 */

public class Praise {

    public static void praise(final Context context, String uid, String token, String pid) {
        ViseHttp.POST("api/member/praise")
                .addParam("uid", uid)
                .addParam("token", token)
                .addParam("pid", pid)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data1) {
                        Log.e("222", data1);
                        try {
                            JSONObject jsonObject = new JSONObject(data1);
                            int code = jsonObject.getInt("code");
                            if (code == 200) {
                                Gson gson = new Gson();
                                MemberPraiseBean memberPraiseBean = gson.fromJson(data1, MemberPraiseBean.class);
                                if (memberPraiseBean.getData().getSta() == 1) {
                                    ToastUtil.showShort(context, "点赞成功");
                                }
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

    public static void cancelPraise(final Context context, String uid, String token, String pid) {
        ViseHttp.POST("api/member/cancelPraise")
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
                                Gson gson = new Gson();
                                MemberPraiseBean memberPraiseBean = gson.fromJson(data, MemberPraiseBean.class);
                                if (memberPraiseBean.getData().getSta() == 1) {
                                    ToastUtil.showShort(context, "取消点赞");
                                }
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
