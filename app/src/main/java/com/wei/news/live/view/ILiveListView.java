package com.wei.news.live.view;

import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.sdk.mvp.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public interface ILiveListView extends BaseView {

    void loadData(List<LiveListEntity.Data> data);
    void addData(List<LiveListEntity.Data> data);
    void showErrorMoreLoadView();
    void showMoreLoadingView();
    void showFootView();

}
