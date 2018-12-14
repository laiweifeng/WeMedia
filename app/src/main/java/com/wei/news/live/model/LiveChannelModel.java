package com.wei.news.live.model;

import com.wei.news.live.entity.BannerEntity;
import com.wei.news.live.entity.LiveListEntity;
import com.wei.news.live.entity.LiveTagDataEntity;
import com.wei.news.live.entity.LiveTagEntity;
import com.wei.news.live.model.ILiveChannelModel;
import com.wei.news.sdk.retrofit.RetrofitCreateHelper;
import com.wei.news.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class LiveChannelModel implements ILiveChannelModel {
    @Override
    public Observable<BannerEntity> loadBannerData() {
        return Observable.create(new ObservableOnSubscribe<BannerEntity>() {
            @Override
            public void subscribe(ObservableEmitter<BannerEntity> emitter) throws Exception {
                BannerEntity bannerEntity=new BannerEntity();
                List<String> imageDescs=new ArrayList<>();
                List<String> imageUrls=new ArrayList<>();
                imageDescs.add(0,"《第五人格》哔哩哔哩直播预选赛赛程");
                imageDescs.add(0,"谁能成为暴雪明日之星？校园解说招募大赛海选名单公布！");

                imageUrls.add(0,"https://i0.hdslb.com/bfs/vc/70349ea9ea5530608abd95268d3e92ec58c14e27.jpg");
                imageUrls.add(1,"https://i0.hdslb.com/bfs/vc/a65a1f9d2c783cab4e91940e6a2edc83e6a74af3.jpg");
                bannerEntity.setDescList(imageDescs);
                bannerEntity.setUrlList(imageUrls);
                emitter.onNext(bannerEntity);
            }
        });
    }

    @Override
    public Observable<LiveTagEntity> loadTagData() {
        return Observable.create(new ObservableOnSubscribe<LiveTagEntity>() {
            @Override
            public void subscribe(ObservableEmitter<LiveTagEntity> emitter) throws Exception {
                LiveTagEntity liveTagDataEntities=new LiveTagEntity();
                List<LiveTagDataEntity> tagDataEntities=new ArrayList<>();
                LiveTagDataEntity data;
                for (int i=0;i<Constant.tagDrawableId.size();i++){
                    data =new LiveTagDataEntity();
                    data.setDrawableId(Constant.tagDrawableId.get(i));
                    data.setTagName(Constant.tagTagName.get(i));
                    tagDataEntities.add(data);
                }
                liveTagDataEntities.setData(tagDataEntities);
                emitter.onNext(liveTagDataEntities);
            }
        });
    }

    @Override
    public Observable<LiveListEntity> loadLiveListData(int pageToken,String kw ) {
        return RetrofitCreateHelper.getInstance().createApi().
                getLiveListData(pageToken,kw,Constant.APIKEY);
    }
}
