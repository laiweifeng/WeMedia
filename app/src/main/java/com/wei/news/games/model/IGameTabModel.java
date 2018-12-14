package com.wei.news.games.model;

import com.wei.news.games.entity.GameListEntity;
import com.wei.news.headline.entity.TypeListEntity;

import io.reactivex.Observable;

public interface IGameTabModel {

    Observable<GameListEntity> loadData(int pageToken, String catid);

}
