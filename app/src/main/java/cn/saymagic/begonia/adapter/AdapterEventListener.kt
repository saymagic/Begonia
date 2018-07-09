package cn.saymagic.begonia.adapter

import android.view.View

interface AdapterEventListener<T> {

    fun onEvent(eventName: String, value: T, itemView: View)

}