package cn.saymagic.begonia.util

import android.os.AsyncTask

/**
 * Created by caoyanming on 2018/7/8.
 */
class AsyncTaskGuard {

    val asyncTaskList: MutableList<AsyncTask<*, *, *>> = ArrayList()

    fun submit(task: AsyncTask<*, *, *>) : Boolean = asyncTaskList.add(task)

    fun remove(task: AsyncTask<*, *, *>) :Boolean = asyncTaskList.remove(task)

    fun onCleared() {
        asyncTaskList.forEach {
            if (!it.isCancelled) {
                it.cancel(true)
            }
        }
        asyncTaskList.clear()
    }

}