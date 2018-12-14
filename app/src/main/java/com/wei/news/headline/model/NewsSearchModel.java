package com.wei.news.headline.model;

import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.Constant;

import io.reactivex.Observable;

public class NewsSearchModel implements INewsSearchModel {

    @Override
    public Observable<TypeListEntity> loadData(int pageToken, String kw) {
        return RetrofitCreateHelper.getInstance().createApi()
                .getTencentNewsList2key(pageToken,kw, Constant.APIKEY);
    }

}
