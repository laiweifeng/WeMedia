package com.wei.news.games.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameListEntity implements Serializable {

    private String appCode;
    private String hasNext;
    private String pageToken;
    private String retcode;
    private ArrayList<Data> data;

    public String getAppCode() {
        return appCode;
    }

    public String getHasNext() {
        return hasNext;
    }

    public String getPageToken() {
        return pageToken;
    }

    public String getRetcode() {
        return retcode;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "GameListEntity{" +
                "appCode='" + appCode + '\'' +
                ", hasNext='" + hasNext + '\'' +
                ", pageToken='" + pageToken + '\'' +
                ", retcode='" + retcode + '\'' +
                ", data=" + data +
                '}';
    }

    public class  Data  implements Serializable {
        private String avatarUrl;
        private String commentCount;
        private String commenterScreenName;
        private String coverUrl;
        private String description;
        private String devId;
        private int downloadCount;
        private String id;
        private String imageURLs;
        private String isFree;
        private String keyValues;
        private String likeCount;
        private String nowDownloadCount;
        private String pDate;
        private String posterScreenName;
        private String propertyDev;
        private String publishDate;
        private String publishDateStr;
        private String rating;
        private String ratingCount;
        private String sellerScreenName;
        private String shareCount;
        private String subtitle;
        private String title;
        private String updateDate;
        private String url;
        private int weekDownloadCount;
        private ArrayList<FileOptions> fileOptions;
        private ArrayList<String> imageUrls;
        private ArrayList<RatingDist> ratingDist;
        private ArrayList<String> tags;
        private ArrayList<String> updateItems;

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public String getCommentCount() {
            return commentCount;
        }

        public String getCommenterScreenName() {
            return commenterScreenName;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public String getDescription() {
            return description;
        }

        public String getDevId() {
            return devId;
        }

        public int getDownloadCount() {
            return downloadCount;
        }

        public String getId() {
            return id;
        }

        public String getImageURLs() {
            return imageURLs;
        }

        public String getIsFree() {
            return isFree;
        }

        public String getKeyValues() {
            return keyValues;
        }

        public String getLikeCount() {
            return likeCount;
        }

        public String getNowDownloadCount() {
            return nowDownloadCount;
        }

        public String getpDate() {
            return pDate;
        }

        public String getPosterScreenName() {
            return posterScreenName;
        }

        public String getPropertyDev() {
            return propertyDev;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public String getRating() {
            return rating;
        }

        public String getRatingCount() {
            return ratingCount;
        }

        public String getSellerScreenName() {
            return sellerScreenName;
        }

        public String getShareCount() {
            return shareCount;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getTitle() {
            return title;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public String getUrl() {
            return url;
        }

        public int getWeekDownloadCount() {
            return weekDownloadCount;
        }

        public List<FileOptions> getFileOptions() {
            return fileOptions;
        }

        public ArrayList<String> getImageUrls() {
            return imageUrls;
        }

        public List<RatingDist> getRatingDist() {
            return ratingDist;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<String> getUpdateItems() {
            return updateItems;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "avatarUrl='" + avatarUrl + '\'' +
                    ", commentCount='" + commentCount + '\'' +
                    ", commenterScreenName='" + commenterScreenName + '\'' +
                    ", coverUrl='" + coverUrl + '\'' +
                    ", description='" + description + '\'' +
                    ", devId='" + devId + '\'' +
                    ", downloadCount='" + downloadCount + '\'' +
                    ", id='" + id + '\'' +
                    ", imageURLs='" + imageURLs + '\'' +
                    ", isFree='" + isFree + '\'' +
                    ", keyValues='" + keyValues + '\'' +
                    ", likeCount='" + likeCount + '\'' +
                    ", nowDownloadCount='" + nowDownloadCount + '\'' +
                    ", pDate='" + pDate + '\'' +
                    ", posterScreenName='" + posterScreenName + '\'' +
                    ", propertyDev='" + propertyDev + '\'' +
                    ", publishDate='" + publishDate + '\'' +
                    ", publishDateStr='" + publishDateStr + '\'' +
                    ", rating='" + rating + '\'' +
                    ", ratingCount='" + ratingCount + '\'' +
                    ", sellerScreenName='" + sellerScreenName + '\'' +
                    ", shareCount='" + shareCount + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", title='" + title + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", url='" + url + '\'' +
                    ", weekDownloadCount='" + weekDownloadCount + '\'' +
                    ", fileOptions=" + fileOptions +
                    ", imageUrls=" + imageUrls +
                    ", ratingDist=" + ratingDist +
                    ", tags=" + tags +
                    ", updateItems=" + updateItems +
                    '}';
        }
    }
    public class FileOptions  implements Serializable {
        private String format;
        private String id;
        private String os;
        private String sizeM;
        private String url;
        private String version;

        public String getFormat() {
            return format;
        }

        public String getId() {
            return id;
        }

        public String getOs() {
            return os;
        }

        public String getSizeM() {
            return sizeM;
        }

        public String getUrl() {
            return url;
        }

        public String getVersion() {
            return version;
        }
    }

    public class RatingDist  implements Serializable {
        String key;
        String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


}
