package com.wei.news.games.utils;

import com.wei.news.App;
import com.wei.news.games.entity.GameListEntity;
import com.wei.news.sdk.manager.SharePreferenceManager;
import com.wei.news.sdk.manager.okdownload.DownloadManager;
import com.wei.news.utils.L;

import java.io.File;
import java.util.ArrayList;

public class TaskCacheUtils {

    public static void saveTaskCache(GameListEntity.Data data){
        ArrayList<GameListEntity.Data> list= getSaveTaskCache();
        list.add(data);
        SharePreferenceManager.putObject(App.getContext(),"app_datas",list);
    }

    public static ArrayList<GameListEntity.Data> getSaveTaskCache(){
        ArrayList<GameListEntity.Data> list= (ArrayList<GameListEntity.Data>) SharePreferenceManager.getObject(App.getContext(),"app_datas");
        if(list==null){
            L.d("list==null:");
            list=new ArrayList<>();
        }
        return  list;
    }

    public static String deleteTaskCache(int deletePosition){
        ArrayList<GameListEntity.Data> list= getSaveTaskCache();
        if(list.size()>0){
            L.d("deletePosition："+deletePosition);
            String url=list.get(deletePosition).getFileOptions().get(0).getUrl();
            String apkName=url.substring(url.lastIndexOf("/"),url.length());
            File file=new File(DownloadManager.getCacheFile(),apkName);
            if(file.exists()){
                L.d("删除的文件："+apkName);
                file.delete();
            }
            boolean is=list.remove(list.get(deletePosition));
            L.d("是否删除成功："+is);
            SharePreferenceManager.putObject(App.getContext(),"app_datas",list);
            return url;
        }

        return "";
    }

}
