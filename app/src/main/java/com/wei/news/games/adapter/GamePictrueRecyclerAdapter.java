package com.wei.news.games.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wei.news.R;
import com.wei.news.games.ui.GamePictureActivity;
import com.wei.news.sdk.manager.DimensionManager;
import com.wei.news.sdk.manager.GlideManager;
import com.wei.news.sdk.manager.IntentManager;
import com.wei.news.utils.L;

import java.util.ArrayList;
import java.util.List;

public class GamePictrueRecyclerAdapter extends RecyclerView.Adapter<GamePictrueRecyclerAdapter.PictureViewHolder> {

    private final ArrayList<String> mUrls;
    private final Context mContext;

    public GamePictrueRecyclerAdapter(Context context, ArrayList<String> imageUrls){
        this.mUrls=imageUrls;
        this.mContext=context;
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView=View.inflate(parent.getContext(),R.layout.item_game_picture,null);
        return new PictureViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final PictureViewHolder holder, final int position) {
        String URL=mUrls.get(position);
        GlideManager.loadImagerForGame(mContext, URL, new RequestListener<Drawable>() {
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
                                    DimensionManager.dip2px(mContext,300)
                                    ,DimensionManager.dip2px(mContext,185));
                }else{//竖向图片
                    layoutParams=new RelativeLayout.LayoutParams(
                            DimensionManager.dip2px(mContext,150)
                            ,DimensionManager.dip2px(mContext,250));
                }
                holder.iv_picture.setLayoutParams(layoutParams);
                holder.iv_picture.setBackground(resource);


                return false;
            }
        });

        holder.iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("picture_urls",mUrls);
                bundle.putInt("position",position);
                IntentManager.startActivity(mContext,GamePictureActivity.class,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    public static class PictureViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_picture;


        public PictureViewHolder(View itemView) {
            super(itemView);
            iv_picture = itemView.findViewById(R.id.iv_picture);
        }
    }
}
