package cn.saymagic.begonia.util

import android.support.v7.util.DiffUtil
import cn.saymagic.begonia.sdk.core.pojo.Photo

class PhotoDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo?, newItem: Photo?): Boolean = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Photo?, newItem: Photo?): Boolean = oldItem?.updatedAt == newItem?.updatedAt

}