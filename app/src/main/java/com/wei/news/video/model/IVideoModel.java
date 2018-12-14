package com.wei.news.video.model;

import com.wei.news.video.entity.VideoEntity;

import io.reactivex.Observable;

public interface IVideoModel {

    Observable<VideoEntity> loadData(int pageToken);
}
