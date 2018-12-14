package com.wei.news.games.view;

import com.wei.news.games.entity.GameListEntity;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.mvp.BaseView;

public interface IGameTabView extends BaseView {

    void loadData(GameListEntity data);
    void addData(GameListEntity data);
    void showErrorMoreLoadView();
    void showMoreLoadingView();
    void showFootView();
}
