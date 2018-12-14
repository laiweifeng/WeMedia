package com.wei.news.games.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wei.news.R;
import com.wei.news.sdk.base.BaseFragment;
import com.wei.news.sdk.manager.DimensionManager;
import com.wei.news.sdk.manager.GlideManager;

import butterknife.BindView;

public class PictureFragment extends BaseFragment {


    @BindView(R.id.iv_picture)
    ImageView iv_picture;

    private String url;

    @Override
    public int getLayoutId() {
        return R.layout.activity_picture;
    }

    @Override
    public void init() {

    }

    @Override
    public void lazyData() {

    }

    @Override
    public void initData() {
        GlideManager.loadImagerForGame(getContext(), url, new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                BitmapDrawable bd = (BitmapDrawable) resource;
                Bitmap bitmap = bd.getBitmap();
//                L.d("width:  "+bitmap.getWidth()+"  height: "+bitmap.getHeight());
                int width=bitmap.getWidth();
                int height=bitmap.getHeight();
                RelativeLayout.LayoutParams layoutParams=null;
                if(width>height){//横向图片
                    layoutParams=new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT
                            ,DimensionManager.dip2px(getContext(),250));
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                }else{//竖向图片
                    layoutParams=new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT
                            ,RelativeLayout.LayoutParams.MATCH_PARENT);
                }
                iv_picture.setLayoutParams(layoutParams);
                iv_picture.setBackground(resource);


                return false;
            }
        });
    }

    @Override
    public void setListener() {

    }

    public void setUrl(String url){
        this.url=url;
    }
}
