package com.wei.news.live.presenter;

import com.wei.news.R;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.model.ILiveListModel;
import com.wei.news.live.model.LiveListModel;
import com.wei.news.live.view.ILiveListView;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class LiveListPresenter  extends BasePresenter<ILiveListView,ILiveListModel> implements ILiveListPresenter{

    private boolean requestAddData=false;
    int pageToken=0;

    public LiveListPresenter(ILiveListView view){
        attachView(view,new LiveListModel());
    }

    @Override
    public void loadData(String kw) {
        mvpView.showLoadingView();
        pageToken=0;
        Observable<LiveListEntity> observable = mvpModel.loadData(pageToken, kw);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<LiveListEntity>() {
            @Override
            public void onSuccess(LiveListEntity entity) {
                //筛选出正在直播的数据
                List<LiveListEntity.Data> dataList = filtrate(entity.getData());
                if(dataList.size()==0){
                    TipManager.showTip(R.string.no_search_data);
                    return;
                }
                mvpView.loadData(dataList);
                mvpView.showFootView();
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
                mvpView.addDisposable(d);
            }
        });
    }

    @Override
    public void addData(String kw) {
        if(requestAddData){
            return;
        }
        requestAddData=true;
        pageToken++;
        Observable<LiveListEntity> observable = mvpModel.loadData(pageToken, kw);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<LiveListEntity>() {
            @Override
            public void onSuccess(LiveListEntity entity) {
                //筛选出正在直播的数据
                List<LiveListEntity.Data> dataList = filtrate(entity.getData());
                if(dataList.size()==0){
                    mvpView.showNoMore();
                    return;
                }
                mvpView.addData(dataList);
            }

            @Override
            public void onFailure(String msg) {
                mvpView.showErrorMoreLoadView();
            }

            @Override
            public void onFinish() {
                requestAddData=false;
            }
            @Override
            public void onSub(Disposable d) {
                mvpView.addDisposable(d);
            }
        });
    }

    private List<LiveListEntity.Data> filtrate(List<LiveListEntity.Data> dataList) {
        List<LiveListEntity.Data> liveData=new ArrayList<>();
        if(dataList!=null){
            for (LiveListEntity.Data data : dataList) {
                if(data.isLive()){
                    liveData.add(data);
                }
            }
        }
        return liveData;
    }
}
