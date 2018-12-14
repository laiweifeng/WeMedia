package com.wei.news.sdk.base;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenter<V,M> {
    public V mvpView;
    public M mvpModel;
    private CompositeDisposable mCompositeDisposable;

    public void attachView(V mvpView,M mvpModel) {
        this.mvpView = mvpView;
        this.mvpModel = mvpModel;
    }

    public void detachView() {
        this.mvpView = null;
        this.mvpModel=null;
    }


}
