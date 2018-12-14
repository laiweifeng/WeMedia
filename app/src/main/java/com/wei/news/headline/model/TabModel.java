package com.wei.news.headline.model;

import com.wei.news.utils.Constant;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;

import io.reactivex.Observable;

public class TabModel implements ITabModel {

    @Override
    public Observable<TypeListEntity> loadData(int pageToken, String catid) {
        return RetrofitCreateHelper.getInstance().createApi()
                .getTencentTypeListData(pageToken,catid, Constant.APIKEY);
    }

}
