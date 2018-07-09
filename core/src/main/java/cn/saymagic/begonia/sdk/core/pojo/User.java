package cn.saymagic.begonia.sdk.core.pojo;

import com.google.gson.annotations.SerializedName;

public class User {


    /**
     * id : pXhwzz1JtQU
     * updated_at : 2016-07-10T11:00:01-05:00
     * username : jimmyexample
     * name : James Example
     * first_name : James
     * last_name : Example
     * instagram_username : instantgrammer
     * twitterUsername : jimmy
     * portfolioUrl : null
     * bio : The user's bio
     * location : Montreal, Qc
     * totalLikes : 20
     * totalPhotos : 10
     * totalCollections : 5
     * followedByUser : false
     * followersCount : 300
     * followingCount : 25
     * downloads : 225974
     * profileImage : {"small":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32","medium":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64","large":"https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128"}
     * badge : {"title":"Book contributor","primary":true,"slug":"book-contributor","link":"https://book.unsplash.com"}
     * links : {"self":"https://api.unsplash.com/users/jimmyexample","html":"https://unsplash.com/jimmyexample","photos":"https://api.unsplash.com/users/jimmyexample/photos","likes":"https://api.unsplash.com/users/jimmyexample/likes","portfolio":"https://api.unsplash.com/users/jimmyexample/portfolio"}
     */

    private String id;
    private String updated_at;
    private String username;
    private String name;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("instagram_username")
    private String instagramUsername;
    @SerializedName("twitter_username")
    private String twitterUsername;
    @SerializedName("portfolio_url")
    private Object portfolioUrl;
    private String bio;
    private String location;
    @SerializedName("total_likes")
    private int totalLikes;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("total_collections")
    private int totalCollections;
    @SerializedName("followed_by_user")
    private boolean followedByUser;
    @SerializedName("followers_count")
    private int followersCount;
    @SerializedName("following_count")
    private int followingCount;
    private int downloads;
    @SerializedName("profile_image")
    private ProfileImageBean profileImage;
    private BadgeBean badge;
    private Links links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInstagramUsername() {
        return instagramUsername;
    }

    public void setInstagramUsername(String instagramUsername) {
        this.instagramUsername = instagramUsername;
    }

    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }

    public Object getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(Object portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public int getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }

    public boolean isFollowedByUser() {
        return followedByUser;
    }

    public void setFollowedByUser(boolean followedByUser) {
        this.followedByUser = followedByUser;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public ProfileImageBean getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImageBean profileImage) {
        this.profileImage = profileImage;
    }

    public BadgeBean getBadge() {
        return badge;
    }

    public void setBadge(BadgeBean badge) {
        this.badge = badge;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public static class ProfileImageBean {
        /**
         * small : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32
         * medium : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64
         * large : https://images.unsplash.com/face-springmorning.jpg?q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128
         */

        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }

    public static class BadgeBean {
        /**
         * title : Book contributor
         * primary : true
         * slug : book-contributor
         * link : https://book.unsplash.com
         */

        private String title;
        private boolean primary;
        private String slug;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }


}
