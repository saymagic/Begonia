package cn.saymagic.begonia.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.Transformations.switchMap
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.repository.datasource.CollectionListDataSource
import cn.saymagic.begonia.repository.datasource.CollectionListDataSourceFactory
import cn.saymagic.begonia.repository.datasource.PhotoListDataSource
import cn.saymagic.begonia.repository.datasource.PhotoListDataSourceFactory
import cn.saymagic.begonia.sdk.core.pojo.Photo
import java.util.concurrent.Executors

class PhotoListViewModel(application: Application) : AndroidViewModel(application) {

    private val paramData : MutableLiveData<DataSourceParam> = MutableLiveData()

    private val sourceFactoryList: LiveData<PhotoListDataSourceFactory> = Transformations.map(paramData, {
        PhotoListDataSourceFactory(it)
    })

    private val sourceLiveData : LiveData<PhotoListDataSource> = Transformations.switchMap(sourceFactoryList, {
        it.sourceLiveData
    })

    val networkState : LiveData<NetworkState> = Transformations.switchMap(sourceLiveData, {
        it.networkState
    })

    val photoList: LiveData<PagedList<Photo>> = Transformations.switchMap(sourceFactoryList, {
        LivePagedListBuilder(it,
                PagedList.Config.Builder().setEnablePlaceholders(false)
                        .setPageSize(10)
                        .setInitialLoadSizeHint(20).build()).setFetchExecutor(Executors.newCachedThreadPool())
                .build()
    })

    fun getPhotos(param: DataSourceParam) {
        if (paramData.value == param) {
            return
        }
        paramData.value = param
    }

    fun retry() {
        sourceLiveData?.value?.retry?.run()
    }

}
