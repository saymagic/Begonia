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
import cn.saymagic.begonia.adapter.UserListAdapter
import cn.saymagic.begonia.fundamental.SpacesItemDecoration
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.repository.Status
import cn.saymagic.begonia.sdk.core.pojo.User
import cn.saymagic.begonia.util.UIController
import cn.saymagic.begonia.util.UserDiffCallback
import cn.saymagic.begonia.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_list.view.*


private const val ARG_USER_DATA_SOURCE_PARAM = "arg_user_data_source_param"

class UserListFragment : Fragment() {

    private lateinit var dataSourceParam: DataSourceParam


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dataSourceParam = it.getParcelable<DataSourceParam>(ARG_USER_DATA_SOURCE_PARAM)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_user_list, container, false)
        val userAdapter = UserListAdapter(UserDiffCallback(), object : AdapterEventListener<User> {
            override fun onEvent(eventName: String, user: User, itemView: View) {
                when (eventName) {
                    UserListAdapter.EVENT_CLICK_USER -> {
                        UIController.viewUser(context!!, user.username)
                    }
                }
            }
        }, {
            userViewModel.retry()
        })
        view.userList.layoutManager = LinearLayoutManager(context)
        view.userList.adapter = userAdapter
        view.userList.addItemDecoration(SpacesItemDecoration(16))

        userViewModel.userList.observe(this, Observer<PagedList<User>> {
            userAdapter.submitList(it)
        })

        var hadLoaded = false
        userViewModel.networkState.observe(this, Observer {
            when (it?.status) {
                Status.SUCCESS -> {
                    if (!hadLoaded) {
                        if (userAdapter.itemCount == 0) {
                            view.userListProgressLayout.showEmpty(R.drawable.ic_free_breakfast_black_24dp, getString(R.string.empty_title), getString(R.string.empty_desc))
                        } else {
                            hadLoaded = true
                            view.userListProgressLayout.showContent()
                            view.post {
                                view.userList.scrollToPosition(0)
                            }
                        }
                    } else {
                        userAdapter.changeNetworkState(it)
                    }
                }
                Status.RUNNING -> {
                    if (!hadLoaded) {
                        view.userListProgressLayout.showLoading()
                    } else {
                        userAdapter.changeNetworkState(it)
                    }
                }
                Status.FAILED -> {
                    if (!hadLoaded) {
                        view.userListProgressLayout.showError(R.drawable.ic_error_black_24dp, getString(R.string.error_title), it.msg?.message
                                ?: "", getString(R.string.constant_retry), {
                            userViewModel.retry()
                        })
                    } else {
                        userAdapter.changeNetworkState(it)
                    }
                }
            }
        })
        userViewModel.getUsers(dataSourceParam)
        return view
    }


    companion object {
        @JvmStatic
        fun newInstance(dataSourceParam: DataSourceParam) =
                UserListFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_USER_DATA_SOURCE_PARAM, dataSourceParam)
                    }
                }
    }
}
