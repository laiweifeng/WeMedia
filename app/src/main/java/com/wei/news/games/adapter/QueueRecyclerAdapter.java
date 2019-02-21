/*
 * Copyright (c) 2017 LingoChamp Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wei.news.games.adapter;

import android.app.job.JobInfo;
import android.content.Context;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.wei.news.R;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.games.utils.TaskCacheUtils;
import com.wei.news.sdk.manager.ApkManager;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.manager.okdownload.DownloadManager;
import com.wei.news.sdk.widget.FootView;
import com.wei.news.utils.L;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueueRecyclerAdapter
        extends RecyclerView.Adapter<QueueRecyclerAdapter.QueueViewHolder> {

    private final List<GameListEntity.Data> mData;
    private final Context mContext;
    private final DownloadManager downloadManager;
    public OnRecycleItemClickListener onRecycleItemListener;
    private OnDeleteButtonClickListener onDeleteButtonClickListener;
    private boolean editMode=false;

    private static final int NORMAL_VIEW = 0;
    private static final int FOOT_VIEW = 1;
    private boolean footViewEnable=true;
    private OnSlideToBottomListener onSlideToBottomListener;
    private OnBottomErrorClickListener onBottomErrorClickListener;


    public QueueRecyclerAdapter(Context context, List<GameListEntity.Data> tabListEntities,DownloadManager downloadController) {
        this.mData=tabListEntities;
        this.mContext=context;
        this.downloadManager=downloadController;

    }

    public void refreshData(List<GameListEntity.Data> data){
        this.mData.clear();;
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<GameListEntity.Data> data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void setEditMode(boolean editMode){
        this.editMode=editMode;
        notifyDataSetChanged();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setFootViewEnable(boolean enable){
        this.footViewEnable=enable;
    }

    public boolean isFootViewEnable(){
        return footViewEnable;
    }



    @Override
    public QueueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW) {
            return new QueueViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_game, parent, false),viewType);
        } else {
           FootView footView=new FootView(parent.getContext());
           return new QueueViewHolder(footView, viewType);
        }

    }

    @Override public void onBindViewHolder(final QueueViewHolder holder, final int position) {
        //footview的显示逻辑
        if (getItemViewType(position) == FOOT_VIEW) {
            L.d("onBindViewHolder FOOT_VIEW");

            if(isFootViewEnable()){
                if(mData.size()==0){
                    holder.footView.setVisibility(View.GONE);
                }else{
                    holder.footView.setVisibility(View.VISIBLE);
                    if(onSlideToBottomListener!=null){
                        onSlideToBottomListener.onBottom(holder.footView);
                    }
                }
            }else{
                holder.footView.setVisibility(View.GONE);
            }

            holder.footView.setOnFootViewErrorClickListener(new FootView.OnFootViewErrorClickListener() {
                @Override
                public void OnFootViewClick() {
                   if(onBottomErrorClickListener!=null){
                       onBottomErrorClickListener.onBottomErrorClcik();
                   }
                }
            });

            return;
        }
        final  GameListEntity.Data data= mData.get(position);

        holder.tv_title.setText(data.getTitle());
        holder.tv_filesize.setText(data.getFileOptions().get(0).getSizeM()+mContext.getString(R.string.mb));
        holder.tv_downloadCount.setText(
                mContext.getString(R.string.total_downloads)
                +formatDowncount(data.getDownloadCount()));
        holder.tv_subtitle.setText(data.getSubtitle());
        GlideManager.loadImager(mContext,data.getAvatarUrl(),holder.iv_avatar);

        if(isEditMode()){
            holder.tv_download.setVisibility(View.GONE);
            holder.tv_delete.setVisibility(View.VISIBLE);
        }else{
            holder.tv_download.setVisibility(View.VISIBLE);
            holder.tv_delete.setVisibility(View.GONE);
        }

        final String url=data.getFileOptions().get(0).getUrl();

        //初始化进入页面的下载进度缓存信息
        DownloadTask task = downloadManager.getTask(url);
        if(task!=null){
            BreakpointInfo info =StatusUtil.getCurrentInfo(task);
            if(info!=null){
                long totalOffset = info.getTotalOffset();
                long totalLength = info.getTotalLength();
                String curSize=totalOffset/1024/1024+"MB/"+totalLength/1024/1024+"MB";
                holder.tv_downloadSize.setText(curSize);
            }
        }

        refreshViewStatus(holder, url);

        //每次滑动刷新列表重新绑定holder
        downloadManager.bind(holder,url);
        holder.tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String started = downloadManager.getStatus(url);
                L.d("onClick started:"+started);
                switch (started){
                    case DownloadManager.NONE:
                        L.d("url: "+url);
                        downloadManager.addTask(url);
                        downloadManager.start(url);
                        refreshViewStatus(holder, url);
                        downloadManager.bind(holder,url);
                        TaskCacheUtils.saveTaskCache(data);

                        break;
                    case DownloadManager.START_TASK:
                    case DownloadManager.PROGRESS:
                    case DownloadManager.CONNECTED:
                    case DownloadManager.PENDING:
                        downloadManager.stop(url);
                        break;
                    case DownloadManager.PAUSE:
                    case DownloadManager.IDLE:
                    case DownloadManager.UNKNOWN:
                        downloadManager.start(url);
                        break;
                    case DownloadManager.COMPLETED:
                        //安装应用
                        String fileName=url.substring(url.lastIndexOf("/"),url.length());
                        ApkManager.installApk(mContext,new File(downloadManager.getCacheFile(),fileName));
                        break;
                    case DownloadManager.RETRY:
                    case DownloadManager.ERROR:
                        downloadManager.start(url);
                        break;
                }

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("itemView  onClick");
                if(onRecycleItemListener!=null){
                    onRecycleItemListener.onItemClick(position,isEditMode());
                }
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteButtonClickListener!=null){
                    onDeleteButtonClickListener.onDeleteButtonClick(position);
                }
            }
        });

    }



    @Override public int getItemCount() {
        return  mData.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return FOOT_VIEW;
        }
        return NORMAL_VIEW;
    }

    public static class QueueViewHolder extends RecyclerView.ViewHolder {

       public  TextView tv_title;
       public TextView statusTv;

       public  TextView tv_filesize;
       public TextView tv_downloadCount;
       public TextView tv_subtitle;
       public TextView tv_download;
       public ImageView iv_avatar;
       public TextView tv_downloadSize;
       public ProgressBar progressBar;

       public View ll_apkInfoView;
       public View ll_downloadView;

       public TextView tv_delete;
       public FootView footView;


       public QueueViewHolder(View itemView, int viewType) {
           super(itemView);
           if (viewType == NORMAL_VIEW) {
               tv_title = itemView.findViewById(R.id.tv_title);
               tv_filesize = itemView.findViewById(R.id.tv_filesize);
               tv_downloadCount = itemView.findViewById(R.id.tv_downloadCount);
               tv_subtitle = itemView.findViewById(R.id.tv_subtitle);
               tv_download = itemView.findViewById(R.id.tv_download);
               iv_avatar = itemView.findViewById(R.id.iv_avatar);
               tv_downloadSize = itemView.findViewById(R.id.tv_downloadSize);
               statusTv = itemView.findViewById(R.id.tv_state);
               progressBar = itemView.findViewById(R.id.progressBar);
               ll_apkInfoView = itemView.findViewById(R.id.ll_apkInfoView);
               ll_downloadView = itemView.findViewById(R.id.ll_downloadView);
               tv_delete = itemView.findViewById(R.id.tv_delete);

           } else if (viewType == FOOT_VIEW) {
               footView = (FootView) itemView;
           }

       }
   }

    private void refreshViewStatus(QueueViewHolder holder, String url) {
        if(downloadManager.getTask(url)!=null){
            startState(holder);
        }else{
            noneState(holder);
        }
    }

    private void startState(QueueRecyclerAdapter.QueueViewHolder holder) {
        holder.ll_apkInfoView.setVisibility(View.GONE);
        holder.tv_subtitle.setVisibility(View.GONE);
        holder.ll_downloadView.setVisibility(View.VISIBLE);
        holder. progressBar.setVisibility(View.VISIBLE);
    }
    private void noneState(QueueRecyclerAdapter.QueueViewHolder holder) {
        holder.ll_apkInfoView.setVisibility(View.VISIBLE);
        holder.tv_subtitle.setVisibility(View.VISIBLE);
        holder.ll_downloadView.setVisibility(View.GONE);
        holder.progressBar.setVisibility(View.GONE);
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

    public void setOnRecycleItemClickListener(OnRecycleItemClickListener l){
        this.onRecycleItemListener=l;
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(int position,boolean isEditMode);

    }
    public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener l){
        this.onDeleteButtonClickListener=l;
    }

    public interface OnDeleteButtonClickListener {
        void onDeleteButtonClick(int position);
    }

    public void setOnSlideToBottomListener(OnSlideToBottomListener l){
        this.onSlideToBottomListener=l;
    }

    public interface OnSlideToBottomListener{
        void onBottom(FootView footView );
    }
    public void setOnBottomErrorClickListener(OnBottomErrorClickListener l){
        this.onBottomErrorClickListener=l;
    }

    public interface OnBottomErrorClickListener{
        void onBottomErrorClcik( );
    }





}