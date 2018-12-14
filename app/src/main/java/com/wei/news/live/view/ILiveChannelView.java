package com.wei.news.live.view;

import com.wei.news.live.entity.BannerEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.entity.LiveTagEntity;
import com.wei.news.sdk.mvp.BaseView;

import java.util.List;

public interface ILiveChannelView extends BaseView {
    void loadBannerData(BannerEntity bannerEntity);
    void loadTagData(LiveTagEntity liveTagEntity);
    void loadLiveListData(List<LiveListEntity.Data> liveListDataList);
}
