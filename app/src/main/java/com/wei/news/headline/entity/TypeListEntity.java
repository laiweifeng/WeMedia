package com.wei.news.headline.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TypeListEntity implements Serializable {

    private String pageToken;
    private boolean hasNext;
    private List<Data> data;
    private String dataType;
    private String appCode;
    private String retcode;

    @Override
    public String toString() {
        return "TypeListEntity{" +
                "pageToken='" + pageToken + '\'' +
                ", hasNext=" + hasNext +
                ", data=" + data +
                ", dataType='" + dataType + '\'' +
                ", appCode='" + appCode + '\'' +
                ", retcode='" + retcode + '\'' +
                '}';
    }

    public String getPageToken() {
        return pageToken;
    }

    public void setPageToken(String pageToken) {
        this.pageToken = pageToken;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public class Data implements Serializable {
        private String title;
        private String publishDateStr;
        private List<String> imageUrls;
        private String id;
        private int commentCount;
        private long publishDate;
        private String url;
        private String posterScreenName;
        private String content;
        private String catPathKey;
        private String posterId;
        private String videoUrls;
        private boolean isViewed;
        private List<String> tags;



        @Override
        public String toString() {
            return "Data{" +
                    "title='" + title + '\'' +
                    ", publishDateStr=" + publishDateStr +
                    ", imageUrls=" + imageUrls +
                    ", id='" + id + '\'' +
                    ", commentCount=" + commentCount +
                    ", publishDate=" + publishDate +
                    ", url='" + url + '\'' +
                    ", posterScreenName='" + posterScreenName + '\'' +
                    ", content='" + content + '\'' +
                    ", catPathKey='" + catPathKey + '\'' +
                    ", posterId='" + posterId + '\'' +
                    ", videoUrls='" + videoUrls + '\'' +
                    ", tags=" + tags +
                    '}';
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public long getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(long publishDate) {
            this.publishDate = publishDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPosterScreenName() {
            return posterScreenName;
        }

        public void setPosterScreenName(String posterScreenName) {
            this.posterScreenName = posterScreenName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCatPathKey() {
            return catPathKey;
        }

        public void setCatPathKey(String catPathKey) {
            this.catPathKey = catPathKey;
        }

        public String getPosterId() {
            return posterId;
        }

        public void setPosterId(String posterId) {
            this.posterId = posterId;
        }

        public String getVideoUrls() {
            return videoUrls;
        }

        public void setVideoUrls(String videoUrls) {
            this.videoUrls = videoUrls;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
        public boolean isViewed() {
            return isViewed;
        }

        public void setViewed(boolean viewed) {
            isViewed = viewed;
        }
    }
}
