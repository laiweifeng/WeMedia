package com.wei.news;

import android.app.Application;
import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;
import com.wei.news.sdk.widget.X5WebView;

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        //预先加载webview，防止首次启动卡顿
//        QbSdk.initX5Environment(this,null);
        new X5WebView(this);
    }

    public static Context getContext(){
        return context;
    }
}
