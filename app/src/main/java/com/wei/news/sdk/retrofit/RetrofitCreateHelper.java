package com.wei.news.sdk.retrofit;



import com.wei.news.App;
import com.wei.news.utils.Constant;
import com.wei.news.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Horrarndoo on 2017/9/7.
 * <p>
 */

public class RetrofitCreateHelper  {

    static RetrofitCreateHelper retrofitCreateHelper=new RetrofitCreateHelper();

    private RetrofitCreateHelper(){}

    public static RetrofitCreateHelper getInstance(){
        return retrofitCreateHelper;
    }

    private static final int DEFAULT_TIME_OUT = 15;//超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;


    //缓存容量
     long SIZE_OF_CACHE = 10 * 1024 * 1024; // 10 MiB
    //缓存路径
     String cacheFile = App.getContext().getCacheDir()+"/http";
     Cache cache = new Cache(new File(cacheFile), SIZE_OF_CACHE);

    OkHttpClient.Builder builder = new OkHttpClient.Builder()
           .cache(cache)
            .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接 超时时间
            .writeTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS)//写操作 超时时间
            .readTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS);//读操作 超时时间
//            .retryOnConnectionFailure(true);//错误重连


    public  <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    public  ApiStores createApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(builder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(ApiStores.class);
    }


    public  void onSubscribe(Observable observable, Observer observer){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void clearDisposables(List<Disposable> disposables){
        if(disposables!=null){
            for (Disposable disposable : disposables) {
                L.d("disposable:");
                disposable.dispose();
            }
        }
    }



}

