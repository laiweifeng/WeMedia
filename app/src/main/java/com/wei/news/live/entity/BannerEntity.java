package com.wei.news.live.entity;

import java.util.List;

public class BannerEntity {

    List<String> descList;
    List<String> urlList;

    public List<String> getDescList() {
        return descList;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setDescList(List<String> descList) {
        this.descList = descList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
}
