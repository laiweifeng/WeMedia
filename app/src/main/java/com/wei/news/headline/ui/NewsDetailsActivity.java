package com.wei.news.headline.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wei.news.R;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.base.BaseActivity;
import com.wei.news.sdk.widget.X5WebView;
import com.wei.news.utils.L;

import butterknife.BindView;

public class NewsDetailsActivity extends BaseActivity{

    @BindView(R.id.webView)
    X5WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void init() {

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    @Override
    public void initData() {
        Bundle bundle = getIntent().getBundleExtra(IntentManager.KEY_NAME);
        setTitle(bundle.getString("title"));
        String url=bundle.getString("url");
        L.d("url:"+url);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
//        settings.setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        settings.setSupportZoom(true);//是否可以缩放，默认true
//        settings.setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
//        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
//        settings.setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        settings.setAppCacheEnabled(true);//是否使用缓存
//        settings.setDomStorageEnabled(true);//DOM Storage
//
//        // 解决6.0 7.0 8.0 图片不显示
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        settings.setBlockNetworkImage(false);
//
//
        webView.loadUrl(url);
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webView.setWebViewClient(new WebViewClient(){
//             @Override
//             public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                 // TODO Auto-generated method stub
//                 // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                         view.loadUrl(url);
//                   return true;
//             }
//        });

    }

    @Override
    public void setListener() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
