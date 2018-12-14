package com.wei.news.utils;

import com.wei.news.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Constant {




    //IdataApi官方key
    // 网站：www.idataapi.cn

    // key自己上去注册账户密码，并添加以下接口：
    //                      1. 哔哩哔哩直播
    //                      2.腾讯新闻
    //                      3.小咖秀
    //                      4.360手机助手
    //      这次良心提供，没有余额了就没法用了会报 500异常
    public static final String APIKEY="K1Wx0A1hhp5BKiMivD6ygDHziFKXfBYGJ48A8K03iV48Y64c0kukcAzF0mMcEDJ2";
    //腾讯新闻接口参数详情浏览 http://www.idataapi.cn/product/detail/90
    public static final String BASE_URL="http://api01.idataapi.cn:8000/";


    //小咖秀的接口参数详情浏览 http://www.idataapi.cn/product/test/605
    public static final int TYPE=1;
    public static final int VIDEO_CATID=1;
    public static final int SOUND_SORT=2;
    public static final int VIDEO_SORT=2;


    //直播tag
    public final static ArrayList<Integer> tagDrawableId=new ArrayList();
    public final static ArrayList<String> tagTagName=new ArrayList();

    static {
        tagDrawableId.add( R.drawable.yxlm);
        tagDrawableId.add( R.drawable.jdqs);
        tagDrawableId.add( R.drawable.gwlr);
        tagDrawableId.add( R.drawable.wzry);
        tagDrawableId.add( R.drawable.ic_category_t28);
        tagDrawableId.add( R.drawable.ic_category_t34);
        tagDrawableId.add( R.drawable.ic_category_t37);
        tagDrawableId.add( R.drawable.ic_category_t20);
    }
    static {
        tagTagName.add("英雄联盟");
        tagTagName.add("绝地求生");
        tagTagName.add("怪物猎人:世界");
        tagTagName.add("王者荣耀");
        tagTagName.add("唱见电台");
        tagTagName.add("游戏");
        tagTagName.add("手游");
        tagTagName.add("娱乐");
    }

    /**
     * 游戏首页的tab
     */
    public static HashMap<String,String> gameCatidMap=new HashMap<>();

    static {
        gameCatidMap.put("动作","90:75989:动作");
        gameCatidMap.put("角色扮演","90:75982:角色扮演");
        gameCatidMap.put("射击","90:75998:射击");
        gameCatidMap.put("武侠","91:75950:武侠");
        gameCatidMap.put("奇幻","91:75964:奇幻");
        gameCatidMap.put("恐怖","91:75968:恐怖");
        gameCatidMap.put("欧美魔幻","92:75972:欧美魔幻");
        gameCatidMap.put("军事战争","91:75953:军事战争");
        gameCatidMap.put("篮球","91:75966:篮球");
        gameCatidMap.put("消除","90:75994:消除");
        gameCatidMap.put("像素","92:75978:像素");
        gameCatidMap.put("二次元","91:75945:二次元");
        gameCatidMap.put("塔防","90:75984:塔防");
        gameCatidMap.put("僵尸","91:75967:僵尸");
        gameCatidMap.put("跑酷","90:75991:跑酷");
        gameCatidMap.put("音乐","90:76000:音乐");
        gameCatidMap.put("MOBA","90:75995:MOBA");
    }

    /**
     * 新闻首页的tab
     */
    public static HashMap<String,String> catidMap=new HashMap<>();

    static {
        catidMap.put("要闻","news_news_top");
        catidMap.put("财经","news_news_finance");
        catidMap.put("娱乐","news_news_ent");
        catidMap.put("体育","news_news_sports");
        catidMap.put("科技","news_news_top");
        catidMap.put("社会","news_news_ssh");
        catidMap.put("军事","news_news_mil");
        catidMap.put("时尚","news_news_lad");
        catidMap.put("纪录片","news_news_doco");
        catidMap.put("国际","news_news_world");
        catidMap.put("文化","news_news_cul");
        catidMap.put("房产","news_news_house");
        catidMap.put("图片","news_video_main");
        catidMap.put("佛学","news_news_fx");
        catidMap.put("汽车","news_news_auto");
        catidMap.put("游戏","news_news_game");
        catidMap.put("股票","news_news_istock");
        catidMap.put("动漫","news_news_ac");
        catidMap.put("理财","news_news_lic");
        catidMap.put("政务","news_news_msh");
        catidMap.put("家居","news_news_jiaju");
        catidMap.put("NBA","news_news_nba");
        catidMap.put("电竞","news_news_esport");
        catidMap.put("数码","news_news_digi");
        catidMap.put("星座","news_news_astro");
        catidMap.put("电影","news_news_movie");
        catidMap.put("教育","news_news_edu");
        catidMap.put("美容","news_news_meirong");
        catidMap.put("电视剧","news_news_tv");
        catidMap.put("搏击","news_news_strike");
        catidMap.put("健康","news_news_health");
        catidMap.put("摄影","news_news_sheying");
        catidMap.put("生活","news_news_lifes");
        catidMap.put("旅游","news_news_visit");
        catidMap.put("韩流","news_news_hanliu");
        catidMap.put("综艺","news_news_zongyi");
        catidMap.put("美食","news_news_food");
        catidMap.put("育儿","news_news_baby");
        catidMap.put("宠物","news_news_pet");
        catidMap.put("历史","news_news_history");
        catidMap.put("音乐","news_news_music");
        catidMap.put("传媒","news_news_media");
        catidMap.put("科普","news_news_kepu");
        catidMap.put("足球","news_news_football");
        catidMap.put("必读","news_news_twentyf");
        catidMap.put("校园","news_news_schoolyard");
        catidMap.put("时政","news_news_shizheng");

    }


    //path
    public static final String DROID_LOGO = "M 149.22,22.00\n" +
            "           C 148.23,20.07 146.01,16.51 146.73,14.32\n" +
            "             148.08,10.21 152.36,14.11 153.65,16.06\n" +
            "             153.65,16.06 165.00,37.00 165.00,37.00\n" +
            "             194.29,27.24 210.71,27.24 240.00,37.00\n" +
            "             240.00,37.00 251.35,16.06 251.35,16.06\n" +
            "             252.64,14.11 256.92,10.21 258.27,14.32\n" +
            "             258.99,16.51 256.77,20.08 255.78,22.00\n" +
            "             252.53,28.28 248.44,34.36 246.00,41.00\n" +
            "             252.78,43.16 258.78,48.09 263.96,52.85\n" +
            "             281.36,68.83 289.00,86.62 289.00,110.00\n" +
            "             289.00,110.00 115.00,110.00 115.00,110.00\n" +
            "             115.00,110.00 117.66,91.00 117.66,91.00\n" +
            "             120.91,76.60 130.30,62.72 141.04,52.85\n" +
            "             146.22,48.09 152.22,43.16 159.00,41.00\n" +
            "             159.00,41.00 149.22,22.00 149.22,22.00 Z\n" +
            "           M 70.80,56.00\n" +
            "           C 70.80,56.00 97.60,100.00 97.60,100.00\n" +
            "             101.34,106.21 108.32,116.34 110.21,123.00\n" +
            "             113.76,135.52 103.90,147.92 91.00,147.92\n" +
            "             78.74,147.92 74.44,139.06 69.00,130.00\n" +
            "             69.00,130.00 39.80,82.00 39.80,82.00\n" +
            "             35.73,75.29 28.40,66.08 29.20,58.00\n" +
            "             30.26,47.20 38.61,40.47 49.00,39.72\n" +
            "             61.22,40.24 64.96,46.28 70.80,56.00 Z\n" +
            "           M 375.80,58.00\n" +
            "           C 376.60,66.08 369.27,75.29 365.20,82.00\n" +
            "             365.20,82.00 336.00,130.00 336.00,130.00\n" +
            "             330.71,138.82 326.73,147.24 315.00,147.89\n" +
            "             301.74,148.63 291.14,135.87 294.79,123.00\n" +
            "             296.68,116.34 303.66,106.21 307.40,100.00\n" +
            "             307.40,100.00 333.00,58.00 333.00,58.00\n" +
            "             339.02,47.98 342.23,40.92 355.00,39.72\n" +
            "             365.83,40.00 374.69,46.77 375.80,58.00 Z\n" +
            "           M 289.00,116.00\n" +
            "           C 289.00,116.00 289.00,239.00 289.00,239.00\n" +
            "             288.98,249.72 285.92,257.31 275.00,261.10\n" +
            "             265.22,264.50 258.37,259.56 255.02,264.43\n" +
            "             253.78,266.24 254.00,269.84 254.00,272.00\n" +
            "             254.00,272.00 254.00,298.00 254.00,298.00\n" +
            "             254.00,304.85 254.77,310.07 250.36,315.93\n" +
            "             242.35,326.68 226.84,326.49 218.80,315.93\n" +
            "             215.07,311.00 215.01,306.83 215.00,301.00\n" +
            "             215.00,301.00 215.00,262.00 215.00,262.00\n" +
            "             215.00,262.00 190.00,262.00 190.00,262.00\n" +
            "             190.00,262.00 190.00,301.00 190.00,301.00\n" +
            "             189.99,306.83 189.93,311.00 186.20,315.93\n" +
            "             178.16,326.49 162.65,326.68 154.64,315.93\n" +
            "             151.09,311.22 151.01,307.61 151.00,302.00\n" +
            "             151.00,302.00 151.00,272.00 151.00,272.00\n" +
            "             151.00,269.84 151.22,266.24 149.98,264.43\n" +
            "             146.53,259.42 138.97,264.76 129.00,260.86\n" +
            "             118.39,256.72 116.02,248.29 116.00,238.00\n" +
            "             116.00,238.00 116.00,116.00 116.00,116.00\n" +
            "             116.00,116.00 289.00,116.00 289.00,116.00 Z";


}
