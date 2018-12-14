package com.wei.news.live.model;

import com.wei.news.live.entity.BannerEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.entity.LiveTagEntity;

import io.reactivex.Observable;


public interface ILiveChannelModel {

    Observable<BannerEntity> loadBannerData();
    Observable<LiveTagEntity> loadTagData();
    Observable<LiveListEntity> loadLiveListData(int pageToken,String kw);
}
