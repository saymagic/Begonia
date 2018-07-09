package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import cn.saymagic.begonia.exception.UnexpectedCodeException
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.Unsplash
import cn.saymagic.begonia.sdk.core.UnsplashConstants
import cn.saymagic.begonia.sdk.core.api.UnsplashService
import cn.saymagic.begonia.sdk.core.pojo.Photo
import cn.saymagic.begonia.sdk.core.pojo.SearchResult
import cn.saymagic.begonia.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoListDataSource(private val dataParam: DataSourceParam) : PositionalDataSource<Photo>() {

    private val api: UnsplashService = Unsplash.getInstance().service

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    var retry: Runnable? = null

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Photo>) {
        networkState.postValue(NetworkState.LOADING)

        when (dataParam.type) {
            Constants.PHOTO_DATA_TYPE_NORMAL -> api.getPhotos(params.startPosition, params.loadSize, UnsplashConstants.ORDER_BY_LATEST)
                    .enqueue(getPhotoListLoadRangeCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_USER -> api.getUserPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_USERNAME), params.startPosition, params.loadSize)
                    .enqueue(getPhotoListLoadRangeCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_USER_LIKED -> api.getUserLikes(dataParam.getString(Constants.PHOTO_DATA_PARAM_USERNAME), params.startPosition, params.loadSize, UnsplashConstants.ORDER_BY_LATEST)
                    .enqueue(getPhotoListLoadRangeCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_COLLECTION -> api.getCollectionPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_COLLECTION_ID), params.startPosition, params.loadSize)
                    .enqueue(getPhotoListLoadRangeCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_SEARCH -> api.searchPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_SEARCH_TEXT), params.startPosition, params.loadSize)
                    .enqueue(getSearchResultLoadRangeCallback(params, callback))
        }
    }

    private fun getPhotoListLoadRangeCallback(params: LoadRangeParams, callback: LoadRangeCallback<Photo>): Callback<List<Photo>> {
        return object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
                retry = Runnable {
                    loadRange(params, callback)
                }
            }

            override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
                retry = if (response?.isSuccessful == true) {
                    callback.onResult(response?.body()!!)
                    networkState.postValue(NetworkState.LOADED)
                    null
                } else {
                    networkState.postValue(NetworkState.error(UnexpectedCodeException(response?.code())))
                    Runnable {
                        loadRange(params, callback)
                    }
                }
            }
        }
    }

    private fun getPhotoListLoadInitialCallback(params: LoadInitialParams, callback: LoadInitialCallback<Photo>): Callback<List<Photo>> {
        return object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
                retry = Runnable {
                    loadInitial(params, callback)
                }
            }

            override fun onResponse(call: Call<List<Photo>>?, response: Response<List<Photo>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        Integer.parseInt(response.raw()?.header("x-total", "-1"))
                    } catch (e: Exception) {
                        -1
                    }
                    if (total >= 0) {
                        callback.onResult(response?.body()!!, 0, total)
                    } else {
                        callback.onResult(response?.body()!!, response?.body()?.size ?: 0)
                    }
                    networkState.postValue(NetworkState.LOADED)
                    null
                } else {
                    networkState.postValue(NetworkState.error(UnexpectedCodeException(response?.code())))
                    Runnable {
                        loadInitial(params, callback)
                    }
                }
            }
        }
    }

    private fun getSearchResultLoadRangeCallback(params: LoadRangeParams, callback: LoadRangeCallback<Photo>): Callback<SearchResult<Photo>> {
        return object : Callback<SearchResult<Photo>> {
            override fun onFailure(call: Call<SearchResult<Photo>>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
                retry = Runnable {
                    loadRange(params, callback)
                }
            }

            override fun onResponse(call: Call<SearchResult<Photo>>?, response: Response<SearchResult<Photo>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        response?.body()?.total?:Integer.parseInt(response.raw()?.header("x-total", "-1"))
                    } catch (e: Exception) {
                        -1
                    }
                    callback.onResult(response?.body()!!.results)
                    networkState.postValue(NetworkState.LOADED)
                    null
                } else {
                    networkState.postValue(NetworkState.error(UnexpectedCodeException(response?.code())))
                    Runnable {
                        loadRange(params, callback)
                    }
                }
            }
        }
    }


    private fun getSearchResultLoadInitialCallback(params: LoadInitialParams, callback: LoadInitialCallback<Photo>): Callback<SearchResult<Photo>> {
        return object : Callback<SearchResult<Photo>> {
            override fun onFailure(call: Call<SearchResult<Photo>>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
                retry = Runnable {
                    loadInitial(params, callback)
                }
            }

            override fun onResponse(call: Call<SearchResult<Photo>>?, response: Response<SearchResult<Photo>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        response?.body()?.total?:Integer.parseInt(response.raw()?.header("x-total", "-1"))
                    } catch (e: Exception) {
                        -1
                    }
                    if (total >= 0) {
                        callback.onResult(response?.body()!!.results, 0, total)
                    } else {
                        callback.onResult(response?.body()!!.results, response?.body()?.results?.size ?: 0)
                    }
                    networkState.postValue(NetworkState.LOADED)
                    null
                } else {
                    networkState.postValue(NetworkState.error(UnexpectedCodeException(response?.code())))
                    Runnable {
                        loadInitial(params, callback)
                    }
                }
            }
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Photo>) {
        networkState.postValue(NetworkState.LOADING)
        when (dataParam.type) {
            Constants.PHOTO_DATA_TYPE_NORMAL -> api.getPhotos(params.requestedStartPosition, params.requestedLoadSize, UnsplashConstants.ORDER_BY_LATEST)
                    .enqueue(getPhotoListLoadInitialCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_USER -> api.getUserPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_USERNAME), params.requestedStartPosition, params.requestedLoadSize)
                    .enqueue(getPhotoListLoadInitialCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_USER_LIKED -> api.getUserLikes(dataParam.getString(Constants.PHOTO_DATA_PARAM_USERNAME), params.requestedStartPosition, params.requestedLoadSize, UnsplashConstants.ORDER_BY_LATEST)
                    .enqueue(getPhotoListLoadInitialCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_COLLECTION -> api.getCollectionPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_COLLECTION_ID), params.requestedStartPosition, params.requestedLoadSize)
                    .enqueue(getPhotoListLoadInitialCallback(params, callback))
            Constants.PHOTO_DATA_TYPE_SEARCH -> api.searchPhotos(dataParam.getString(Constants.PHOTO_DATA_PARAM_SEARCH_TEXT), params.requestedStartPosition, params.requestedLoadSize)
                    .enqueue(getSearchResultLoadInitialCallback(params, callback))
        }
    }


}