package com.wei.news.headline.presenter;

import com.wei.news.R;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.headline.model.INewsSearchModel;
import com.wei.news.headline.model.ITabModel;
import com.wei.news.headline.model.NewsSearchModel;
import com.wei.news.headline.model.TabModel;
import com.wei.news.headline.view.INewsSearchView;
import com.wei.news.headline.view.ITabView;
import com.wei.news.sdk.base.BasePresenter;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.sdk.rxjava.ApiCallback;
import com.wei.news.utils.L;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class NewsSearchPresenter extends BasePresenter<INewsSearchView,INewsSearchModel> implements INewsSearchPresenter {

    private int pageToken=1;

    private boolean requestAddData=false;

     public NewsSearchPresenter(INewsSearchView view){
         attachView(view,new NewsSearchModel());
    }
    
    
    @Override
    public void loadData( String kw) {

        pageToken=1;
        mvpView.showLoadingView();
        L.d(kw+"   loadData");
        Observable<TypeListEntity> observable = mvpModel.loadData(pageToken,kw);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<TypeListEntity>() {
            @Override
            public void onSuccess(TypeListEntity entity) {
                if(entity.getData()==null||entity.getData().size()==0){
                    TipManager.showTip(R.string.no_search_data);
                    return;
                }

                mvpView.loadData(entity);
                mvpView.showFootView();
                mvpView.hideLoadingView();
            }

            @Override
            public void onFailure(String msg) {
                mvpView.showReload();
            }

            @Override
            public void onFinish() {
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
         L.d(kw+"  addData");
         mvpView.showMoreLoadingView();
        Observable<TypeListEntity> observable = mvpModel.loadData(pageToken,kw);
        RetrofitCreateHelper.getInstance().onSubscribe(observable, new ApiCallback<TypeListEntity>() {
            @Override
            public void onSuccess(TypeListEntity entity) {
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
