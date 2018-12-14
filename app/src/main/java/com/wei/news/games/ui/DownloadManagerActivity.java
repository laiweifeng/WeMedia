package com.wei.news.games.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.liulishuo.okdownload.DownloadTask;
import com.wei.news.R;
import com.wei.news.games.adapter.QueueRecyclerAdapter;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.utils.TaskCacheUtils;
import com.wei.news.sdk.base.BaseActivity;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.sdk.manager.okdownload.DownloadManager;
import com.wei.news.sdk.widget.MessageDialog;

import java.util.ArrayList;

import butterknife.BindView;

public class DownloadManagerActivity extends BaseActivity implements View.OnClickListener, QueueRecyclerAdapter.OnRecycleItemClickListener, QueueRecyclerAdapter.OnDeleteButtonClickListener {

    @BindView(R.id.root_noTask)
    View root_noTask;
    @BindView(R.id.root_edit)
    View root_edit;

    @BindView(R.id.lv_apps)
    RecyclerView listView;

    @BindView(R.id.tv_edit)
    TextView tv_edit;


    QueueRecyclerAdapter queueRecyclerAdapter;
    DownloadManager downloadManager;
    private ArrayList<GameListEntity.Data> mData;

    @Override
    public int getLayoutId() {
        return R.layout.activity_download;
    }

    @Override
    public void init() {
        setTitle(R.string.app_manager);
    }

    @Override
    public void initData() {

        downloadManager=DownloadManager.getInstance();
        mData = TaskCacheUtils.getSaveTaskCache();

        queueRecyclerAdapter = new QueueRecyclerAdapter(getContext(), mData, downloadManager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(OrientationHelper. VERTICAL);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(queueRecyclerAdapter);

        noTask(mData);
        queueRecyclerAdapter.setFootViewEnable(false);

    }

    private void noTask(ArrayList<GameListEntity.Data> saveTaskCache) {
        if(saveTaskCache.size()==0){
            root_noTask.setVisibility(View.VISIBLE);
            root_edit.setVisibility(View.GONE);
        }
    }

    @Override
    public void setListener() {
        tv_edit.setOnClickListener(this);

        queueRecyclerAdapter.setOnRecycleItemClickListener(this);
        queueRecyclerAdapter.setOnDeleteButtonClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit:
                queueRecyclerAdapter.setEditMode(queueRecyclerAdapter.isEditMode()?false:true);
                tv_edit.setText(queueRecyclerAdapter.isEditMode()?R.string.complete:R.string.edit);
                break;
        }
    }

    @Override
    public void onItemClick(int position, boolean isEditMode) {
        if(!isEditMode){
            GameListEntity.Data data=mData.get(position);
            Bundle bundle=new Bundle();
            bundle.putSerializable("game_data",data);
            IntentManager.startActivity(getContext(),GameDescActivity.class,bundle);
        }
    }

    @Override
    public void onDeleteButtonClick(final int position) {
        MessageDialog messageDialog=new MessageDialog(getContext());
        messageDialog.show();
        messageDialog.setTitle("删除任务");
        messageDialog.setMeassage("确定删除您选中任务吗？\n本地文件也一同删除！");
        messageDialog.setOnButtonClickListener(new MessageDialog.OnButtonClickListener() {
            @Override
            public void onOk(View v) {
               String url= TaskCacheUtils.deleteTaskCache(position);
                downloadManager.stop(url);
                downloadManager.refreshCache();
                ArrayList<GameListEntity.Data> saveTaskCache= TaskCacheUtils.getSaveTaskCache();
                queueRecyclerAdapter.refreshData(saveTaskCache);

                noTask(saveTaskCache);
            }
        });
    }

}
