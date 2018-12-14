package com.wei.news.live.presenter;

import com.wei.news.live.entity.BannerEntity;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.utils.L;

public interface ILiveChannelPresenter {

    void loadBannerData( );
    void loadTagData();
    void loadLiveListData(String kw);
}
