package cn.saymagic.begonia.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import cn.saymagic.begonia.repository.remote.UserRepository

class UserProfileModel : ViewModel() {

    private val userName = MutableLiveData<String>()
    private val userResult = Transformations.map(userName, {
        repository.getUser(it)
    })

    val user = Transformations.switchMap(userResult, {
        it.user
    })!!

    val networkState = Transformations.switchMap(userResult, {
        it.networkState
    })!!

    val repository = UserRepository()

    fun getUser(name: String) {
        if (userName.value == name) {
            return
        }
        userName.value = name
    }

}