package com.wei.news.headline.model;

import com.wei.news.headline.entity.TypeListEntity;

import io.reactivex.Observable;

public interface INewsSearchModel {

    Observable<TypeListEntity> loadData(int pageToken, String kw);

}
