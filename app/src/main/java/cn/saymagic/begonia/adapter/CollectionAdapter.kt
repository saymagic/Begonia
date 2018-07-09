package cn.saymagic.begonia.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.saymagic.begonia.R
import cn.saymagic.begonia.fundamental.into
import cn.saymagic.begonia.sdk.core.pojo.Collection
import kotlinx.android.synthetic.main.item_collection.view.*

class CollectionAdapter(callback: DiffUtil.ItemCallback<Collection>, eventListener: AdapterEventListener<Collection>, retry: () -> Unit) : BaseNetRetryableAdapter<Collection>(callback, eventListener, retry) {

    override fun getItemContentViewType(position: Int): Int {
        return R.layout.item_collection
    }

    companion object {
        const val EVENT_CLICK_THUMB = "EVENT_CLICK_THUMB"
    }
    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CollectionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_collection, null), eventListener)
    }

    override fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CollectionViewHolder) {
            val collection = getItem(position)
            if (collection != null) {
                holder.bindTo(collection)
            }
        }
    }

}


class CollectionViewHolder(itemView: View, val eventListener: AdapterEventListener<Collection>) : RecyclerView.ViewHolder(itemView) {

    lateinit var collection: Collection

    init {
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        itemView.layoutParams = lp
        itemView.setOnClickListener {
            eventListener.onEvent(CollectionAdapter.EVENT_CLICK_THUMB, collection, itemView)
        }
    }

    fun bindTo(collection: Collection) {
        this.collection = collection
        itemView.collectionThumb.into(collection.coverPhoto?.urls?.regular?:"")
        itemView.collectionName.text = collection.title
        itemView.collectionDesc.text = "${collection.totalPhotos} photos. by ${collection.user?.name}"
    }

}