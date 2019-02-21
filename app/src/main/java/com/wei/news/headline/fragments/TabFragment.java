package com.wei.news.headline.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.sdk.manager.CacheManager;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.Constant;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.headline.presenter.TabPresenter;
import com.wei.news.headline.adapter.NewsAdapter;
import com.wei.news.headline.ui.NewsDetailsActivity;
import com.wei.news.headline.view.ITabView;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.mvp.MvpFragment;
import com.wei.news.sdk.widget.FootView;
import com.wei.news.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class TabFragment extends MvpFragment<TabPresenter> implements ITabView, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener, FootView.OnFootViewErrorClickListener, AdapterView.OnItemClickListener, LoadStatusView.onReloadClickListener {

    @BindView(R.id.lv_tab)
    ListView listView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;



    private String title;

    private ArrayList<TypeListEntity.Data> newsList;

    private NewsAdapter tabAdapter;

    private FootView footView;
    private CacheManager cacheManager;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab;
    }



    @Override
    public void init() {
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        footView = new FootView(getContext());
        footView.hide();
        listView.addFooterView(footView);

        cacheManager = new CacheManager<TypeListEntity.Data>();
        addLoadView(R.id.rootview);
    }


    @Override
    public void initData() {
        L.d("TabFragment initData:"+getTitle());
        newsList =cacheManager.getCache(getContext(),getCatid());

        if(newsList.size()>0){
            showFootView();
        }

        if(canLoadData()){
            mvpPresenter.loadData(getCatid());
            setCanLoadData(false);
        }

        tabAdapter=new NewsAdapter(getContext(), newsList,R.layout.item_tab);
        listView.setAdapter(tabAdapter);

    }

    @Override
    public void lazyData() {
        mvpPresenter.loadData(getCatid());
        setCanLoadData(false);
    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
        footView.setOnFootViewErrorClickListener(this);
        getLoadStatusView().setonReloadClickListener(this);
    }

    public void setTitle(String title) {
        this.title = title;

    }
    public String getTitle() {
       return title;

    }

    public String getCatid(){
        return Constant.catidMap.get(title);
    }

    @Override
    protected TabPresenter createPresenter() {
        return new TabPresenter(this);
    }

    @Override
    public void loadData(TypeListEntity data) {
        getLoadStatusView().hideLoadStatus();
        if(data!=null){
            newsList.clear();
            newsList.addAll(data.getData());
            cacheManager.putCache(getContext(),getCatid(), newsList);
            tabAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void addData(TypeListEntity data) {
        if(data.getData()!=null){
            newsList.addAll(data.getData());
            tabAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoadingView() {
        swipeRefreshLayout.setRefreshing(true);
        if(newsList.size()<=0){
            getLoadStatusView().showLoading();
        }
    }

    @Override
    public void hideLoadingView() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showNoMore() {

    }

    @Override
    public void showReload() {
        if(newsList.size()<=0){
            getLoadStatusView().showReload();
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        ((MainActivity)getActivity()).addDisposable(disposable);
    }


    @Override
    public void showErrorMoreLoadView() {
        footView.showError();
    }

    @Override
    public void showMoreLoadingView() {
        footView.showLoading();
    }
    @Override
    public void showFootView() {
        footView.show();
    }



    @Override
    public void onRefresh() {
        mvpPresenter.loadData(getCatid());
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if(absListView.getLastVisiblePosition()==absListView.getCount()-3){
            showMoreLoadingView();
        }

        if((firstVisibleItem+visibleItemCount)==totalItemCount
                &&totalItemCount>2
                &&absListView.getLastVisiblePosition()==absListView.getCount()-1
                &&!footView.isLoadError()){
            mvpPresenter.addData(getCatid());
        }



    }

    public void OnFootViewClick() {
        mvpPresenter.addData(getCatid());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
         TypeListEntity.Data data= (TypeListEntity.Data) adapterView.getItemAtPosition(position);
        Bundle bundle=new Bundle();
        bundle.putString("url", data.getUrl());
        bundle.putString("title", data.getTitle());
        IntentManager.startActivity(getContext(), NewsDetailsActivity.class,bundle);

        data.setViewed(true);
        newsList.set(position,data);
        tabAdapter.notifyDataSetChanged();

    }

    @Override
    public void OnReloadClick() {
        swipeRefreshLayout.setRefreshing(true);
        mvpPresenter.loadData(getCatid());
        setCanLoadData(false);
    }
}
