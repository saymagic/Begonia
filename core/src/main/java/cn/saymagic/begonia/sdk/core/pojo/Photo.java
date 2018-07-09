package cn.saymagic.begonia.sdk.core.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photo {


    /**
     * id : 8lf23Ic0cjU
     * created_at : 2018-01-27T09:32:08-05:00
     * updated_at : 2018-05-09T03:55:12-04:00
     * width : 4301
     * height : 3072
     * color : #E6E9D5
     * description : null
     * urls : {"raw":"https://images.unsplash.com/photo-1517063485230-c9d1f320141b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjI2NjIyfQ&s=358acf5067b30c381e10e60d69c8c03d","full":"https://images.unsplash.com/photo-1517063485230-c9d1f320141b?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjI2NjIyfQ&s=002ad430f9593b01d35107f0cb3eaa92","regular":"https://images.unsplash.com/photo-1517063485230-c9d1f320141b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjI2NjIyfQ&s=7f4eeaa36d0057430d499d015708986f","small":"https://images.unsplash.com/photo-1517063485230-c9d1f320141b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjI2NjIyfQ&s=3778f5f7e08451fe42d04749739a586f","thumb":"https://images.unsplash.com/photo-1517063485230-c9d1f320141b?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjI2NjIyfQ&s=4f9ebe734aa22f25e48689d741d9acf4"}
     * links : {"self":"https://api.unsplash.com/photos/8lf23Ic0cjU","html":"https://unsplash.com/photos/8lf23Ic0cjU","download":"https://unsplash.com/photos/8lf23Ic0cjU/download","download_location":"https://api.unsplash.com/photos/8lf23Ic0cjU/download"}
     * categories : []
     * sponsored : false
     * likes : 0
     * liked_by_user : false
     * current_user_collections : []
     * slug : null
     * user : {"id":"TITA4jxLbVQ","updated_at":"2018-05-03T14:01:34-04:00","username":"munandy","name":"Andy Munro","first_name":"Andy","last_name":"Munro","twitter_username":"andymunro99","portfolio_url":null,"bio":null,"location":"UK","links":{"self":"https://api.unsplash.com/users/munandy","html":"https://unsplash.com/@munandy","photos":"https://api.unsplash.com/users/munandy/photos","likes":"https://api.unsplash.com/users/munandy/likes","portfolio":"https://api.unsplash.com/users/munandy/portfolio","following":"https://api.unsplash.com/users/munandy/following","followers":"https://api.unsplash.com/users/munandy/followers"},"profile_image":{"small":"https://images.unsplash.com/profile-1516444458216-d064334b5215?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=fdef910401a2b22cbd02bf505f0e7305","medium":"https://images.unsplash.com/profile-1516444458216-d064334b5215?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=4366fbc6a2e5bf568456a7f45508d817","large":"https://images.unsplash.com/profile-1516444458216-d064334b5215?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=53a82ea1f3eb91070749739e334f097d"},"instagram_username":null,"total_collections":0,"total_likes":0,"total_photos":13}
     */

    private String id;

    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private int width;
    private int height;
    private String color;
    private String description;
    private Urls urls;
    private Links links;
    private boolean sponsored;
    private int likes;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    private String slug;
    private User user;
    private List<String> categories;
    private Statistics statistics;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByUser() {
        return likedByUser;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }

    public Object getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
