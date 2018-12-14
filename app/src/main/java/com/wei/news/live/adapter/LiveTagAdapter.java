package com.wei.news.live.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.news.R;
import com.wei.news.live.entity.LiveTagDataEntity;
import com.wei.news.live.entity.LiveTagEntity;
import com.wei.news.sdk.adapter.CommonAdapter;
import com.wei.news.sdk.adapter.ViewHolder;

import java.util.List;

public class LiveTagAdapter extends CommonAdapter<LiveTagDataEntity> {


    public LiveTagAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setDatas(ViewHolder holder, Object object) {
       LiveTagDataEntity liveTagEntity= ((LiveTagDataEntity)object);

        ImageView iv_tag=holder.getView(R.id.iv_tag);
        TextView tv_tag=holder.getView(R.id.tv_tag);

        iv_tag.setImageResource(liveTagEntity.getDrawableId());
        tv_tag.setText(liveTagEntity.getTagName());
    }
}
