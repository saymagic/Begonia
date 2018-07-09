package cn.saymagic.begonia.sdk.core.pojo;

public class Statistics {

    private StatisticsItem downloads;
    private StatisticsItem views;
    private StatisticsItem likes;

    public StatisticsItem getDownloads() {
        return downloads;
    }

    public void setDownloads(StatisticsItem downloads) {
        this.downloads = downloads;
    }

    public StatisticsItem getViews() {
        return views;
    }

    public void setViews(StatisticsItem views) {
        this.views = views;
    }

    public StatisticsItem getLikes() {
        return likes;
    }

    public void setLikes(StatisticsItem likes) {
        this.likes = likes;
    }
}
