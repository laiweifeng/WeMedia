package com.wei.news.headline.model;

import com.wei.news.headline.entity.TypeListEntity;

import io.reactivex.Observable;

public interface ITabModel {

    Observable<TypeListEntity> loadData(int pageToken, String catid);

}
