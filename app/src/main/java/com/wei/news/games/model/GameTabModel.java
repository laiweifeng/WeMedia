package com.wei.news.games.model;

import com.wei.news.games.entity.GameListEntity;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.Constant;
import com.wei.news.utils.L;

import io.reactivex.Observable;

public class GameTabModel implements IGameTabModel {

    @Override
    public Observable<GameListEntity> loadData(int pageToken, String catid) {
        L.d("pageToken:"+pageToken+"   catid:"+catid);
        return RetrofitCreateHelper.getInstance().createApi()
                .getGamesListData(catid,pageToken, Constant.APIKEY);
    }

}
