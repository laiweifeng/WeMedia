package com.wei.news.live.model;

import com.wei.news.live.entity.LiveListEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ILiveListModel {

    Observable<LiveListEntity> loadData(int pageToken, String kw);
}
