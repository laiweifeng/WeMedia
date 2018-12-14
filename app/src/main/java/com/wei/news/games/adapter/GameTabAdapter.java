package com.wei.news.games.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wei.news.App;
import com.wei.news.MainActivity;
import com.wei.news.R;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.adapter.CommonAdapter;
import com.wei.news.sdk.adapter.ViewHolder;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.utils.L;

import org.w3c.dom.Text;

import java.util.List;

public class GameTabAdapter extends CommonAdapter {

    View ll_apkInfoView;
    View ll_downloadView;
    ProgressBar progressBar;
    TextView tv_subtitle;

    GameListEntity.Data data;

    public GameTabAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setDatas(ViewHolder holder, Object object) {
        data = (GameListEntity.Data)object;
        TextView tv_title=holder.getView(R.id.tv_title);
        TextView tv_filesize=holder.getView(R.id.tv_filesize);
        TextView tv_downloadCount=holder.getView(R.id.tv_downloadCount);
        tv_subtitle=holder.getView(R.id.tv_subtitle);
        TextView tv_download=holder.getView(R.id.tv_download);
        ImageView iv_avatar=holder.getView(R.id.iv_avatar);

        TextView tv_downloadSize=holder.getView(R.id.tv_downloadSize);
        TextView tv_state=holder.getView(R.id.tv_state);
        progressBar=holder.getView(R.id.progressBar);

        ll_apkInfoView=holder.getView(R.id.ll_apkInfoView);
        ll_downloadView=holder.getView(R.id.ll_downloadView);

        downloadState(false);

        tv_title.setText(data.getTitle());
        tv_filesize.setText(data.getFileOptions().get(0).getSizeM()+"MB");
        tv_downloadCount.setText("总下载量："+formatDowncount(data.getDownloadCount()));
        tv_subtitle.setText(data.getSubtitle());
        GlideManager.loadImager(getContext(),data.getAvatarUrl(),iv_avatar);

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                L.d("download");
                downloadState(true);


            }
        });

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

    private void downloadState(boolean state){
        if(state){
            ll_apkInfoView.setVisibility(View.GONE);
            tv_subtitle.setVisibility(View.GONE);
            ll_downloadView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }else{
            ll_apkInfoView.setVisibility(View.VISIBLE);
            tv_subtitle.setVisibility(View.VISIBLE);
            ll_downloadView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
