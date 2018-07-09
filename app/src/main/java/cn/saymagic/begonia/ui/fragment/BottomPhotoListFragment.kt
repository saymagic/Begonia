package cn.saymagic.begonia.ui.fragment

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.saymagic.begonia.pojo.DataSourceParam

private const val ARG_PHOTO_DATA_SOURCE_PARAM = "arg_photo_data_source_param"

class BottomPhotoListFragment : BottomSheetDialogFragment() {

    private lateinit var DataSourceParam: DataSourceParam

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            DataSourceParam = it.getParcelable(ARG_PHOTO_DATA_SOURCE_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return PhotoListManager.onCreateView(this, activity!!, DataSourceParam, inflater, container, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(DataSourceParam: DataSourceParam) = BottomPhotoListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PHOTO_DATA_SOURCE_PARAM, DataSourceParam)
            }
        }
    }
}