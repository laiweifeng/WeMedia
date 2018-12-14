package com.wei.news.games.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.wei.news.R;
import com.wei.news.games.adapter.GamePictrueRecyclerAdapter;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.utils.TaskCacheUtils;
import com.wei.news.sdk.base.BaseActivity;
import com.wei.news.sdk.manager.ApkManager;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.manager.okdownload.DownloadManager;
import com.wei.news.sdk.manager.okdownload.ProgressUtil;
import com.wei.news.sdk.manager.okdownload.QueueListener;
import com.wei.news.sdk.manager.okdownload.TagUtil;
import com.wei.news.utils.L;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class GameDescActivity extends BaseActivity implements View.OnClickListener, QueueListener.OnDownloadListener {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_downloadCount)
    TextView tv_downloadCount;
    @BindView(R.id.tv_filesize)
    TextView tv_filesize;
    @BindView(R.id.tv_rating)
    TextView tv_rating;
    @BindView(R.id.tv_description)
    TextView tv_description;


    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;

    @BindView(R.id.id_flowlayout)
    TagFlowLayout flowLayout;
    @BindView(R.id.btn_download)
    Button btn_download;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private DownloadManager mDownloadManager;
    private GameListEntity.Data mData;
    private DownloadTask task;
    private String url;
    private QueueListener taskListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_game_desc;
    }

    @Override
    public void init() {
        Bundle bundle=getIntent().getBundleExtra("bundle");
        mData = (GameListEntity.Data) bundle.getSerializable("game_data");

        mDownloadManager = DownloadManager.getInstance();

        ArrayList<GameListEntity.Data> saveTaskCache = TaskCacheUtils.getSaveTaskCache();
        url = mData.getFileOptions().get(0).getUrl();

        for (GameListEntity.Data data : saveTaskCache) {
            if(url.equals(data.getFileOptions().get(0).getUrl())){
                task = mDownloadManager.getTask(url);
                if(task!=null){
                    taskListener = mDownloadManager.getListener();
                    if(taskListener!=null){
                        taskListener.setOnDownloadListener(this);
                    }
                }
                resetInfo(task,btn_download,progressBar);

                return;

            }
        }


    }

    @Override
    public void initData() {

        GamePictrueRecyclerAdapter recyclerAdapter=new GamePictrueRecyclerAdapter(getContext(), mData.getImageUrls());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper. HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        setTitle(mData.getTitle());
        tv_title.setText(mData.getTitle());
        tv_downloadCount.setText(getString(R.string.total_downloads)
                +formatDowncount(mData.getDownloadCount()));
        tv_filesize.setText(mData.getFileOptions().get(0).getSizeM()+getString(R.string.mb));
        tv_rating.setText(mData.getRating());
        tv_description.setText(mData.getDescription());
        GlideManager.loadImager(getContext(), mData.getAvatarUrl(),iv_avatar);

        flowLayout.setAdapter(new TagAdapter<String>(mData.getTags())
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView textView=new TextView(getContext());
                textView.setBackgroundResource(R.color.blue);
                textView.setTextColor(Color.WHITE);
                textView.setPadding(5,5,5,5);
                textView.setText(s);
                return textView;
            }
        });

    }

    @Override
    public void setListener() {
        btn_download.setOnClickListener(this);
    }

    private String formatDowncount(int count){
        if(count>=1000&&count<10000){
            return count/1000+"千";
        }else if(count>=10000&&count<100000000){
            return count/10000+"万";
        }else{
            return  count/100000000+"亿";
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_download:
                final String started = mDownloadManager.getStatus(url);
                L.d("onClick started:"+started);
                switch (started){
                    case DownloadManager.NONE:
                        L.d("url: "+url);
                        mDownloadManager.addTask(url);
                        mDownloadManager.start(url);
                        TaskCacheUtils.saveTaskCache(mData);

                        QueueListener listener=mDownloadManager.getListener();
                        if(listener!=null){
                            task= mDownloadManager.getTask(url);
                            listener.setOnDownloadListener(this);
                        }

                        break;
                    case DownloadManager.START_TASK:
                    case DownloadManager.PROGRESS:
                    case DownloadManager.CONNECTED:
                        mDownloadManager.stop(url);
                        break;
                    case DownloadManager.PAUSE:
                    case DownloadManager.IDLE:
                        mDownloadManager.start(url);
                        break;
                    case DownloadManager.COMPLETED:
                        //安装应用
                        String fileName=url.substring(url.lastIndexOf("/"),url.length());
                        ApkManager.installApk(getContext(),new File(DownloadManager.getCacheFile(),fileName));
                        break;
                    case DownloadManager.RETRY:
                    case DownloadManager.ERROR:
                        mDownloadManager.start(url);
                        break;
                }

                break;
        }
    }

    public void resetInfo(DownloadTask task, Button downloadButton, ProgressBar progressBar) {

        // process references
        final String status = TagUtil.getStatus(task);
        if (status != null) {
            L.d("   status:"+status);
            //   started
            downloadButton.setText(getStringId2Status(status));

            if (status.equals(DownloadManager.COMPLETED)) {
                downloadButton.setText(R.string.install);
                progressBar.setProgress(progressBar.getMax());
            } else {
                final long total = TagUtil.getTotal(task);
                if (total == 0) {
                    progressBar.setProgress(0);
                } else {
                    ProgressUtil.calcProgressToViewAndMark(progressBar,
                            TagUtil.getOffset(task), total, false);
                }
            }
        } else {
            // non-started
            final StatusUtil.Status statusOnStore = StatusUtil.getStatus(task);
            TagUtil.saveStatus(task, statusOnStore.toString());
            L.d("   non-started:"+statusOnStore.toString());
            if (statusOnStore == StatusUtil.Status.COMPLETED) {
                downloadButton.setText(R.string.install);
                progressBar.setProgress(progressBar.getMax());
            } else {
                switch (statusOnStore) {
                    case IDLE:
                        downloadButton.setText(R.string.continues);
                        break;
                    case PENDING:
                        downloadButton.setText(R.string.state_pending);
                        break;
                    case RUNNING:
                        downloadButton.setText(R.string.state_running);
                        break;
                    default:
                        downloadButton.setText(R.string.state_unknown);
                }

                if (statusOnStore == StatusUtil.Status.UNKNOWN) {
                    progressBar.setProgress(0);
                } else {
                    final BreakpointInfo info = StatusUtil.getCurrentInfo(task);
                    if (info != null) {
                        TagUtil.saveTotal(task, info.getTotalLength());
                        TagUtil.saveOffset(task, info.getTotalOffset());
                        ProgressUtil.calcProgressToViewAndMark(progressBar,
                                info.getTotalOffset(), info.getTotalLength(), false);
                    } else {
                        progressBar.setProgress(0);
                    }
                }
            }
        }
    }
    public int getStringId2Status(String status){
        switch (status){
            case DownloadManager.NONE:
                return R.string.download;
            case DownloadManager.COMPLETED:
                return R.string.completed;
            case DownloadManager.CONNECTED:
                return R.string.connected;
            case DownloadManager.ERROR:
                return R.string.error;
            case DownloadManager.PAUSE:
                return R.string.paused;
            case DownloadManager.PROGRESS:
                return R.string.downloading;
            case DownloadManager.RETRY:
                return R.string.retry;
            case DownloadManager.START_TASK:
                return R.string.taskstart;
            case DownloadManager.PENDING:
                return R.string.wait;
        }
        return R.string.paused;
    }

    @Override
    public void taskStart(DownloadTask task) {
        if(task!=null&&this.task.getId()==task.getId()){
            btn_download.setText(R.string.pause);
        }
    }

    @Override
    public void retry(DownloadTask task) {
        if(task!=null&&this.task.getId()==task.getId()){
            btn_download.setText(R.string.retry);

        }
    }

    @Override
    public void connected(DownloadTask task) {
        if(task!=null&&this.task.getId()==task.getId()){
            btn_download.setText(R.string.connected);
        }
    }

    @Override
    public void progress(DownloadTask task, long currentOffset, long totalLength) {
        if(task!=null&&this.task.getId()==task.getId()){
            String curSize=currentOffset/1024/1024+"MB/"+totalLength/1024/1024+"MB";
            L.d("curSize:"+curSize);
            btn_download.setText(curSize);
            if(progressBar.getTag()==null){
                ProgressUtil.calcProgressToViewAndMark(progressBar,
                        currentOffset, totalLength, false);
            }
            ProgressUtil.updateProgressToViewWithMark(progressBar, currentOffset, false);
        }
    }

    @Override
    public void taskEnd(DownloadTask task, EndCause cause, Exception realCause) {
        if(task!=null&&this.task.getId()==task.getId()){
            final String status = cause.toString();

            L.d("taskEnd:"+status);
            if(cause==EndCause.CANCELED){
                btn_download.setText(R.string.continues);
                TagUtil.saveStatus(task, DownloadManager.PAUSE);
            }else if (cause == EndCause.COMPLETED) {
                progressBar.setProgress(progressBar.getMax());
                btn_download.setText(R.string.install);
                TagUtil.saveStatus(task, DownloadManager.COMPLETED);
            }else if(cause == EndCause.ERROR){
                L.d("realCause:"+realCause.getMessage());
                btn_download.setText(R.string.error);
                TagUtil.saveStatus(task, DownloadManager.ERROR);
            }else if(cause == EndCause.SAME_TASK_BUSY){
                btn_download.setText(R.string.wait);
                TagUtil.saveStatus(task, DownloadManager.PENDING);
            }

        }
    }
}
