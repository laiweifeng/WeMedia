package com.wei.news.live.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LiveListEntity implements Serializable{

    private String pageToken;
    private boolean hasNext;
    private ArrayList<Data> data;
    private String retcode;
    private String appCode;
    private String dataType;

    @Override
    public String toString() {
        return "LiveListEntity{" +
                "pageToken='" + pageToken + '\'' +
                ", hasNext=" + hasNext +
                ", data=" + data +
                ", retcode='" + retcode + '\'' +
                ", appCode='" + appCode + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }

    public String getPageToken() {
        return pageToken;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public List<Data> getData() {
        return data;
    }

    public String getRetcode() {
        return retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public String getDataType() {
        return dataType;
    }

    public class Data implements Serializable {

        private String description;
        private String catId1;
        private ArrayList<Donors> donors;
        private String userScreenName;
        private long income;
        private String userId;
        private String id;
        private boolean isLive;
        private ArrayList<String> tags;
        private int fansCount;
        private String avatarUrl;
        private String coverUrl;
        private String fansDonors;
        private String subtitle;
        private ArrayList<String> imageUrls;
        private String catName1;
        private String catPathKey;
        private int idGrade;
        private String url;
        private ArrayList<String> videoUrls;
        private int onlineCount;
        private String title;
        private String vipDonors;
        private String followCount;
        private String publishDateStr;
        private ArrayList<WeekDonors> weekDonors;
        private long publishDate;

        @Override
        public String toString() {
            return "Data{" +
                    "description='" + description + '\'' +
                    ", catId1='" + catId1 + '\'' +
                    ", donors=" + donors +
                    ", userScreenName='" + userScreenName + '\'' +
                    ", income=" + income +
                    ", userId='" + userId + '\'' +
                    ", id='" + id + '\'' +
                    ", isLive=" + isLive +
                    ", tags=" + tags +
                    ", fansCount=" + fansCount +
                    ", avatarUrl='" + avatarUrl + '\'' +
                    ", coverUrl='" + coverUrl + '\'' +
                    ", fansDonors='" + fansDonors + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", imageUrls=" + imageUrls +
                    ", catName1='" + catName1 + '\'' +
                    ", catPathKey='" + catPathKey + '\'' +
                    ", idGrade=" + idGrade +
                    ", url='" + url + '\'' +
                    ", videoUrls=" + videoUrls +
                    ", onlineCount=" + onlineCount +
                    ", title='" + title + '\'' +
                    ", vipDonors='" + vipDonors + '\'' +
                    ", followCount='" + followCount + '\'' +
                    ", publishDateStr='" + publishDateStr + '\'' +
                    ", weekDonors=" + weekDonors +
                    ", publishDate=" + publishDate +
                    '}';
        }

        public List<Donors> getDonors() {
            return donors;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<WeekDonors> getWeekDonors() {
            return weekDonors;
        }

        public String getDescription() {
            return description;
        }

        public String getCatId1() {
            return catId1;
        }


        public String getUserScreenName() {
            return userScreenName;
        }

        public long getIncome() {
            return income;
        }

        public String getUserId() {
            return userId;
        }

        public String getId() {
            return id;
        }

        public boolean isLive() {
            return isLive;
        }


        public int getFansCount() {
            return fansCount;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public String getFansDonors() {
            return fansDonors;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public String getCatName1() {
            return catName1;
        }

        public String getCatPathKey() {
            return catPathKey;
        }

        public int getIdGrade() {
            return idGrade;
        }

        public String getUrl() {
            return url;
        }

        public List<String> getVideoUrls() {
            return videoUrls;
        }

        public int getOnlineCount() {
            return onlineCount;
        }

        public String getTitle() {
            return title;
        }

        public String getVipDonors() {
            return vipDonors;
        }

        public String getFollowCount() {
            return followCount;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }


        public long getPublishDate() {
            return publishDate;
        }
    }

    public  class  Donors implements   Serializable{
        private String name;
        private String value;


    }
    public  class  WeekDonors implements   Serializable{
        private String id;
        private String name;
        private String value;


    }
}
