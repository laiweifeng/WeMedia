package com.wei.news.video.presenter;

import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.L;
import com.wei.news.video.entity.VideoEntity;
import com.wei.news.video.model.IVideoModel;
import com.wei.news.video.model.VideoModel;
import com.wei.news.video.view.IVideoView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class VideoPresenter  extends BasePresenter<IVideoView,IVideoModel> implements IVideoPresenter {

    int pageToken=1;

    public VideoPresenter(IVideoView iVideoView){
        attachView(iVideoView,new VideoModel());
    }

    @Override
    public void loadData() {
        L.d("loadData");
        mvpView.showLoadingView();
        Observable<VideoEntity> observable = mvpModel.loadData(pageToken);

        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<VideoEntity>() {
            @Override
            public void onSuccess(VideoEntity entity) {
                L.d(""+entity.toString());
                mvpView.loadData(entity);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {
                mvpView.hideLoadingView();
            }
            @Override
            public void onSub(Disposable d) {
                mvpView.addDisposable(d);
            }
        });

    }

    @Override
    public void addData() {

    }
}
