package com.example.administrator.kcapp.app;

import android.app.Activity;
import android.app.Application;

import com.example.administrator.kcapp.utils.Consts;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.vise.xsnow.http.ViseHttp;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class MyApp extends Application {

    private int loginStatus = 1;

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    private int loginNewStatus = 2;

    public int getLoginNewStatus() {
        return loginNewStatus;
    }

    public void setLoginNewStatus(int loginNewStatus) {
        this.loginNewStatus = loginNewStatus;
    }

    private List<Activity> mList = new LinkedList<Activity>();
    private static MyApp instance;

    public MyApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(Consts.BASE_URL);

        ZXingLibrary.initDisplayOpinion(this);

    }

    public synchronized static MyApp getInstance() {
        if (null == instance) {
            instance = new MyApp();
        }
        return instance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
