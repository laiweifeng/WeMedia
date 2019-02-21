package com.wei.news.video.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Window;

import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.sdk.mvp.MvpFragment;
import com.wei.news.utils.L;
import com.wei.news.video.adapter.VideFragmentAdapter;
import com.wei.news.video.entity.VideoEntity;
import com.wei.news.video.presenter.VideoPresenter;
import com.wei.news.video.view.IVideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class VideoFragment extends MvpFragment<VideoPresenter> implements IVideoView, SwipeRefreshLayout.OnRefreshListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;


    private List<Fragment> fragments;
    private VideFragmentAdapter videFragmentAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_vieo;
    }

    @Override
    public void init() {
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

    }


    @Override
    public void initData() {

        mvpPresenter.loadData();

        fragments = new ArrayList<>();
        videFragmentAdapter = new VideFragmentAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(videFragmentAdapter);
    }

    @Override
    public void lazyData() {
    }

    @Override
    public void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        viewPager.addOnPageChangeListener(this );

    }

    @Override
    protected VideoPresenter createPresenter() {
        return new VideoPresenter(this);
    }

    @Override
    public void loadData(VideoEntity videoEntity) {
        fragments.clear();
        List<VideoEntity.Data> datas = videoEntity.getData();
        if(datas!=null){
            for (VideoEntity.Data data :datas){
                fragments.add(new SmallVideoFragment(data));
            }
        }
       videFragmentAdapter.notifyDataSetChanged();
       swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void addData(VideoEntity videoEntity) {

    }


    @Override
    public void onRefresh() {
        mvpPresenter.loadData();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        L.d("VideoFragment hidden:"+hidden);
        if(videFragmentAdapter.getCount()>0){
            //防止小视频在没有显示的情况下播放视频
            SmallVideoFragment centerSmallVideoFragment= (SmallVideoFragment) videFragmentAdapter.getItem(viewPager.getCurrentItem());
            SmallVideoFragment preSmallVideoFragment= (SmallVideoFragment) videFragmentAdapter.getItem(viewPager.getCurrentItem()==0?0:viewPager.getCurrentItem()-1);
            SmallVideoFragment nextSmallVideoFragment= (SmallVideoFragment) videFragmentAdapter.getItem(viewPager.getCurrentItem()+1);
            centerSmallVideoFragment.playerOrStop(!hidden);
            centerSmallVideoFragment.setParentHide(hidden);
            if(preSmallVideoFragment!=null){
                preSmallVideoFragment.setParentHide(hidden);
            }
            if(nextSmallVideoFragment!=null){
                nextSmallVideoFragment.setParentHide(hidden);
            }
        }
    }

    @Override
    public void showLoadingView() {
        swipeRefreshLayout.setRefreshing(true);
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

    }

    @Override
    public void addDisposable(Disposable disposable) {
        ((MainActivity)getActivity()).addDisposable(disposable);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        L.d("onPageScrolled  position:"+position+"   positionOffset:"+positionOffset+"   positionOffsetPixels:"+positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        if(viewPager.getChildCount()==position+1){
            L.d("lastPosition");


        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        L.d("onPageScrollStateChanged  state:"+state);
    }
}
