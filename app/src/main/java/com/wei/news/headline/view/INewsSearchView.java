package com.wei.news.headline.view;

import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.mvp.BaseView;

import io.reactivex.disposables.Disposable;

public interface INewsSearchView extends BaseView {

    void loadData(TypeListEntity data);
    void addData(TypeListEntity data);
    void showMoreLoadingView();
    void showErrorMoreLoadView();
    void showFootView();
    void addDisposable(Disposable disposable);
}
