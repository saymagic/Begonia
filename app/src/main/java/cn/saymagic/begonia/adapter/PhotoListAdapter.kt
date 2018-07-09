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

class PhotoListAdapter(callback: DiffUtil.ItemCallback<Photo>, private val eventListener: AdapterEventListener<Photo>, private val retry: () -> Unit) : PagedListAdapter<Photo, RecyclerView.ViewHolder>(callback) {

    companion object {
        val EVENT_CLICK_IMG = "EVENT_CLICK_IMG"
        val EVENT_CLICK_USER = "EVENT_CLICK_USER"
    }

    var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_photo -> PhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, null), eventListener)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, {
                retry.invoke()
            })
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoViewHolder) {
            val photo = getItem(position)
            if (photo != null) {
                holder.bindTo(photo)
            }
        } else if (holder is NetworkStateItemViewHolder) {
            holder.bindTo(networkState)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.item_photo
        }
    }

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

class PhotoViewHolder(itemView: View, val eventListener: AdapterEventListener<Photo>) : RecyclerView.ViewHolder(itemView) {

    lateinit var photo: Photo

    init {
        itemView.img.setOnClickListener {
            eventListener.onEvent(PhotoListAdapter.EVENT_CLICK_IMG, photo, itemView)
        }
        itemView.text.setOnClickListener {
            eventListener.onEvent(PhotoListAdapter.EVENT_CLICK_USER, photo, itemView)
        }
        itemView.photoUserAvatar.setOnClickListener {
            eventListener.onEvent(PhotoListAdapter.EVENT_CLICK_USER, photo, itemView)
        }
    }

    fun bindTo(photo: Photo) {
        this.photo = photo
        itemView.text.text = photo.user.username
        itemView.img.into(photo.urls?.small ?: "")
        itemView.photoUserAvatar.into(photo.user.profileImage.medium, true)
    }

}