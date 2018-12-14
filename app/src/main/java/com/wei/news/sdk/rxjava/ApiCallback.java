package com.wei.news.sdk.rxjava;



import com.wei.news.App;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.net.NetManager;
import com.wei.news.utils.L;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;



public abstract class ApiCallback<E > implements Observer<E> {


    public abstract void onSuccess(E entity);

    public abstract void onFailure(String msg);

    public abstract void onFinish();

    public abstract void onSub(Disposable d);

    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable=d;
        onSub(d);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        String message;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            L.d("ApiCallback onError code :"+code);
            message = httpException.getMessage();
            if (code == 504) {
                message = "网络不给力";
            }
            if (code == 502 || code == 404) {
                message = "服务器异常，请稍后再试";
            }
            if(code == 429){
                message = "请求过多，请检查是否同时多次请求";
            }
            TipManager.showTip(message);

        } else {
            message=e.getMessage();
            if(!NetManager.isNetworkConnected(App.getContext())){
                message="请检查网络是否可用";
//                TipManager.showTip("请检查网络是否可用");
            }
        }
        L.d("ApiCallback onError:"+message);

        dispase();

        onFailure(message);
        onFinish();
    }

    @Override
    public void onNext(E entity) {
        onSuccess(entity);
        L.d("ApiCallback onNext:");

    }

    @Override
    public void onComplete() {
        L.d("ApiCallback onComplete:");
        dispase();
        onFinish();
    }

    private void dispase() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


}
