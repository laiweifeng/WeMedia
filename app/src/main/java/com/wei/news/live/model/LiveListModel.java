package com.wei.news.live.model;

import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.model.ILiveListModel;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.Constant;

import java.util.List;

import io.reactivex.Observable;

public class LiveListModel implements ILiveListModel {


    @Override
    public Observable<LiveListEntity> loadData(int pageToken, String kw) {
        return RetrofitCreateHelper.getInstance().createApi().getLiveListData(pageToken,kw,Constant.APIKEY);
    }

}
