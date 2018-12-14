package com.wei.news.video.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.wei.news.R;
import com.wei.news.sdk.base.BaseFragment;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.utils.L;
import com.wei.news.video.entity.VideoEntity;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class SmallVideoFragment extends BaseFragment implements View.OnClickListener {

    private  boolean isParentHide;
    private final VideoEntity.Data mData;
    @BindView(R.id.videoView)
    PLVideoView videoView;


    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.iv_player)
    ImageView iv_player;

    @BindView(R.id.iv_cover)
    ImageView iv_cover;

    @BindView(R.id.progressBar)
    ProgressBar loadingView;


    private String videoPath;

    public SmallVideoFragment(VideoEntity.Data data) {
        this.mData=data;
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_small_video;
    }

    @Override
    public void init() {
        videoPath = mData.getVideoUrls().get(0);
        //強制平鋪
        videoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        hidePlayerView();

    }

    @Override
    public void lazyData() {
        setDataSource();
    }

    private void setDataSource() {
        L.v("path: "+videoPath);
        showProgressBar();
        hidePlayerView();
        videoView.setVideoPath(videoPath);
    }

    @Override
    public void initData() {
        if(canLoadData()){
            setDataSource();
        }

        tv_desc.setText(mData.getDescription());
        L.d("CoverUrl:"+mData.getCoverUrl());
        GlideManager.loadImagerForSmallVideo(getContext(), mData.getCoverUrl(), new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                L.d("e:"+e.getMessage());
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
               iv_cover.setBackground(resource);
                return false;
            }
        });
        showCoverView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        L.d("SmallVideoFragment setUserVisibleHint:"+isVisibleToUser);
        if(!isVisibleToUser){
            if(videoView!=null&&videoView.isPlaying()){
                videoView.pause();
            }
        }
    }

    @Override
    public void setListener() {

        iv_cover.setOnClickListener(this);
        iv_player.setOnClickListener(this);
        videoView.setOnClickListener(this);

        videoView.setOnPreparedListener(new PLOnPreparedListener() {
            @Override
            public void onPrepared(int i) {
                if(getUserVisibleHint()&&!isParentHide){
                    L.d("准备完毕，Fragment可见可以播放："+videoPath);
                    videoView.start();
                    hideProgressBar();
                    hideCoverView();
                    showPlayerView();
                }else{
                    videoView.pause();
                }
            }
        });
        videoView.setOnCompletionListener(new PLOnCompletionListener() {
            @Override
            public void onCompletion() {
                videoView.start();// 循环播放
            }
        });

    }

    public  void showPlayerView(){
//        iv_player.setVisibility(ViewPager.VISIBLE);
//        iv_player.setAlpha(1);

    }
    public  void hidePlayerView(){
//        iv_player.setVisibility(ViewPager.GONE);
//        iv_player.setAlpha(0);
    }

    private void showProgressBar(){
        loadingView.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        loadingView.setVisibility(View.GONE);
    }

    private  void showCoverView(){
//        iv_cover.setAlpha(1);
        iv_cover.setVisibility(View.VISIBLE);

    }
    private  void hideCoverView(){
//        iv_cover.setAlpha(0);
        iv_cover.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View v) {
        L.d("SmallVideoFragment onClick:"+v.getId());
        switch (v.getId()){
            case R.id.videoView:
            case R.id.iv_cover:
            case R.id.iv_player:
                playerOrStop(true);
                break;
        }
    }

    public void playerOrStop(boolean player) {
        if(!player){
            videoView.pause();
            showPlayerView();
            showCoverView();
        }else {
            videoView.start();
            hidePlayerView();
            hideCoverView();
            hideProgressBar();
        }
    }

    public  void setParentHide(boolean parentHide) {
        isParentHide = parentHide;
    }

    @Override
    public void onPause() {
        super.onPause();
        L.d("SmallVideoFragment onPause");
        videoView.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        L.d(mData.getDescription()+"  SmallVideoFragment onStart   "  +"isFragmentVisibleToUser:"+isFragmentVisibleToUser());
        if(!isParentHide){
            videoView.start();
            hideProgressBar();
           showPlayerView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.d("SmallVideoFragment onDestroy");
        videoView.stopPlayback();
    }

}