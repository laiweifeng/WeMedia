package com.wei.news.live.presenter;

import com.wei.news.live.entity.BannerEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.entity.LiveTagEntity;
import com.wei.news.live.model.ILiveChannelModel;
import com.wei.news.live.model.LiveChannelModel;
import com.wei.news.live.view.ILiveChannelView;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.L;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LiveChannelPresenter extends BasePresenter<ILiveChannelView,ILiveChannelModel> implements ILiveChannelPresenter {

    int pageToken=0;

    public LiveChannelPresenter(ILiveChannelView iLiveView){
        attachView(iLiveView,new LiveChannelModel());
    }

    @Override
    public void loadBannerData( ) {

        Observable<BannerEntity> bannerEntityObservable =
                mvpModel.loadBannerData();

        RetrofitCreateHelper.getInstance().onSubscribe(bannerEntityObservable,new ApiCallback<BannerEntity>() {
            @Override
            public void onSuccess(BannerEntity entity) {

                mvpView.loadBannerData(entity);

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

            }
        });
    }

    @Override
    public void loadTagData() {
        L.d("loadTagData");
        Observable<LiveTagEntity> liveTagEntityObservable =
                mvpModel.loadTagData();

        RetrofitCreateHelper.getInstance().onSubscribe(liveTagEntityObservable,new ApiCallback<LiveTagEntity>() {
            @Override
            public void onSuccess(LiveTagEntity entity) {
                L.d("loadTagData onSuccess");
                mvpView.loadTagData(entity);
            }

            @Override
            public void onFailure(String msg) {

            }

            @Override
            public void onFinish() {

            }
            @Override
            public void onSub(Disposable d) {

            }
        });
    }

    @Override
    public void loadLiveListData(String kw) {
        mvpView.showLoadingView();
        Observable<LiveListEntity> observable = mvpModel.loadLiveListData(pageToken, kw);

        RetrofitCreateHelper.getInstance().onSubscribe(observable,new ApiCallback<LiveListEntity>() {
            @Override
            public void onSuccess(LiveListEntity entity) {

                //筛选出正在直播的数据
                List<LiveListEntity.Data> liveData=new ArrayList<>();
                List<LiveListEntity.Data> dataList=  entity.getData();
                if(dataList!=null){
                    for (LiveListEntity.Data data : dataList) {
                        if(data.isLive()){
                            liveData.add(data);
                        }
                    }
                }

                mvpView.loadLiveListData(liveData);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.showReload();
            }

            @Override
            public void onFinish() {
                mvpView.hideLoadingView();
            }
            @Override
            public void onSub(Disposable d) {

            }
        });
    }
}
