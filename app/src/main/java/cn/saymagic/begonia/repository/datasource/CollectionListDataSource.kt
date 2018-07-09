package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import cn.saymagic.begonia.exception.UnexpectedCodeException
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.Unsplash
import cn.saymagic.begonia.sdk.core.api.UnsplashService
import cn.saymagic.begonia.sdk.core.pojo.Collection
import cn.saymagic.begonia.sdk.core.pojo.SearchResult
import cn.saymagic.begonia.util.Constants
import cn.saymagic.begonia.util.Constants.COLLECTION_DATA_PARAM_USERNAME
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CollectionListDataSource(private val dataParam: DataSourceParam) : PositionalDataSource<Collection>() {

    private val api: UnsplashService = Unsplash.getInstance().service

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var retry: Runnable? = null

    private fun getCollectionListLoadRangeCallback(params: LoadRangeParams, callback: LoadRangeCallback<Collection>): Callback<List<Collection>> {
        return object : Callback<List<Collection>> {
            override fun onFailure(call: Call<List<Collection>>?, t: Throwable?) {
                retry = Runnable {
                    loadRange(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<List<Collection>>?, response: Response<List<Collection>>?) {
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

    private fun getSearchResultLoadRangeCallback(params: LoadRangeParams, callback: LoadRangeCallback<Collection>): Callback<SearchResult<Collection>> {
        return object : Callback<SearchResult<Collection>> {
            override fun onFailure(call: Call<SearchResult<Collection>>?, t: Throwable?) {
                retry = Runnable {
                    loadRange(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<SearchResult<Collection>>?, response: Response<SearchResult<Collection>>?) {
                retry = if (response?.isSuccessful == true) {
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

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Collection>) {
        networkState.postValue(NetworkState.LOADING)
        when (dataParam.type) {
            Constants.COLLECTION_DATA_TYPE_USER -> api.getUserCollections(dataParam.getString(COLLECTION_DATA_PARAM_USERNAME), params.startPosition, params.loadSize)
                    .enqueue(getCollectionListLoadRangeCallback(params, callback))
            Constants.COLLECTION_DATA_TYPE_SEARCH -> {
                api.searchCollections(dataParam.getString(Constants.COLLECTION_DATA_PARAM_SEARCH_TEXT), params.startPosition, params.loadSize)
                        .enqueue(getSearchResultLoadRangeCallback(params, callback))
            }
        }
    }

    private fun getCollectionListLoadInitialCallback(params: LoadInitialParams, callback: LoadInitialCallback<Collection>): Callback<List<Collection>> {
        return object : Callback<List<Collection>> {
            override fun onFailure(call: Call<List<Collection>>?, t: Throwable?) {
                retry = Runnable {
                    loadInitial(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<List<Collection>>?, response: Response<List<Collection>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        Integer.parseInt(response.raw()?.header("x-total", "-1"))
                    } catch (e: Exception) {
                        -1
                    }
                    if (total >= 0) {
                        callback.onResult(response?.body()!!, 0, total)
                    } else {
                        callback.onResult(response?.body()!!, response?.body()?.size
                                ?: 0)
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

    private fun getSearchResultLoadInitialCallback(params: LoadInitialParams, callback: LoadInitialCallback<Collection>): Callback<SearchResult<Collection>> {
        return object : Callback<SearchResult<Collection>> {
            override fun onFailure(call: Call<SearchResult<Collection>>?, t: Throwable?) {
                retry = Runnable {
                    loadInitial(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<SearchResult<Collection>>?, response: Response<SearchResult<Collection>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        response.body()?.total
                                ?: Integer.parseInt(response.raw()?.header("x-total", "-1"))
                    } catch (e: Exception) {
                        -1
                    }
                    if (total >= 0) {
                        callback.onResult(response?.body()!!.results, 0, total)
                    } else {
                        callback.onResult(response?.body()!!.results, response?.body()?.results?.size
                                ?: 0)
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

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Collection>) {
        networkState.postValue(NetworkState.LOADING)
        when (dataParam.type) {
            Constants.COLLECTION_DATA_TYPE_USER -> {
                api.getUserCollections(dataParam.getString(Constants.COLLECTION_DATA_PARAM_USERNAME), params.requestedStartPosition, params.requestedLoadSize)
                        .enqueue(getCollectionListLoadInitialCallback(params, callback))
            }
            Constants.COLLECTION_DATA_TYPE_SEARCH -> {
                api.searchCollections(dataParam.getString(Constants.COLLECTION_DATA_PARAM_SEARCH_TEXT), params.requestedStartPosition, params.requestedLoadSize)
                        .enqueue(getSearchResultLoadInitialCallback(params, callback))
            }
        }
    }


}