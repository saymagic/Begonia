package cn.saymagic.begonia.util

import android.support.v7.util.DiffUtil
import cn.saymagic.begonia.sdk.core.pojo.Collection
import cn.saymagic.begonia.sdk.core.pojo.Photo

class CollectionDiffCallback : DiffUtil.ItemCallback<Collection>() {

    override fun areItemsTheSame(oldItem: Collection?, newItem: Collection?): Boolean = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Collection?, newItem: Collection?): Boolean = oldItem?.updatedAt == newItem?.updatedAt

}