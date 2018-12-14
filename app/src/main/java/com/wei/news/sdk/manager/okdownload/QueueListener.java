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

package com.wei.news.sdk.manager.okdownload;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.ProgressBar;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
import com.wei.news.App;
import com.wei.news.R;
import com.wei.news.games.adapter.QueueRecyclerAdapter;
import com.wei.news.utils.L;

public class QueueListener extends DownloadListener1 {
    private static final String TAG = "QueueListener";



    private SparseArray<QueueRecyclerAdapter.QueueViewHolder> holderMap = new SparseArray<>();

    private OnDownloadListener downloadListener;


    void bind(DownloadTask task, QueueRecyclerAdapter.QueueViewHolder holder) {
        Log.i(TAG, "bind " + task.getId() + " with " + holder);
        // replace.
        final int size = holderMap.size();
        for (int i = 0; i < size; i++) {
            if (holderMap.valueAt(i) == holder) {
                holderMap.removeAt(i);
                break;
            }
        }
        holderMap.put(task.getId(), holder);
    }

    void resetInfo(DownloadTask task, QueueRecyclerAdapter.QueueViewHolder holder) {

        // process references
        final String status = TagUtil.getStatus(task);
        if (status != null) {
            L.d(holder.tv_title.getText().toString()+"   status:"+status);
            //   started
            holder.statusTv.setText(getStringId2Status(status));
            //设置下载按钮状态
            setButtonStatus(holder, status);

            if (status.equals(DownloadManager.COMPLETED)) {
                holder.tv_download.setText(R.string.install);
                holder.progressBar.setProgress(holder.progressBar.getMax());
            } else {
                final long total = TagUtil.getTotal(task);
                if (total == 0) {
                    holder.progressBar.setProgress(0);
                } else {
                    ProgressUtil.calcProgressToViewAndMark(holder.progressBar,
                            TagUtil.getOffset(task), total, false);
                }
            }
        } else {
            // non-started
            final StatusUtil.Status statusOnStore = StatusUtil.getStatus(task);
            TagUtil.saveStatus(task, statusOnStore.toString());
            L.d(holder.tv_title.getText().toString()+"   non-started:"+statusOnStore.toString());
            if (statusOnStore == StatusUtil.Status.COMPLETED) {
                holder.statusTv.setText(R.string.completed);
                holder.tv_download.setText(R.string.install);
                holder.progressBar.setProgress(holder.progressBar.getMax());
            } else {
                switch (statusOnStore) {
                    case IDLE:
                        holder.statusTv.setText(R.string.paused);
                        holder.tv_download.setText(R.string.continues);
                        break;
                    case PENDING:
                        holder.statusTv.setText(R.string.state_pending);
                        holder.tv_download.setText(R.string.state_pending);
                        break;
                    case RUNNING:
                        holder.statusTv.setText(R.string.state_running);
                        break;
                    default:
                        holder.statusTv.setText(R.string.state_unknown);
                }

                if (statusOnStore == StatusUtil.Status.UNKNOWN) {
                    holder.progressBar.setProgress(0);
                } else {
                    final BreakpointInfo info = StatusUtil.getCurrentInfo(task);
                    if (info != null) {
                        TagUtil.saveTotal(task, info.getTotalLength());
                        TagUtil.saveOffset(task, info.getTotalOffset());
                        ProgressUtil.calcProgressToViewAndMark(holder.progressBar,
                                info.getTotalOffset(), info.getTotalLength(), false);
                    } else {
                        holder.progressBar.setProgress(0);
                    }
                }
            }
        }
    }



    public void clearBoundHolder() {
        holderMap.clear();
    }

    @Override
    public void taskStart(@NonNull DownloadTask task,
                          @NonNull Listener1Assist.Listener1Model model) {
        TagUtil.saveStatus(task, DownloadManager.START_TASK);

        if(downloadListener!=null){
            downloadListener.taskStart(task);
        }

        final QueueRecyclerAdapter.QueueViewHolder holder = holderMap.get(task.getId());
        L.d("taskStart:");
        if (holder == null) return;

        holder.statusTv.setText(R.string.taskstart);
        holder.tv_download.setText(R.string.pause);

    }

    @Override public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
        TagUtil.saveStatus(task,DownloadManager. RETRY);

        if(downloadListener!=null){
            downloadListener.retry(task);
        }

        L.d("retry:");
        final QueueRecyclerAdapter.QueueViewHolder holder = holderMap.get(task.getId());
        if (holder == null) return;

        holder.statusTv.setText(R.string.retry);
    }

    @Override
    public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset,
                          long totalLength) {
        TagUtil.saveStatus(task, DownloadManager.CONNECTED);
        TagUtil.saveOffset(task, currentOffset);
        TagUtil.saveTotal(task, totalLength);

        if(downloadListener!=null){
            downloadListener.connected(task);
        }

        L.d("connected:");
        final QueueRecyclerAdapter.QueueViewHolder holder = holderMap.get(task.getId());
        if (holder == null) return;

        holder.statusTv.setText(R.string.connected);

        ProgressUtil.calcProgressToViewAndMark(holder.progressBar, currentOffset, totalLength,
                false);
    }

    @Override
    public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
        TagUtil.saveStatus(task, DownloadManager.PROGRESS);
        TagUtil.saveOffset(task, currentOffset);

        if(downloadListener!=null){
            downloadListener.progress(task,currentOffset,totalLength);
        }

        final QueueRecyclerAdapter.QueueViewHolder holder = holderMap.get(task.getId());
        if (holder == null) return;
        holder.statusTv.setText(R.string.downloading);
        String curSize=currentOffset/1024/1024+"MB/"+totalLength/1024/1024+"MB";
        holder.tv_downloadSize.setText(curSize);


        Log.i(TAG, "progress " + task.getId() + " with " + holder);
        ProgressUtil.updateProgressToViewWithMark(holder.progressBar, currentOffset, false);
    }



    @Override
    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause,
                        @Nullable Exception realCause,
                        @NonNull Listener1Assist.Listener1Model model) {
        final String status = cause.toString();

        if(downloadListener!=null){
            downloadListener.taskEnd(task,cause,realCause);
        }

        L.d("taskEnd:"+status);
        final QueueRecyclerAdapter.QueueViewHolder holder = holderMap.get(task.getId());
        if (holder == null) return;

        if(cause==EndCause.CANCELED){
            holder.tv_download.setText(R.string.continues);
            holder.statusTv.setText(R.string.paused);
            TagUtil.saveStatus(task, DownloadManager.PAUSE);
        }else if (cause == EndCause.COMPLETED) {
            holder.progressBar.setProgress(holder.progressBar.getMax());
            holder.statusTv.setText(R.string.completed);
            holder.tv_download.setText(R.string.install);
            TagUtil.saveStatus(task, DownloadManager.COMPLETED);
        }else if(cause == EndCause.ERROR){
            L.d("realCause:"+realCause.getMessage());
            holder.statusTv.setText(R.string.error);
            holder.tv_download.setText(R.string.retry);
            TagUtil.saveStatus(task, DownloadManager.ERROR);
        }else if(cause == EndCause.SAME_TASK_BUSY){
            holder.statusTv.setText(R.string.wait);
            holder.tv_download.setText(R.string.pause);
            TagUtil.saveStatus(task, DownloadManager.PENDING);
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

    private void setButtonStatus(QueueRecyclerAdapter.QueueViewHolder holder, String status) {
        switch (status){
            case DownloadManager.COMPLETED:
                holder.tv_download.setText(R.string.completed);
                break;
            case DownloadManager.ERROR:
            case DownloadManager.RETRY:
                holder.tv_download.setText(R.string.retry);
                break;
            case DownloadManager.NONE:
                holder.tv_download.setText(R.string.download);
                break;
            case DownloadManager.START_TASK:
            case DownloadManager.CONNECTED:
            case DownloadManager.PROGRESS:
            case DownloadManager.PENDING:
                holder.tv_download.setText(R.string.pause);
                break;

            case DownloadManager.PAUSE:
                holder.tv_download.setText(R.string.continues);
                break;
        }
    }

    public void setOnDownloadListener(OnDownloadListener l){
        this.downloadListener=l;
    }

    public interface OnDownloadListener{
        void taskStart(DownloadTask task);
        void retry(DownloadTask task);
        void connected(DownloadTask task);
        void progress(DownloadTask task, long currentOffset, long totalLength);
        void taskEnd(DownloadTask task,EndCause cause,Exception realCause);
    }

}