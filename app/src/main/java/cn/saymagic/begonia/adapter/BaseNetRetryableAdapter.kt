package cn.saymagic.begonia.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.saymagic.begonia.R
import cn.saymagic.begonia.fundamental.into
import cn.saymagic.begonia.repository.NetworkState
import cn.saymagic.begonia.sdk.core.pojo.Photo
import cn.saymagic.begonia.ui.NetworkStateItemViewHolder
import kotlinx.android.synthetic.main.item_photo.view.*

abstract class BaseNetRetryableAdapter<T> (callback: DiffUtil.ItemCallback<T>, val eventListener: AdapterEventListener<T>, private val retry: () -> Unit) : PagedListAdapter<T, RecyclerView.ViewHolder>(callback) {

    companion object {
        val EVENT_CLICK_IMG = "EVENT_CLICK_IMG"
        val EVENT_CLICK_USER = "EVENT_CLICK_USER"
    }

    var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, {
                retry.invoke()
            })
            else -> onCreateContentViewHolder(parent, viewType)
        }
    }

    abstract fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NetworkStateItemViewHolder) {
            holder.bindTo(networkState)
        } else {
            onBindContentViewHolder(holder, position)
        }
    }

    abstract fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            getItemContentViewType(position)
        }
    }

    abstract fun getItemContentViewType(position: Int): Int

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun changeNetworkState(newNetWorkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetWorkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetWorkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

}
