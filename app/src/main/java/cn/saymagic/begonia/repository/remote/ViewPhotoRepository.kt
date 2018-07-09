package cn.saymagic.begonia.repository.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.Unsplash
import cn.saymagic.begonia.sdk.core.pojo.Download
import cn.saymagic.begonia.sdk.core.pojo.Photo
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ViewPhotoRepository {

    private val service = Unsplash.getInstance().service!!

    private var retry: Runnable? = null

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val photoBitmapLiveData: MutableLiveData<Bitmap> = MutableLiveData()

    val targetList: MutableList<Target> = ArrayList()

    fun getPhoto(photoId: String): PhotoData {
        networkState.postValue(NetworkState.LOADING)
        service.getPhoto(photoId).enqueue(object : Callback<Photo> {
            override fun onFailure(call: Call<Photo>?, t: Throwable?) {
                networkState.postValue(NetworkState.error(t))
                retry = Runnable {
                    getPhoto(photoId)
                }
            }

            override fun onResponse(call: Call<Photo>?, response: Response<Photo>?) {
                val photo = response?.body()!!
                downloadToBitmap(photo)
            }
        })
        return PhotoData(networkState, photoBitmapLiveData, {
            retry?.run()
        })
    }

    private fun downloadToBitmap(photo: Photo) {
        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                networkState.postValue(NetworkState.error(e))
                retry = Runnable {
                    downloadToBitmap(photo)
                }
                targetList.remove(this)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                networkState.postValue(NetworkState.LOADED)
                photoBitmapLiveData.postValue(bitmap)
                retry = null
                targetList.remove(this)
            }

        }
        targetList.add(target)
        Picasso.get().load(photo.urls?.regular ?: "").into(target)
    }

    fun onCleared() {
        targetList.clear()
    }

}

data class PhotoData(val networkState: LiveData<NetworkState>, val photo: LiveData<Bitmap>, val retry: () -> Unit)

