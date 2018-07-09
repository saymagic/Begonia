package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.sdk.core.pojo.Collection

class CollectionListDataSourceFactory(private val dataParam: DataSourceParam) : DataSource.Factory<Int, Collection>() {

    val sourceLiveListData : MutableLiveData<CollectionListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Collection> {
        val source = CollectionListDataSource(dataParam)
        sourceLiveListData.postValue(source)
        return source
    }

}