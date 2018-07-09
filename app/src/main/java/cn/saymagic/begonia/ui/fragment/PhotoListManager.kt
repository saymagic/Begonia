package cn.saymagic.begonia.ui.fragment

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.saymagic.begonia.R
import cn.saymagic.begonia.adapter.AdapterEventListener
import cn.saymagic.begonia.adapter.PhotoListAdapter
import cn.saymagic.begonia.fundamental.SpacesItemDecoration
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.Status
import cn.saymagic.begonia.sdk.core.pojo.Photo
import cn.saymagic.begonia.util.PhotoDiffCallback
import cn.saymagic.begonia.util.UIController
import cn.saymagic.begonia.viewmodel.PhotoListViewModel
import kotlinx.android.synthetic.main.fragment_photo_list.view.*

object PhotoListManager {

    fun onCreateView(fragment: Fragment, activity: FragmentActivity, dataSourceParam : DataSourceParam, inflater: LayoutInflater, container: ViewGroup?,
                     savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_photo_list, container, false)
        val photoViewModel = ViewModelProviders.of(fragment).get(PhotoListViewModel::class.java)
        val photoAdapter = PhotoListAdapter(PhotoDiffCallback(), object : AdapterEventListener<Photo> {
            override fun onEvent(eventName: String, photo: Photo, itemView: View) {
                when (eventName) {
                    PhotoListAdapter.EVENT_CLICK_IMG -> activity?.let { UIController.viewPhoto(it, photo.id) }
                    PhotoListAdapter.EVENT_CLICK_USER -> activity?.let { UIController.viewUser(it, photo.user.username) }
                }
            }
        }, {
            photoViewModel.retry()
        })
        view.photoList.adapter = photoAdapter
        view.photoList.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        view.photoList.addItemDecoration(SpacesItemDecoration(16))

        photoViewModel.photoList.observe(fragment, Observer<PagedList<Photo>> {
            photoAdapter.submitList(it)
        })
        var hadLoaded = false
        photoViewModel.networkState.observe(fragment, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    if(!hadLoaded){
                        if (photoAdapter.itemCount == 0) {
                            view.photoListProgressLayout.showEmpty(R.drawable.ic_free_breakfast_black_24dp, activity.getString(R.string.empty_title),  activity.getString(R.string.empty_desc))
                        } else {
                            hadLoaded = true
                            view.photoListProgressLayout.showContent()
                            view.post {
                                view.photoList.scrollToPosition(0)
                            }
                        }
                    }else {
                        photoAdapter.changeNetworkState(it)
                    }
                }
                Status.RUNNING -> {
                    if (!hadLoaded) {
                        view.photoListProgressLayout.showLoading()
                    } else {
                        photoAdapter.changeNetworkState(it)
                    }
                }
                Status.FAILED -> {
                    if (!hadLoaded) {
                        view.photoListProgressLayout.showError(R.drawable.ic_error_black_24dp,  activity.getString(R.string.error_title), it.msg?.message?:"",  activity.getString(R.string.constant_retry),  {
                            photoViewModel.retry()
                        })
                    } else {
                        photoAdapter.changeNetworkState(it)
                    }
                }
            }
        })
        photoViewModel.getPhotos(dataSourceParam)
        return view
    }
}