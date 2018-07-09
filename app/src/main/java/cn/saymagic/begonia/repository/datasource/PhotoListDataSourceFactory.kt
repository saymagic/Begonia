package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.sdk.core.pojo.Photo

class PhotoListDataSourceFactory(val dataParam: DataSourceParam) : DataSource.Factory<Int, Photo>() {

    val sourceLiveData : MutableLiveData<PhotoListDataSource>  = MutableLiveData()

    override fun create(): DataSource<Int, Photo> {
        val source = PhotoListDataSource(dataParam)
        sourceLiveData.postValue(source)
        return source
    }

}