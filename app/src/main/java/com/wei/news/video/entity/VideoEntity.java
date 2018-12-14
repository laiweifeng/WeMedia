package com.wei.news.video.entity;

import java.util.List;

public class VideoEntity {


    private boolean hasNext;
    private String pageToken;
    private String appCode;
    private List<Data> data;
    private String dataType;
    private String retcode;

    public boolean isHasNext() {
        return hasNext;
    }

    public String getPageToken() {
        return pageToken;
    }

    public String getAppCode() {
        return appCode;
    }

    public List<Data> getData() {
        return data;
    }

    public String getDataType() {
        return dataType;
    }

    public String getRetcode() {
        return retcode;
    }

    public  class  Data{

        private String url;
        private long publishDate;
        private List<String> videoUrls;
        private String favoriteCount;
        private String id;
        private String publishDateStr;
        private String posterScreenName;
        private String mediaType;
        private String description;
        private String audioId;
        private int likeCount;
        private boolean rating;
        private String durationMin;
        private GeoPoint geoPoint;
        private boolean isFree;
        private String country;
        private boolean viewCount;
        private String catPathKey;
        private long updateDate;
        private String dislikeCount;
        private boolean memberOnly;
        private String coverUrl;
        private String posterId;
        private List<String> tags;
        private int commentCount;
        private String title;
        private List<PartList> partList;
        private long danmakuCount;
        private boolean imageUrls;
        private boolean imageURLs;
        private List<String> labels;
        private boolean pDate;
        private boolean subtitle;
        private boolean shareCount;
        private boolean commenterScreenName;
        private boolean sellerScreenName;

        public String getUrl() {
            return url;
        }

        public long getPublishDate() {
            return publishDate;
        }

        public List<String> getVideoUrls() {
            return videoUrls;
        }

        public String getFavoriteCount() {
            return favoriteCount;
        }

        public String getId() {
            return id;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public String getPosterScreenName() {
            return posterScreenName;
        }

        public String getMediaType() {
            return mediaType;
        }

        public String getDescription() {
            return description;
        }

        public String getAudioId() {
            return audioId;
        }

        public int getLikeCount() {
            return likeCount;
        }

        public boolean isRating() {
            return rating;
        }

        public String getDurationMin() {
            return durationMin;
        }

        public GeoPoint getGeoPoint() {
            return geoPoint;
        }

        public boolean isFree() {
            return isFree;
        }

        public String getCountry() {
            return country;
        }

        public boolean isViewCount() {
            return viewCount;
        }

        public String getCatPathKey() {
            return catPathKey;
        }

        public long getUpdateDate() {
            return updateDate;
        }

        public String getDislikeCount() {
            return dislikeCount;
        }

        public boolean isMemberOnly() {
            return memberOnly;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public String getPosterId() {
            return posterId;
        }

        public List<String> getTags() {
            return tags;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public String getTitle() {
            return title;
        }

        public List<PartList> getPartList() {
            return partList;
        }

        public long getDanmakuCount() {
            return danmakuCount;
        }

        public boolean isImageUrls() {
            return imageUrls;
        }

        public boolean isImageURLs() {
            return imageURLs;
        }

        public List<String> getLabels() {
            return labels;
        }

        public boolean ispDate() {
            return pDate;
        }

        public boolean isSubtitle() {
            return subtitle;
        }

        public boolean isShareCount() {
            return shareCount;
        }

        public boolean isCommenterScreenName() {
            return commenterScreenName;
        }

        public boolean isSellerScreenName() {
            return sellerScreenName;
        }
    }
    public  class  PartList {
        private String partName;
        private String partId;
        private String durationMin;

        public String getPartName() {
            return partName;
        }

        public String getPartId() {
            return partId;
        }

        public String getDurationMin() {
            return durationMin;
        }
    }

    public class GeoPoint{
        private String lat;
        private String lon;

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }
    }
}
