package cn.saymagic.begonia.viewmodel

import android.app.Application
import android.app.WallpaperManager
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.graphics.Bitmap
import android.os.AsyncTask
import cn.saymagic.begonia.BegoniaApplication
import cn.saymagic.begonia.R
import cn.saymagic.begonia.pojo.AsyncResult
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.repository.remote.ViewPhotoRepository
import cn.saymagic.begonia.util.AsyncTaskGuard
import cn.saymagic.begonia.util.PhotoUtils
import java.io.File

class ViewPhotoModel(application: Application) : AndroidViewModel(application) {

    private val repository = ViewPhotoRepository()

    private val photoId: MutableLiveData<String> = MutableLiveData()

    private val photoData = map(photoId, {
        repository.getPhoto(it)
    })

    val networkState: LiveData<NetworkState> = switchMap(photoData, {
        it.networkState
    })

    val photo: LiveData<Bitmap> = switchMap(photoData, {
        it.photo
    })

    var rerty: (() -> Unit)? = {
        photoData?.value?.retry?.invoke()
    }

    val toastTip: MutableLiveData<String> = MutableLiveData()

    val asyncTaskGuard: AsyncTaskGuard = AsyncTaskGuard()

    fun getPhoto(photoId: String) {
        if (this.photoId.value == photoId) {
            return
        }
        this.photoId.value = photoId
    }

    fun saveToPhotoAlbum() {
        if (photo.value == null) {
            return
        } else {
            asyncTaskGuard.submit(object : AsyncTask<Bitmap, Void, AsyncResult>() {
                override fun doInBackground(vararg params: Bitmap?): AsyncResult {
                    return try {
                        AsyncResult.success(PhotoUtils.saveBitmapIntoSystem(getApplication<BegoniaApplication>(), params[0]))
                    } catch (err: Throwable) {
                        AsyncResult.error(err)
                    }
                }

                override fun onPostExecute(result: AsyncResult) {
                    if (result.isSuccess()) {
                        toastTip.postValue(getApplication<BegoniaApplication>().getString(R.string.save_to_system_success, (result.result as File).absolutePath))
                    } else {
                        toastTip.postValue(result.error!!.message)
                    }
                    asyncTaskGuard.remove(this)
                }

            }.execute(photo.value))
        }
    }

    fun setWallpaper() {
        if (photo.value == null) {
            return
        } else {
            asyncTaskGuard.submit(object : AsyncTask<Bitmap, Void, AsyncResult>() {
                override fun doInBackground(vararg params: Bitmap?): AsyncResult {
                    return try {
                        WallpaperManager.getInstance(getApplication()).setBitmap(photo.value)
                        AsyncResult.success()
                    } catch (err: Throwable) {
                        AsyncResult.error(err)
                    }
                }

                override fun onPostExecute(result: AsyncResult) {
                    if (result.isSuccess()) {
                        toastTip.postValue(getApplication<BegoniaApplication>().getString(R.string.set_wallpaper_success))
                    } else {
                        toastTip.postValue(result.error!!.message)
                    }
                    asyncTaskGuard.remove(this)
                }

            }.execute(photo.value))
        }
    }

    override fun onCleared() {
        super.onCleared()
        repository.onCleared()
        asyncTaskGuard.onCleared()
    }
}