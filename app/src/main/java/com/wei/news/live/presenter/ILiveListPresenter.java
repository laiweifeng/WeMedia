package com.wei.news.live.presenter;

import com.wei.news.live.model.ILiveListModel;
import com.wei.news.live.view.ILiveListView;
import com.wei.news.sdk.base.BasePresenter;

public interface ILiveListPresenter{

    void loadData(String kw);
    void addData(String kw);
}
