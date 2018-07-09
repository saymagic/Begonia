package cn.saymagic.begonia.ui.fragment


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cn.saymagic.begonia.R
import cn.saymagic.begonia.adapter.AdapterEventListener
import cn.saymagic.begonia.adapter.CollectionAdapter
import cn.saymagic.begonia.adapter.CollectionAdapter.Companion.EVENT_CLICK_THUMB
import cn.saymagic.begonia.fundamental.SpacesItemDecoration
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.Status
import cn.saymagic.begonia.sdk.core.pojo.Collection
import cn.saymagic.begonia.util.CollectionDiffCallback
import cn.saymagic.begonia.util.Constants
import cn.saymagic.begonia.viewmodel.CollectionListViewModel
import kotlinx.android.synthetic.main.fragment_collection_list.view.*


private const val ARG_COLLECTION_DATA_SOURCE_PARAM = "arg_collection_data_source_param"


class CollectionListFragment : Fragment() {

    private lateinit var dataSourceParam: DataSourceParam


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataSourceParam = it.getParcelable<DataSourceParam>(ARG_COLLECTION_DATA_SOURCE_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val collectionViewModel = ViewModelProviders.of(this).get(CollectionListViewModel::class.java)
        val view =   inflater.inflate(R.layout.fragment_collection_list, container, false)
        val collectionAdapter = CollectionAdapter(CollectionDiffCallback(), object : AdapterEventListener<Collection> {
            override fun onEvent(eventName: String, collection: Collection, itemView: View) {
                when (eventName) {
                    EVENT_CLICK_THUMB -> {
                        BottomPhotoListFragment
                                .newInstance(DataSourceParam(Constants.PHOTO_DATA_TYPE_COLLECTION).putString(Constants.PHOTO_DATA_PARAM_COLLECTION_ID, "${collection.id}"))
                                .show(fragmentManager, "dialog")
                    }
                }
            }
        }, {
            collectionViewModel.retry()
        })
        view.collectionList.layoutManager = LinearLayoutManager(context)
        view.collectionList.adapter = collectionAdapter
        view.collectionList.addItemDecoration(SpacesItemDecoration(16))

        collectionViewModel.collectionList.observe(this, Observer<PagedList<Collection>> {
            collectionAdapter.submitList(it)
        })

        var hadLoaded = false
        collectionViewModel.networkState.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    if(!hadLoaded){
                        if (collectionAdapter.itemCount == 0) {
                            view.collectionListProgressLayout.showEmpty(R.drawable.ic_free_breakfast_black_24dp, getString(R.string.empty_title), getString(R.string.empty_desc))
                        } else {
                            hadLoaded = true
                            view.collectionListProgressLayout.showContent()
                            view.post {
                                view.collectionList.scrollToPosition(0)
                            }
                        }
                    }else {
                        collectionAdapter.changeNetworkState(it)
                    }
                }
                Status.RUNNING -> {
                    if (!hadLoaded) {
                        view.collectionListProgressLayout.showLoading()
                    } else {
                        collectionAdapter.changeNetworkState(it)
                    }
                }
                Status.FAILED -> {
                    if (!hadLoaded) {
                        view.collectionListProgressLayout.showError(R.drawable.ic_error_black_24dp, getString(R.string.error_title), it.msg?.message?:"", getString(R.string.constant_retry),  {
                            collectionViewModel.retry()
                        })
                    } else {
                        collectionAdapter.changeNetworkState(it)
                    }
                }
            }
        })
        collectionViewModel.getCollection(dataSourceParam)
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(dataSourceParam: DataSourceParam) =
                CollectionListFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_COLLECTION_DATA_SOURCE_PARAM, dataSourceParam)
                    }
                }
    }
}
