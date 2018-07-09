package cn.saymagic.begonia.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.app.Fragment
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
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import kotlinx.android.synthetic.main.fragment_photo_list.view.*


private const val ARG_PHOTO_DATA_SOURCE_PARAM = "arg_photo_data_source_param"

class PhotoListFragment : Fragment() {

    private lateinit var DataSourceParam: DataSourceParam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            DataSourceParam = it.getParcelable(ARG_PHOTO_DATA_SOURCE_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return PhotoListManager.onCreateView(this, activity!!, DataSourceParam, inflater , container, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(DataSourceParam: DataSourceParam) = PhotoListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PHOTO_DATA_SOURCE_PARAM, DataSourceParam)
            }
        }
    }
}
