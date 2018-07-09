package cn.saymagic.begonia.sdk.core.api;

import java.util.List;
import java.util.Map;

import cn.saymagic.begonia.sdk.core.pojo.Collection;
import cn.saymagic.begonia.sdk.core.pojo.Download;
import cn.saymagic.begonia.sdk.core.pojo.Photo;
import cn.saymagic.begonia.sdk.core.pojo.Portfolio;
import cn.saymagic.begonia.sdk.core.pojo.SearchResult;
import cn.saymagic.begonia.sdk.core.pojo.Statistics;
import cn.saymagic.begonia.sdk.core.pojo.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface UnsplashService {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

    @GET("users/{username}/portfolio")
    Call<Portfolio> getUserPortfolio(@Path("username") String username);

    @GET("users/{username}/photos")
    Call<List<Photo>> getUserPhotos(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page);

    @GET("users/{username}/photos")
    Call<List<Photo>> getUserPhotos(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page, @QueryMap Map<String, String> options);

    @GET("users/{username}/likes")
    Call<List<Photo>> getUserLikes(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page, @Query("order_by") String order_by);

    @GET("users/{username}/collections")
    Call<List<Collection>> getUserCollections(@Path("username") String username, @Query("page") int page, @Query("per_page") int per_page);

    @GET("users/{username}/statistics")
    Call<List<Statistics>> getUserStatistics(@Path("username") String username);

    @GET("photos")
    Call<List<Photo>> getPhotos(@Query("page") int page, @Query("per_page") int per_page, @Query("order_by") String order_by);

    @GET("photos/curated")
    Call<List<Photo>> getPhotosCurated(@Query("page") int page, @Query("per_page") int per_page, @Query("order_by") String order_by);

    @GET("photos/{id}")
    Call<Photo> getPhoto(@Path("id") String photoId);

    @GET("photos/{id}/statistics")
    Call<Statistics> getPhotoStatistics(@Path("id") String photoId);

    @GET("photos/{id}/download")
    Call<Download> getPhotoDownload(@Path("id") String photoId);

    @GET("search/photos")
    Call<SearchResult<Photo>> searchPhotos(@Query("query") String query, @Query("page") int page, @Query("per_page") int per_page);

    @GET("search/photos")
    Call<SearchResult<Photo>> searchPhotos(@Query("query") String query, @Query("page") int page, @Query("per_page") int per_page, @QueryMap Map<String, String> options);

    @GET("search/collections")
    Call<SearchResult<Collection>> searchCollections(@Query("query") String query, @Query("page") int page, @Query("per_page") int per_page);

    @GET("search/users")
    Call<SearchResult<User>> searchUsers(@Query("query") String query, @Query("page") int page, @Query("per_page") int per_page);

    @GET("collections")
    Call<List<Collection>> getCollections(@Query("page") int page, @Query("per_page") int per_page);

    @GET("collections/featured")
    Call<List<Collection>> getCollectionsFeatured(@Query("page") int page, @Query("per_page") int per_page);

    @GET("collections/curated")
    Call<List<Collection>> getCollectionsCurated(@Query("page") int page, @Query("per_page") int per_page);

    @GET("collections/{id}")
    Call<Collection> getCollection(@Path("id") String collectionId);

    @GET("collections/curated/{id}")
    Call<Collection> getCuratedCollection(@Path("id") String collectionId);

    @GET("collections/{id}/photos")
    Call<List<Photo>> getCollectionPhotos(@Path("id") String collectionId, @Query("page") int page, @Query("per_page") int per_page);

    @GET("collections/curated/{id}/photos")
    Call<List<Photo>> getCollectionCuratedPhotos(@Path("id") String collectionId, @Query("page") int page, @Query("per_page") int per_page);

    @GET("collections/{id}/related")
    Call<Collection> getRelatedCollection(@Path("id") String collectionId);
}
