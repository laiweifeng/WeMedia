package com.wei.news.headline.presenter;

import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.headline.model.ITabModel;
import com.wei.news.headline.model.TabModel;
import com.wei.news.headline.view.ITabView;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.L;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class TabPresenter extends BasePresenter<ITabView,ITabModel> implements ITabPresenter {

    private int pageToken=1;

    private boolean requestAddData=false;

     public TabPresenter(ITabView tabView){
         attachView(tabView,new TabModel());
    }
    
    
    @Override
    public void loadData( String catid) {

        pageToken=1;
        mvpView.showLoadingView();
        L.d(catid+"   loadData");
        Observable<TypeListEntity> observable = mvpModel.loadData(pageToken,catid);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<TypeListEntity>() {
            @Override
            public void onSuccess(TypeListEntity entity) {
                mvpView.loadData(entity);
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
    public void addData(String catid) {

         if(requestAddData){
             return;
         }
        requestAddData=true;
        pageToken++;
         L.d(catid+"  addData");
         mvpView.showMoreLoadingView();
        Observable<TypeListEntity> observable = mvpModel.loadData(pageToken,catid);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<TypeListEntity>() {
            @Override
            public void onSuccess(TypeListEntity entity) {
                mvpView.addData(entity);
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


}
