package com.wei.news.games.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.games.adapter.QueueRecyclerAdapter;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.presenter.GameTabPresenter;
import com.wei.news.games.ui.GameDescActivity;
import com.wei.news.games.view.IGameTabView;
import com.wei.news.sdk.manager.CacheManager;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.TipManager;
import com.wei.news.sdk.manager.okdownload.DownloadManager;
import com.wei.news.sdk.mvp.MvpFragment;
import com.wei.news.sdk.widget.FootView;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.utils.Constant;
import com.wei.news.utils.L;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class GameTabFragment extends MvpFragment<GameTabPresenter>
        implements IGameTabView, SwipeRefreshLayout.OnRefreshListener,
        QueueRecyclerAdapter.OnRecycleItemClickListener,
        QueueRecyclerAdapter.OnSlideToBottomListener,
        LoadStatusView.onReloadClickListener,
        QueueRecyclerAdapter.OnBottomErrorClickListener {

    @BindView(R.id.lv_tab)
    RecyclerView listView;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;



    private String title;

    private ArrayList<GameListEntity.Data> mData;

    QueueRecyclerAdapter queueRecyclerAdapter;



    private FootView footView;
    private CacheManager cacheManager;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_tab_game;
    }



    @Override
    public void init() {
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);


        cacheManager = new CacheManager<GameListEntity.Data>();

        addLoadView(R.id.rootview);
    }


    @Override
    public void initData() {
        mData =cacheManager.getCache(getContext(),getCatid());

        if(canLoadData()){
            mvpPresenter.loadData(getCatid());
            setCanLoadData(false);
        }

        DownloadManager downloadManager=DownloadManager.getInstance();
        queueRecyclerAdapter=new QueueRecyclerAdapter(getActivity(), mData,downloadManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(queueRecyclerAdapter);


    }

    @Override
    public void lazyData() {
        mvpPresenter.loadData(getCatid());
        setCanLoadData(false);
    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);

        queueRecyclerAdapter.setOnRecycleItemClickListener(this);

        queueRecyclerAdapter.setOnSlideToBottomListener(this);

        getLoadStatusView().setonReloadClickListener(this);

        queueRecyclerAdapter.setOnBottomErrorClickListener(this);

    }

    @Override
    public void onBottom(FootView footView) {
        this.footView=footView;
        if(!footView.isNoMore()){
            mvpPresenter.addData(getCatid());
        }
    }

    public void setTitle(String title) {
        this.title = title;

    }
    public String getTitle() {
       return title;

    }

    public String getCatid(){
        return Constant.gameCatidMap.get(title);
    }

    @Override
    protected GameTabPresenter createPresenter() {
        return new GameTabPresenter(this);
    }

    @Override
    public void loadData(GameListEntity data) {
        getLoadStatusView().hideLoadStatus();
        if(data!=null){
            queueRecyclerAdapter.refreshData(data.getData());
            cacheManager.putCache(getContext(),getCatid(),data.getData());
            return;
        }
    }

    @Override
    public void addData(GameListEntity data) {
        queueRecyclerAdapter.addData(data.getData());
    }

    @Override
    public void showLoadingView() {
        swipeRefreshLayout.setRefreshing(true);
        if(mData.size()<=0){
            getLoadStatusView().showLoading();
        }
    }

    @Override
    public void hideLoadingView() {
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showNoMore() {
        if(footView!=null){
            footView.showNoMore();
        }
    }

    @Override
    public void showReload() {
        if(mData.size()<=0){
            getLoadStatusView().showReload();
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {
        ((MainActivity)getActivity()).addDisposable(disposable);
    }

    @Override
    public void showErrorMoreLoadView() {
        if(footView!=null){
            footView.showError();
        }
    }

    @Override
    public void showMoreLoadingView() {
        if(footView!=null){
            footView.showLoading();
        }
    }
    @Override
    public void showFootView() {
        if(footView!=null){
            footView.show();
        }
    }

    @Override
    public void onRefresh() {
        mvpPresenter.loadData(getCatid());
    }

    @Override
    public void onResume() {
        super.onResume();
        L.d("GameTabFragment onResume");
        queueRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(int position, boolean isEditMode) {
        GameListEntity.Data data=mData.get(position);
        Bundle bundle=new Bundle();
        bundle.putSerializable("game_data",data);
        IntentManager.startActivity(getContext(),GameDescActivity.class,bundle);
    }


    @Override
    public void OnReloadClick() {
        swipeRefreshLayout.setRefreshing(true);
        mvpPresenter.loadData(getCatid());
        setCanLoadData(false);
    }

    @Override
    public void onBottomErrorClcik() {
        mvpPresenter.addData(getCatid());
    }
}
