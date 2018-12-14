package com.wei.news.sdk.manager.okdownload;

import android.content.Context;
import android.view.View;

import com.liulishuo.okdownload.DownloadTask;
import com.wei.news.App;
import com.wei.news.games.adapter.QueueRecyclerAdapter;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.utils.TaskCacheUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DownloadManager {


    public final static String NONE="none";
    public final static String START_TASK="START_TASK";
    public final static String RETRY="RETRY";
    public final static String COMPLETED="COMPLETED";
    public final static String CONNECTED="CONNECTED";
    public final static String PROGRESS="PROGRESS";
    public final static String PAUSE ="PAUSE";
    public final static String ERROR="ERROR";
    public final static String PENDING="PENDING";
    public final static String IDLE="IDLE";
    public final static String UNKNOWN="UNKNOWN";

    private final QueueListener listener = new QueueListener();

    static File cacheFile =new File(getParentFile(App.getContext())+"/DownloadApk");

    static DownloadManager downloadManager=new DownloadManager();

    HashMap<String,DownloadTask> taskHashMap=new HashMap<>();

    public static DownloadManager getInstance(){
        return downloadManager;
    }

    private DownloadManager(){
        refreshCache();
    }

    public void refreshCache(){
        taskHashMap.clear();
        ArrayList<GameListEntity.Data> taskData = TaskCacheUtils.getSaveTaskCache();
        for (GameListEntity.Data data : taskData) {
            String url=data.getFileOptions().get(0).getUrl();
            DownloadTask task = buildTask(url);
            taskHashMap.put(url,task);
        }
    }

    public DownloadTask buildTask(String url) {
        String fileName=url.substring(url.lastIndexOf("/"),url.length());
        return new DownloadTask.Builder(url, cacheFile)
                .setFilename(fileName)
                // the minimal interval millisecond for callback progress
                .setMinIntervalMillisCallbackProcess(16)
                // ignore the same task has already completed in the past.
                .setPassIfAlreadyCompleted(false)
                .build();
    }

    public void addTask(String url){
        DownloadTask task = buildTask(url);
        taskHashMap.put(url,task);
    }


    public DownloadTask getTask(String url){
        return taskHashMap.get(url);
    }

    public void start(String url){
        DownloadTask task = getTask(url);
        if(task!=null){
            task.enqueue(listener);
        }
    }

    public void stop(String url) {
        DownloadTask task = getTask(url);
        if(task!=null){
            task.cancel();
        }
    }

    public String getStatus(String url){
        DownloadTask task = getTask(url);
        if(task!=null){
           return TagUtil.getStatus(task);
        }else{
            return NONE;
        }
    }


    public void bind(final QueueRecyclerAdapter.QueueViewHolder holder, String url) {
        final DownloadTask task = getTask(url);
        if (task!=null) {
            listener.bind(task, holder);
            listener.resetInfo(task, holder);
        }
    }

    public static File getParentFile( Context context) {
        final File externalSaveDir = context.getExternalCacheDir();
        if (externalSaveDir == null) {
            return context.getCacheDir();
        } else {
            return externalSaveDir;
        }
    }

    public static File getCacheFile(){
        return cacheFile;
    }

    public QueueListener getListener() {
        return listener;
    }





}
