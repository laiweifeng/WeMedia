package com.wei.news.sdk.mvp;

import io.reactivex.disposables.Disposable;

public interface BaseView {

    void showLoadingView();
    void hideLoadingView();
    void showNoMore();
    void showReload();
    void addDisposable(Disposable disposable);

}
