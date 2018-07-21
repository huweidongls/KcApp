package com.example.administrator.kcapp.utils;

import android.util.Log;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 99zan on 2018/1/17.
 */

public class HttpUtil {

    private static HttpUtil downloadUtil;
    private final OkHttpClient okHttpClient;
    private static String BASE_URL = "";

    public static HttpUtil getInstance() {
        if (downloadUtil == null) {
            synchronized (HttpUtil.class){
                if(downloadUtil == null){
                    downloadUtil = new HttpUtil();
                }
            }
        }
        return downloadUtil;
    }

    private HttpUtil() {
        okHttpClient = new OkHttpClient();
    }

    private void get(String url, Callback callback){
        Request.Builder requestBuilder = new Request.Builder().url(BASE_URL+url);
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= okHttpClient.newCall(request);
        mcall.enqueue(callback);
    }

    private void post(String url, RequestBody requestBody, Callback callback){
        Request request = new Request.Builder()
                .url(BASE_URL+url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void rxGet(final String url, AObserver<String> observer){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                get(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("222", "网络请求失败！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
//                        Log.e("222", result+"2222222222");
                        e.onNext(result);
                        e.onComplete();
                    }
                });
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void rxPost(final String url, final RequestBody requestBody, AObserver<String> observer){
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> e) throws Exception {
                post(url, requestBody, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("222", "网络请求失败！");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
//                        Log.e("222", result+"2222222222");
                        e.onNext(result);
                        e.onComplete();
                    }
                });
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
