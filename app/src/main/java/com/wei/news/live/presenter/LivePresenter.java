package com.wei.news.live.presenter;

import com.wei.news.live.model.ILiveModel;
import com.wei.news.live.model.LiveModel;
import com.wei.news.live.view.ILiveView;
import com.wei.news.sdk.base.BasePresenter;

public class LivePresenter extends BasePresenter<ILiveView,ILiveModel> {

    public  LivePresenter(ILiveView iLiveView){
        attachView(iLiveView,new LiveModel());
    }



}
