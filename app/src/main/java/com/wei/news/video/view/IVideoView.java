package com.wei.news.video.view;

import com.wei.news.sdk.mvp.BaseView;
import com.wei.news.video.entity.VideoEntity;

public interface IVideoView extends BaseView {

    void loadData(VideoEntity videoEntity);
    void addData(VideoEntity videoEntity);


}
