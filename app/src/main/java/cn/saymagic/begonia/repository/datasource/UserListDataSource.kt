package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.PositionalDataSource
import cn.saymagic.begonia.exception.UnexpectedCodeException
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.Unsplash
import cn.saymagic.begonia.sdk.core.api.UnsplashService
import cn.saymagic.begonia.sdk.core.pojo.SearchResult
import cn.saymagic.begonia.sdk.core.pojo.User
import cn.saymagic.begonia.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserListDataSource(private val dataParam: DataSourceParam) : PositionalDataSource<User>() {

    private val api: UnsplashService = Unsplash.getInstance().service

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    var retry: Runnable? = null

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<User>) {
        networkState.postValue(NetworkState.LOADING)
        when (dataParam.type) {
            Constants.USER_DATA_TYPE_SEARCH -> api.searchUsers(dataParam.getString(Constants.USER_DATA_TYPE_PARAM_SEARCH_TEXT), params.requestedStartPosition, params.pageSize)
                    .enqueue(getSearchResultLoadInitialCallback(params, callback))
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<User>) {
        networkState.postValue(NetworkState.LOADING)
        when (dataParam.type) {
            Constants.USER_DATA_TYPE_SEARCH -> api.searchUsers(dataParam.getString(Constants.USER_DATA_TYPE_PARAM_SEARCH_TEXT), params.startPosition, params.loadSize)
                    .enqueue(getSearchResultLoadRangeCallback(params, callback))
        }
    }

    private fun getSearchResultLoadInitialCallback(params: PositionalDataSource.LoadInitialParams, callback: PositionalDataSource.LoadInitialCallback<User>): Callback<SearchResult<User>>? {
        return object : Callback<SearchResult<User>> {
            override fun onFailure(call: Call<SearchResult<User>>?, t: Throwable?) {
                retry = Runnable {
                    loadInitial(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<SearchResult<User>>?, response: Response<SearchResult<User>>?) {
                retry = if (response?.isSuccessful == true) {
                    val total = try {
                        response?.body()?.total
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

    private fun getSearchResultLoadRangeCallback(params: PositionalDataSource.LoadRangeParams, callback: PositionalDataSource.LoadRangeCallback<User>): Callback<SearchResult<User>>? {
        return object : Callback<SearchResult<User>> {
            override fun onFailure(call: Call<SearchResult<User>>?, t: Throwable?) {
                retry = Runnable {
                    loadRange(params, callback)
                }
                networkState.postValue(NetworkState.error(t))
            }

            override fun onResponse(call: Call<SearchResult<User>>?, response: Response<SearchResult<User>>?) {
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


}