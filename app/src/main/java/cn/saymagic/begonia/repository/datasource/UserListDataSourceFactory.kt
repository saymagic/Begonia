package cn.saymagic.begonia.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.sdk.core.pojo.Collection
import cn.saymagic.begonia.sdk.core.pojo.User

class UserListDataSourceFactory(private val dataParam: DataSourceParam) : DataSource.Factory<Int, User>() {

    val sourceLiveData : MutableLiveData<UserListDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, User> {
        val source = UserListDataSource(dataParam)
        sourceLiveData.postValue(source)
        return source
    }

}