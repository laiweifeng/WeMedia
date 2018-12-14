package com.wei.news.headline.view;

import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.mvp.BaseView;

public interface ITabView extends BaseView {

    void loadData(TypeListEntity data);
    void addData(TypeListEntity data );
    void showErrorMoreLoadView();
    void showMoreLoadingView();
    void showFootView();
}
