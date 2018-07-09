package cn.saymagic.begonia.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.saymagic.begonia.R
import cn.saymagic.begonia.fundamental.into
import cn.saymagic.begonia.sdk.core.pojo.User

class UserListAdapter(callback: DiffUtil.ItemCallback<User>, eventListener: AdapterEventListener<User>, retry: () -> Unit) : BaseNetRetryableAdapter<User>(callback, eventListener, retry) {

    companion object {
        const val EVENT_CLICK_USER = "EVENT_CLICK_USER"
    }

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, null), eventListener)
    }

    override fun onBindContentViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserViewHolder) {
            val user = getItem(position)
            if (user != null) {
                holder.bindTo(user)
            }
        }
    }

    override fun getItemContentViewType(position: Int): Int {
        return R.layout.item_user
    }

}

class UserViewHolder(itemView: View, private val eventListener: AdapterEventListener<User>) : RecyclerView.ViewHolder(itemView) {

    lateinit var user: User
    var userName: TextView
    var userDesc: TextView
    var userAvatar: ImageView

    init {
        val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        itemView.layoutParams = lp
        itemView.setOnClickListener {
            eventListener.onEvent(UserListAdapter.EVENT_CLICK_USER, user, itemView)
        }
        userName = itemView.findViewById<TextView>(R.id.user_name)
        userDesc = itemView.findViewById<TextView>(R.id.user_desc)
        userAvatar = itemView.findViewById<ImageView>(R.id.user_avatar)
    }

    fun bindTo(user: User) {
        this.user = user
        userName.text = user.name
        userDesc.text = "${user.location ?: ""} ${user.badge?.slug ?: ""} "
        if (user.profileImage?.large != null) {
            userAvatar.into(user.profileImage.large, true)
        } else {
            userAvatar.setImageResource(R.drawable.circle)
        }
    }

}