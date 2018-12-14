package com.wei.news.utils;

import com.wei.news.App;
import com.wei.news.R;
import com.wei.news.utils.L;
import com.wei.news.sdk.manager.SharePreferenceManager;
import com.wenhuaijun.easytagdragview.bean.SimpleTitleTip;
import com.wenhuaijun.easytagdragview.bean.Tip;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TagsManager {

    static String[] dragTips= App.getContext().getResources().getStringArray(R.array.home_tag);
    static String[] add_tag= App.getContext().getResources().getStringArray(R.array.add_tag);
    static String[] game_tag= App.getContext().getResources().getStringArray(R.array.game_tag);
    static String[] app_tag= App.getContext().getResources().getStringArray(R.array.app_tag);

    public static List<Tip> getNewsDragTips(){
        List<Tip> result = new ArrayList<>();

       List<Tip> tipList= (List<Tip>) SharePreferenceManager.getObject(App.getContext(),"news_tags");
        if(tipList!=null){
            return tipList;
        }


        for(int i=0;i<dragTips.length;i++){
            String temp =dragTips[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i);
//            if (i==0){
//                tip.setIsSelected(true);
//            }else{
//                tip.setIsSelected(false);
//            }
            result.add(tip);
        }
        return result;
    }
    public static List<Tip> getNewsAddTips(){
        List<Tip> result = new ArrayList<>();

        List<Tip> tipList= (List<Tip>) SharePreferenceManager.getObject(App.getContext(),"news_tags");
        if(tipList!=null){
            List<String> allTags = new ArrayList<>();
            allTags.addAll(Arrays.asList(dragTips));
            allTags.addAll(Arrays.asList(add_tag));

            List<Integer> idList=new ArrayList<>();


            List<String> allTip = new ArrayList<>();
            for (int i=0;i<tipList.size();i++) {
                SimpleTitleTip simpleTitleTip = (SimpleTitleTip) tipList.get(i);
                idList.add(simpleTitleTip.getId());
                allTip.add(simpleTitleTip.getTip());
            }

            for (String tag : allTags) {
                if(allTip.contains(tag)){
                    continue;
                }
                SimpleTitleTip tip = new SimpleTitleTip();
                tip.setTip(tag);
                tip.setId(getRandomNum(idList));
                result.add(tip);
            }


            return result;
        }


        for(int i=0;i<add_tag.length;i++){
            String temp =add_tag[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i+add_tag.length);
            result.add(tip);
        }
        return result;
    }


    private static int getRandomNum(List<Integer> integerList){
        Random rand = new Random();
        int number= rand.nextInt(2000 );
        if (integerList.contains(number)){
            getRandomNum(integerList);
        }
        return  number;
    }


    public static List<Tip> getGamesTags(){
        List<Tip> result = new ArrayList<>();
        for(int i=0;i<game_tag.length;i++){
            String temp =game_tag[i];
            SimpleTitleTip tip = new SimpleTitleTip();
            tip.setTip(temp);
            tip.setId(i);
            result.add(tip);
        }
        return result;
    }

}
