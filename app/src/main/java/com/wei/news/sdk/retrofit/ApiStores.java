package com.wei.news.sdk.retrofit;


import com.wei.news.games.entity.GameListEntity;
import com.wei.news.headline.entity.TypeListEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.video.entity.VideoEntity;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiStores {


    //http://api01.idataapi.cn:8000/news/tencent?pageToken=1&catid=news_news_top&apikey=K1Wx0A1hhp5BKiMivD6ygDHziFKXfBYGJ48A8K03iV48Y64c0kukcAzF0mMcEDJ2
    @GET("/news/tencent")
    Observable<TypeListEntity> getTencentTypeListData(@Query("pageToken") int pageToken, @Query("catid") String catid, @Query("apikey") String apikey);
    @GET("/news/tencent")
    Observable<TypeListEntity> getTencentNewsList2key(@Query("pageToken") int pageToken, @Query("kw") String kw, @Query("apikey") String apikey);

    //http://api01.idataapi.cn:8000/video/xiaokaxiu?type=1&video_catid=1&sound_sort=2&video_sort=2&pageToken=1&
    // apikey=K1Wx0A1hhp5BKiMivD6ygDHziFKXfBYGJ48A8K03iV48Y64c0kukcAzF0mMcEDJ2
    @GET("/video/xiaokaxiu")
    Observable<VideoEntity> getVideoListData(@Query("pageToken") int pageToken,
                                             @Query("type") int type,
                                             @Query("video_catid") int video_catid,
                                             @Query("sound_sort") int sound_sort,
                                             @Query("video_sort") int video_sort,
                                             @Query("apikey") String apikey);


    //?kw=lol&pageToken=1
    @GET("/liveroom/bilibili")
    Observable<LiveListEntity> getLiveListData(@Query("pageToken") int pageToken,
                                               @Query("kw") String kw,
                                               @Query("apikey") String apikey);
    //??sort=0&catid=91:75964:奇幻&pageToken=0
    @GET("/mobileapp/mobile360")
    Observable<GameListEntity> getGamesListData(
                                                @Query("catid") String catid,
                                                @Query("pageToken") int pageToken,
                                                @Query("apikey") String apikey);


    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
