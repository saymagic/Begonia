package cn.saymagic.begonia.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.repository.datasource.UserListDataSource
import cn.saymagic.begonia.repository.datasource.UserListDataSourceFactory
import cn.saymagic.begonia.sdk.core.pojo.User
import java.util.concurrent.Executors

class UserViewModel : ViewModel() {

    private val paramData : MutableLiveData<DataSourceParam> = MutableLiveData()

    private val sourceFactoryList: LiveData<UserListDataSourceFactory> = Transformations.map(paramData, {
        UserListDataSourceFactory(it)
    })

    private val sourceLiveData : LiveData<UserListDataSource> = Transformations.switchMap(sourceFactoryList, {
        it.sourceLiveData
    })

    val userList : LiveData<PagedList<User>> = Transformations.switchMap(sourceFactoryList, {
        LivePagedListBuilder(it,
                PagedList.Config.Builder().setEnablePlaceholders(false)
                        .setPageSize(10)
                        .setInitialLoadSizeHint(20).build()).setFetchExecutor(Executors.newCachedThreadPool())
                .build()
    })

    val networkState : LiveData<NetworkState> = Transformations.switchMap(sourceLiveData, {
        it.networkState
    })

    fun getUsers(param: DataSourceParam) {
        if (paramData.value == param) {
            return
        }
        paramData.value = param
    }

    fun retry() {
        sourceLiveData?.value?.retry?.run()
    }

    override fun onCleared() {
        super.onCleared()
    }
}