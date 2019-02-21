package com.wei.news.live.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.wei.news.R;
import com.wei.news.live.adapter.LiveHostAdapter;
import com.wei.news.live.adapter.LiveTagAdapter;
import com.wei.news.live.entity.BannerEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.entity.LiveTagDataEntity;
import com.wei.news.live.entity.LiveTagEntity;
import com.wei.news.live.presenter.LiveChannelPresenter;
import com.wei.news.live.ui.LiveActivity;
import com.wei.news.live.ui.LiveListActivity;
import com.wei.news.live.view.ILiveChannelView;
import com.wei.news.sdk.manager.CacheManager;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.mvp.MvpFragment;
import com.wei.news.sdk.widget.GridViewWithHeaderAndFooter;
import com.wei.news.sdk.widget.LoadStatusView;
import com.wei.news.sdk.widget.SearchBoxView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;
import io.reactivex.disposables.Disposable;

public class LiveFragment extends MvpFragment<LiveChannelPresenter> implements ILiveChannelView, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, LoadStatusView.onReloadClickListener, View.OnClickListener {

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.searchview)
    SearchBoxView searchview;

    BGABanner bgaBanner;

    GridView gv_tags;

    @BindView(R.id.gv_live_list)
    GridViewWithHeaderAndFooter gv_live_list;


    ArrayList<LiveListEntity.Data> liveListDataList;
    private LiveHostAdapter liveHostAdapter;
    private View headerView;

    private final String HOT_LIVE_TAG="王者荣耀";
    private CacheManager cacheManager;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_live;
    }

    @Override
    protected LiveChannelPresenter createPresenter() {
        return new LiveChannelPresenter(this);
    }

    @Override
    public void init() {
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        headerView = View.inflate(getContext(), R.layout.live_fragment_header,null);
        gv_tags=  headerView.findViewById(R.id.gv_tags);
        bgaBanner= headerView.findViewById(R.id.banner_main_zoomStack);
        searchview.setIcon(R.drawable.ic_bilibili);

        cacheManager = new CacheManager<LiveListEntity.Data>();

        addLoadView(R.id.rootview);
    }


    @Override
    public void initData() {
        liveListDataList=cacheManager.getCache(getContext(),HOT_LIVE_TAG);

        gv_live_list.setVisibility(View.INVISIBLE);
        gv_live_list.addHeaderView(headerView);
        mvpPresenter.loadBannerData();
        mvpPresenter.loadTagData();
        mvpPresenter.loadLiveListData(HOT_LIVE_TAG);

        liveHostAdapter = new LiveHostAdapter(getContext(),liveListDataList, R.layout.item_host_live);
        gv_live_list.setAdapter(liveHostAdapter);

        if(liveListDataList.size()>0){
            gv_live_list.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void lazyData() {

    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        gv_live_list.setOnItemClickListener(this);
        gv_tags.setOnItemClickListener(this);
        getLoadStatusView().setonReloadClickListener(this);
        searchview.setOnClickListener(this);
    }

    public void loadBannerData(BannerEntity bannerEntity) {

        bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideManager.loadImager(getActivity(),model,itemView);
            }
        });

        bgaBanner.setData(bannerEntity.getUrlList(),bannerEntity.getDescList());
    }

    @Override
    public void loadTagData(LiveTagEntity liveTagEntity) {
        List<LiveTagDataEntity> dataList=liveTagEntity.getData();
        LiveTagAdapter liveTagAdapter=new LiveTagAdapter(getContext(),dataList,R.layout.item_live_tag);
        gv_tags.setAdapter(liveTagAdapter);
    }

    @Override
    public void loadLiveListData(List<LiveListEntity.Data> liveListDataList) {
        getLoadStatusView().hideLoadStatus();
        this.liveListDataList.clear();
        this.liveListDataList.addAll(liveListDataList);
        cacheManager.putCache(getContext(),HOT_LIVE_TAG,this.liveListDataList);
        liveHostAdapter.notifyDataSetChanged();
        gv_live_list.setVisibility(View.VISIBLE);

    }


    @Override
    public void showLoadingView() {
        swipeRefreshLayout.setRefreshing(true);
        if(liveListDataList.size()<=0){
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
        if(liveListDataList.size()<=0){
            getLoadStatusView().showReload();
        }
    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void onRefresh() {
        mvpPresenter.loadLiveListData(HOT_LIVE_TAG);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.gv_live_list){
            LiveListEntity.Data data= (LiveListEntity.Data) parent.getItemAtPosition(position+2);
            Bundle bundle=new Bundle();
            bundle.putSerializable("LiveData",data);
            IntentManager.startActivity(getContext(), LiveActivity.class,bundle);
        }else if(parent.getId()==R.id.gv_tags){
            LiveTagDataEntity tagDataEntity= (LiveTagDataEntity) parent.getItemAtPosition(position);
            Bundle bundle=new Bundle();
            bundle.putSerializable("tag",tagDataEntity.getTagName());
            IntentManager.startActivity(getContext(), LiveListActivity.class,bundle);

        }

    }

    @Override
    public void OnReloadClick() {
        swipeRefreshLayout.setRefreshing(true);
        mvpPresenter.loadLiveListData(HOT_LIVE_TAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchview:
                IntentManager.startActivity(getContext(), LiveListActivity.class,null);
                break;
        }
    }
}
