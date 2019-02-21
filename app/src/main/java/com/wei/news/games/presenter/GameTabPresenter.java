package com.wei.news.games.presenter;

import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.model.GameTabModel;
import com.wei.news.games.model.IGameTabModel;
import com.wei.news.games.view.IGameTabView;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.L;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class GameTabPresenter extends BasePresenter<IGameTabView,IGameTabModel> implements IGameTabPresenter {

    private int pageToken=1;

    private boolean requestAddData=false;

     public GameTabPresenter(IGameTabView tabView){
         attachView(tabView,new GameTabModel());
    }
    
    
    @Override
    public void loadData( String catid) {

        pageToken=1;
        mvpView.showLoadingView();
        L.d("loadData");
        Observable<GameListEntity> observable = mvpModel.loadData(pageToken,catid);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<GameListEntity>() {
            @Override
            public void onSuccess(GameListEntity entity) {
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
         L.d("addData");
         mvpView.showMoreLoadingView();
        Observable<GameListEntity> observable = mvpModel.loadData(pageToken,catid);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<GameListEntity>() {
            @Override
            public void onSuccess(GameListEntity entity) {

                if(entity.getData()==null||entity.getData().size()==0){
                    mvpView.showNoMore();
                    return;
                }

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
