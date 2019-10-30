package com.wei.news.me.fragments;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.wei.news.R;
import com.wei.news.sdk.base.BaseFragment;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.sdk.widget.X5WebView;

import butterknife.BindView;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.webView)
    X5WebView x5WebView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.iv_back)
    ImageButton iv_back;

    @BindView(R.id.iv_goHome)
    ImageButton iv_goHome;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

     final static String url="https://github.com/laiweifeng";

    @Override
    public void init() {
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        addLoadView(R.id.rootview);
    }


    @Override
    public void initData() {
        swipeRefreshLayout.setRefreshing(true);

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
//        // 解决图片不显示
//        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        settings.setBlockNetworkImage(false);
//
//
//        webView.loadUrl("https://github.com/laiweifeng");
//        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // TODO Auto-generated method stub
//                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//                view.loadUrl(url);
//                return true;
//            }
//        });

        getLoadStatusView().showLoading();
        x5WebView.loadUrl(url);

    }

    @Override
    public void lazyData() {

    }

    @Override
    public void setListener() {
        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(true);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                swipeRefreshLayout.setRefreshing(false);
                getLoadStatusView().hideLoadStatus();
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                getLoadStatusView().showReload();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                x5WebView.loadUrl(url);
            }
        });

        getLoadStatusView().setonReloadClickListener(new LoadStatusView.onReloadClickListener() {
            @Override
            public void OnReloadClick() {
                getLoadStatusView().showLoading();
                x5WebView.loadUrl(url);
            }
        });

        iv_back.setOnClickListener(this);
        iv_goHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_goHome:
                swipeRefreshLayout.setRefreshing(true);
                x5WebView.loadUrl(url);
                break;

            case R.id.iv_back:
                if (x5WebView != null && x5WebView.canGoBack()){
                    swipeRefreshLayout.setRefreshing(true);
                    x5WebView.goBack();
                }

                break;
        }
    }
}
