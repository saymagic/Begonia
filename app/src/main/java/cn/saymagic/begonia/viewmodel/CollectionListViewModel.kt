package cn.saymagic.begonia.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.repository.datasource.CollectionListDataSource
import cn.saymagic.begonia.repository.datasource.CollectionListDataSourceFactory
import cn.saymagic.begonia.sdk.core.pojo.Collection
import java.util.concurrent.Executors

class CollectionListViewModel : ViewModel() {

    private val paramData : MutableLiveData<DataSourceParam> = MutableLiveData()

    private val sourceFactoryList: LiveData<CollectionListDataSourceFactory> = Transformations.map(paramData, {
         CollectionListDataSourceFactory(it)
    })

    private val sourceLiveListData : LiveData<CollectionListDataSource> = Transformations.switchMap(sourceFactoryList, {
        it.sourceLiveListData
    })

    val collectionList : LiveData<PagedList<Collection>> = Transformations.switchMap(sourceFactoryList, {
         LivePagedListBuilder(it,
                PagedList.Config.Builder().setEnablePlaceholders(false)
                        .setPageSize(10)
                        .setInitialLoadSizeHint(20).build()).setFetchExecutor(Executors.newCachedThreadPool())
                .build()
    })

    val networkState : LiveData<NetworkState> = Transformations.switchMap(sourceLiveListData, {
        it.networkState
    })

    fun getCollection(param: DataSourceParam) {
        if (paramData.value == param) {
            return
        }
        paramData.value = param
    }

    fun retry() {
        sourceLiveListData?.value?.retry?.run()
    }

    override fun onCleared() {
        super.onCleared()
    }
}