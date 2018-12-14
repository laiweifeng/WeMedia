package com.wei.news.live.ui;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.wei.news.R;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.presenter.LivePresenter;
import com.wei.news.live.view.ILiveView;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.DimensionManager;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.mvp.MvpActivity;
import com.wei.news.utils.L;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import butterknife.BindView;

public class LiveActivity extends MvpActivity<LivePresenter> implements ILiveView, View.OnClickListener, View.OnTouchListener, PLOnPreparedListener, PLOnCompletionListener {

    private static final int HIDE_VIDEO_CONTROLLER = 0;
    private static final long HIDE_CONTROLLER_TIME = 5000;
    @BindView(R.id.VideoView)
    PLVideoView plVideoView;

    @BindView(R.id.ib_back)
    ImageButton ib_back;

    @BindView(R.id.ib_playPause)
    ImageButton ib_playPause;

    @BindView(R.id.ib_full)
    ImageButton ib_full;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_time)
    TextView tv_time;

    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.tv_id)
    TextView tv_id;

    @BindView(R.id.tv_fansCount)
    TextView tv_fansCount;
    @BindView(R.id.tv_description)
    TextView tv_description;

    @BindView(R.id.iv_head)
    ImageView iv_head;

    @BindView(R.id.rl_videoRootView)
    View rl_videoRootView;
    @BindView(R.id.rl_videoTools)
    View rl_videoTools;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowLayout;


    private String videoPath;

    LiveListEntity.Data mData;

    @Override
    protected LivePresenter createPresenter() {
        return new LivePresenter(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_live;
    }

    @Override
    public void init() {

        //強制平鋪
        plVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);

    }

    @Override
    public void initData() {


        mData= (LiveListEntity.Data) getIntent().getBundleExtra(IntentManager.KEY_NAME).getSerializable("LiveData");
        List<String> videoUrls = mData.getVideoUrls();
        videoPath = videoUrls.get(0);
        L.d("videoPath:"+videoPath);
        tv_title.setText(mData.getTitle());
        tv_fansCount.setText("粉丝："+mData.getFansCount());
        tv_id.setText("房间号："+mData.getId());
        tv_username.setText(mData.getUserScreenName());
        tv_description.setText(mData.getDescription());
        plVideoView.setVideoPath(videoPath);
        GlideManager.loadCircleImager(this,mData.getAvatarUrl(),iv_head);

        flowLayout.setAdapter(new TagAdapter<String>(mData.getTags())
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView textView=new TextView(LiveActivity.this);
                textView.setBackgroundResource(R.drawable.biligame_btn_white_26);
                textView.setText(s);
                return textView;
            }
        });

    }

    @Override
    public void setListener() {
        ib_back.setOnClickListener(this);
        ib_playPause.setOnClickListener(this);
        ib_full.setOnClickListener(this);
        rl_videoTools.setOnClickListener(this);
        plVideoView.setOnTouchListener(this);
        plVideoView.setOnPreparedListener(this);
        plVideoView.setOnCompletionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        plVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        plVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(HIDE_VIDEO_CONTROLLER);
        plVideoView.stopPlayback();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_playPause:
                playPause();
                break;
            case R.id.ib_full:
                switchFull();
                break;
            case R.id.rl_videoTools:
                hideVideoController();
                break;



        }
    }

    private void playPause() {
        if(plVideoView.isPlaying()){
            puase();
        }else{
            start();
        }
    }

    private void puase(){
        plVideoView.pause();
        puaseIcon();
    }



    private void start(){
        plVideoView.start();
        playIcon();
    }

    private void puaseIcon() {
        ib_playPause.setBackgroundResource(R.drawable.ic_browser_pause_white_22dp);
    }

    private void playIcon() {
        ib_playPause.setBackgroundResource(R.drawable.ic_browser_play_white_22dp);
    }

    ////获取屏幕方向判断是否横屏
    private boolean isOrientation(){
        Configuration mConfiguration = this.getResources().getConfiguration(); //获取设置的配置信息
        int ori = mConfiguration.orientation;
        return ori==mConfiguration.ORIENTATION_LANDSCAPE?true:false;
    }

    private  void showVideoController(){
        handler.sendEmptyMessageDelayed(HIDE_VIDEO_CONTROLLER,HIDE_CONTROLLER_TIME);
        rl_videoTools.setVisibility(View.VISIBLE);
    }

    private  void hideVideoController(){
        handler.removeMessages(HIDE_VIDEO_CONTROLLER);
        rl_videoTools.setVisibility(View.GONE);
    }

    //横竖屏切换
    private void switchFull() {
        if (isOrientation()) {
            portrait();
        } else  {
            orientation();
        }
    }

    private String long2Time(long ms){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(ms);
        return hms;
    }

    private void orientation() {
        LinearLayout.LayoutParams params=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        rl_videoRootView.setLayoutParams(params);
        //强制为横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ib_full.setBackgroundResource(R.drawable.ic_player_portrait_ver_fullscreen);
    }

    private void portrait() {
        LinearLayout.LayoutParams params=
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT
                        , DimensionManager.dip2px(this,240));
        rl_videoRootView.setLayoutParams(params);
        //强制为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //显示状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ib_full.setBackgroundResource(R.drawable.xplayer_ic_portrait_fullscreen);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.VideoView:
            showVideoController();
                return true;
        }
        return false;
    }

    @Override
    public void onPrepared(int i) {
        //视频准备完毕开始播放
        puaseIcon();
        handler.sendEmptyMessageDelayed(HIDE_VIDEO_CONTROLLER,HIDE_CONTROLLER_TIME);
    }

    @Override
    public void onCompletion() {
        playIcon();
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HIDE_VIDEO_CONTROLLER:
                    hideVideoController();
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                if(isOrientation()){
                    portrait();
                    return true;
                }
        }
        return super.onKeyDown(keyCode, event);
    }
}
