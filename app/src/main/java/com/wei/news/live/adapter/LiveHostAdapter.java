package com.wei.news.live.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.sdk.adapter.CommonAdapter;
import com.wei.news.sdk.adapter.ViewHolder;
import com.wei.news.sdk.manager.GlideManager;

import java.util.List;

public class LiveHostAdapter extends CommonAdapter<LiveListEntity.Data> {
    public LiveHostAdapter(Context context, List<LiveListEntity.Data> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setDatas(ViewHolder holder, Object object) {
        LiveListEntity.Data data= (LiveListEntity.Data) object;
        ImageView iv_cover=holder.getView(R.id.iv_cover);
        TextView tv_title=holder.getView(R.id.tv_title);
        TextView tv_user_name=holder.getView(R.id.tv_user_name);
        TextView tv_online_count=holder.getView(R.id.tv_online_count);
        TextView tv_cat_path_key=holder.getView(R.id.tv_cat_path_key);

        GlideManager.loadImagerForBiliBili(getContext(),data.getCoverUrl(),iv_cover);

        tv_title.setText(data.getTitle());
        tv_user_name.setText(data.getUserScreenName());
        tv_online_count.setText(data.getOnlineCount()+"");
        tv_cat_path_key.setText(data.getCatPathKey());

    }
}
