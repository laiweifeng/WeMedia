package com.wei.news.sdk.manager;

import android.content.Context;

import com.wei.news.sdk.manager.SharePreferenceManager;

import java.util.ArrayList;

public class CacheManager <T>{

    public  ArrayList<T> getCache(Context context,String key){
        ArrayList<T> data=
                (ArrayList<T>) SharePreferenceManager.getObject(context,key);

        if(data==null){
            data=new ArrayList<>();
        }

        return data;
    }


    public void putCache(Context context,String key,ArrayList<T> data){
        SharePreferenceManager.putObject(context,key,data);
    }


}
