package com.wei.news.sdk.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wei.news.R;
import com.wei.news.utils.L;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class GlideManager {

   static RequestOptions defaultOptions = new RequestOptions()
            .placeholder(R.mipmap.image_load_error)//加载成功之前占位图
            .error(R.mipmap.image_load_error)//加载错误之后的错误图
//            .override(400,400)//指定图片的尺寸
            //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
            .fitCenter()
            //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
//            .centerCrop()
//            .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
//            .skipMemoryCache(true)//跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存所有版本的图像
//            .diskCacheStrategy(DiskCacheStrategy.NONE)//跳过磁盘缓存
//            .diskCacheStrategy(DiskCacheStrategy.DATA)//只缓存原来分辨率的图片
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE);//只缓存最终的图片


    static RequestOptions circleOptions = new RequestOptions()
            .fitCenter()
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存所有版本的图像


    public  static void loadImager(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(defaultOptions)
                .transition(DrawableTransitionOptions.withCrossFade(500) )
                .into(imageView);
    }
    public  static void loadImagerForSmallVideo(Context context, String url,RequestListener<Drawable> listener){

        Glide.with(context).load(url)
                .listener(listener)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);//加载原图大小;

    }
    public  static void loadImagerForBiliBili(Context context, String url, ImageView imageView){

        Glide.with(context).load(url)
                .apply(bitmapTransform(new RoundedCornersTransformation(20, 0,
                        RoundedCornersTransformation.CornerType.TOP)))
                .transition(DrawableTransitionOptions.withCrossFade(500) )
                .into(imageView);

    }
    public  static void loadImagerForGame(Context context, String url,RequestListener<Drawable> listener){

        Glide.with(context).load(url)
            .apply(bitmapTransform(new RoundedCornersTransformation(20, 15,
                    RoundedCornersTransformation.CornerType.ALL)))
            .transition(DrawableTransitionOptions.withCrossFade(500) )
            .listener(listener)
            .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);//加载原图大小;

    }
    public  static void loadCircleImager(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(circleOptions)
                .into(imageView);

    }




}
