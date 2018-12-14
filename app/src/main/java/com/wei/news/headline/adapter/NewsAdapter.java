package com.wei.news.headline.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wei.news.App;
import com.wei.news.R;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.sdk.adapter.CommonAdapter;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.adapter.ViewHolder;

import java.util.List;

public class NewsAdapter extends CommonAdapter {


    public NewsAdapter(Context context, List data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setDatas(ViewHolder holder, Object object) {
        TypeListEntity.Data data= (TypeListEntity.Data)object;

        View ll_moreImg_layout=holder.getView(R.id.ll_moreImg_layout);
        View ll_oneImg_layout=holder.getView(R.id.ll_oneImg_layout);
        TextView tv_moreImg_title=holder.getView(R.id.tv_moreImg_title);
        TextView tv_moreImg_author=holder.getView(R.id.tv_moreImg_author);
        TextView tv_moreImg_time=holder.getView(R.id.tv_moreImg_time);
        ImageView iv_moreImg_1=holder.getView(R.id.iv_moreImg_1);
        ImageView iv_moreImg_2=holder.getView(R.id.iv_moreImg_2);
        ImageView iv_moreImg_3=holder.getView(R.id.iv_moreImg_3);

        TextView tv_oneImg_title=holder.getView(R.id.tv_oneImg_title);
        TextView tv_oneImg_author=holder.getView(R.id.tv_oneImg_author);
        TextView tv_oneImg_time=holder.getView(R.id.tv_oneImg_time);
        ImageView iv_oneImg_1=holder.getView(R.id.iv_oneImg_1);


        List<String> imageUrls = data.getImageUrls();
        if(imageUrls!=null){
            int size = imageUrls.size();
             if(size>=3){
                showMoreLayout(ll_moreImg_layout,ll_oneImg_layout);
                GlideManager.loadImager(App.getContext(),imageUrls.get(0),iv_moreImg_1);
                GlideManager.loadImager(App.getContext(),imageUrls.get(1),iv_moreImg_2);
                GlideManager.loadImager(App.getContext(),imageUrls.get(2),iv_moreImg_3);
                tv_moreImg_title.setText(data.getTitle());
                tv_moreImg_author.setText(data.getPosterScreenName());
                tv_moreImg_time.setText(data.getPublishDateStr().replaceAll("T"," "));
            }else if(size>0&&size<3){
                showOneLayout(ll_moreImg_layout,ll_oneImg_layout);
                GlideManager.loadImager(App.getContext(),imageUrls.get(0),iv_oneImg_1);
                tv_oneImg_title.setText(data.getTitle());
                tv_oneImg_author.setText(data.getPosterScreenName());
                tv_oneImg_time.setText(data.getPublishDateStr().replaceAll("T"," "));
            }else{
                 showOneLayout(ll_moreImg_layout,ll_oneImg_layout);
                 tv_oneImg_title.setText(data.getTitle());
                 tv_oneImg_author.setText(data.getPosterScreenName());
                 tv_oneImg_time.setText(data.getPublishDateStr().replaceAll("T"," "));
                 iv_oneImg_1.setVisibility(View.GONE);
             }
        }else{
            showMoreLayout(ll_moreImg_layout,ll_oneImg_layout);
            iv_moreImg_1.setVisibility(View.GONE);
            iv_moreImg_2.setVisibility(View.GONE);
            iv_moreImg_3.setVisibility(View.GONE);
            tv_moreImg_title.setText(data.getTitle());
            tv_moreImg_author.setText(data.getPosterScreenName());
            tv_moreImg_time.setText(data.getPublishDateStr().replaceAll("T"," "));
        }

        if(data.isViewed()){
            tv_moreImg_title.setTextColor(Color.GRAY);
            tv_oneImg_title.setTextColor(Color.GRAY);
        }else{
            tv_moreImg_title.setTextColor(Color.BLACK);
            tv_oneImg_title.setTextColor(Color.BLACK);
        }

    }

    private void showMoreLayout(View moreLayout,View oneLayout){
        moreLayout.setVisibility(View.VISIBLE);
        oneLayout.setVisibility(View.GONE);
    }
    private void showOneLayout(View moreLayout,View oneLayout){
        moreLayout.setVisibility(View.GONE);
        oneLayout.setVisibility(View.VISIBLE);
    }
}
