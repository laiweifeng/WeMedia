package com.wei.news.video.model;

import com.wei.news.utils.Constant;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.video.entity.VideoEntity;
import com.wei.news.video.model.IVideoModel;

import io.reactivex.Observable;

public class VideoModel implements IVideoModel {
    @Override
    public Observable<VideoEntity> loadData(int pageToken) {

        return RetrofitCreateHelper.getInstance().createApi()
                .getVideoListData(pageToken,
                        Constant.TYPE,
                        Constant.VIDEO_CATID,
                        Constant.SOUND_SORT,
                        Constant.VIDEO_SORT,
                        Constant.APIKEY);

    }
}
