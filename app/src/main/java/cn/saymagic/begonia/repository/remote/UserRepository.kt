package cn.saymagic.begonia.repository.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.Unsplash
import cn.saymagic.begonia.sdk.core.pojo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    val service = Unsplash.getInstance().service

    fun getUser(userName: String) : UserProfileData{
        val networkState: MutableLiveData<NetworkState> = MutableLiveData()
        val user: MutableLiveData<User> = MutableLiveData()
        networkState.postValue(NetworkState.LOADING)
        service.getUser(userName).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
            }
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response?.isSuccessful == true) {
                    user.postValue(response?.body())
                    networkState.postValue(NetworkState.LOADED)
                } else {
                    networkState.postValue(NetworkState.error(null))
                }
            }
        })
        return UserProfileData(user, networkState)
    }

}

class UserProfileData(val user: LiveData<User>, val networkState: LiveData<NetworkState>)