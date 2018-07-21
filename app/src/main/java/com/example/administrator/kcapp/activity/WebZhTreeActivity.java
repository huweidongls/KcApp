package com.example.administrator.kcapp.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.kcapp.R;
import com.example.administrator.kcapp.app.MyApp;
import com.example.administrator.kcapp.utils.StatusBarUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebZhTreeActivity extends AppCompatActivity {

    @BindView(R.id.activity_zh_tree_webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_zh_tree);

        StatusBarUtils.setStatusBar(WebZhTreeActivity.this, Color.parseColor("#ffffff"));
        ButterKnife.bind(this);
        MyApp.getInstance().addActivity(this);

        init();

    }

    private void init() {

        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        /**
         * 缓存策略
         */
//        webView.setCacheStrategy(WebViewCache.CacheStrategy.FORCE);
////        webView.setBlockNetworkImage(true);
//        webView.setEnableCache(true);

        //支持插件
//        webSettings.setPluginsEnabled(true);

        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + "cache";
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        //扩大比例的缩webSettings
        webSettings.setUseWideViewPort(true);
        //自适应屏幕 webSettings
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);

        webSettings.setAllowContentAccess(true); // 是否可访问Content Provider的资源，默认值 true
        webSettings.setAllowFileAccess(true);    // 是否可访问本地文件，默认值 true

        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); //webview中缓存模式
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        webView.loadUrl("http://edu.beijingyjmx.com/index/member/tree");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                Log.e("111", url + "");
//                return true;
                //Android8.0以下的需要返回true 并且需要loadUrl；8.0之后效果相反
                if(Build.VERSION.SDK_INT<26) {
                    view.loadUrl(url);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        WebZhTreeActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
