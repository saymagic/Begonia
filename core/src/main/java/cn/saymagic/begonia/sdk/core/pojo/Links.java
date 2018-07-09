package cn.saymagic.begonia.sdk.core.pojo;

public class Links {

    /**
     * self : https://api.unsplash.com/users/jimmyexample
     * html : https://unsplash.com/jimmyexample
     * photos : https://api.unsplash.com/users/jimmyexample/photos
     * likes : https://api.unsplash.com/users/jimmyexample/likes
     * portfolio : https://api.unsplash.com/users/jimmyexample/portfolio
     */

    private String self;
    private String html;
    private String photos;
    private String likes;
    private String portfolio;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

}
