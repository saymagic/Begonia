package cn.saymagic.begonia.util

import android.support.v7.util.DiffUtil
import cn.saymagic.begonia.sdk.core.pojo.Collection
import cn.saymagic.begonia.sdk.core.pojo.User

class UserDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean = oldItem?.updated_at == newItem?.updated_at

}