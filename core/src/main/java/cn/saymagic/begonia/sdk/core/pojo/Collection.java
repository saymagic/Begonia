package cn.saymagic.begonia.sdk.core.pojo;

import com.google.gson.annotations.SerializedName;

public class Collection {


    /**
     * id : 296
     * title : I like a man with a beard.
     * description : Yeah even Santa...
     * published_at : 2016-01-27T18:47:13-05:00
     * updated_at : 2016-07-10T11:00:01-05:00
     * curated : false
     * total_photos : 12
     * private : false
     * share_key : 312d188df257b957f8b86d2ce20e4766
     */

    private int id;
    private String title;
    private String description;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private boolean curated;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("share_key")
    private String shareKey;
    @SerializedName("cover_photo")
    private Photo coverPhoto;
    @SerializedName("private")
    private boolean privateState;
    private User user;
    private Links links;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isCurated() {
        return curated;
    }

    public void setCurated(boolean curated) {
        this.curated = curated;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public Photo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(Photo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public boolean isPrivateState() {
        return privateState;
    }

    public void setPrivateState(boolean privateState) {
        this.privateState = privateState;
    }
}
