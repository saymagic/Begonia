package cn.saymagic.begonia.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class PhotoListActivityViewModel : ViewModel() {

    val shadowLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val searchLiveData: MutableLiveData<String> = MutableLiveData()

    fun onSearchChanged(text: String = "", start: Boolean = false, finished : Boolean = false){
        if (start) {
            shadowLiveData.value = true
        }
        if (finished) {
            shadowLiveData.value = false
            searchLiveData.value = text
        }
    }
}