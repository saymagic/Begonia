package cn.saymagic.begonia.util

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import cn.saymagic.begonia.ui.activity.SearchResultsActivity
import cn.saymagic.begonia.ui.activity.UserProfileActivity
import cn.saymagic.begonia.ui.activity.ViewPhotoActivity

object UIController {

    fun viewPhoto(context: Context, photoId: String) {
        val intent = Intent(context, ViewPhotoActivity::class.java)
        intent.putExtra(Constants.PHOTO_ID, photoId)
        context.startActivity(intent)
    }

    fun viewUser(context: Context, userName: String) {
        val intent = Intent(context, UserProfileActivity::class.java)
        intent.putExtra(Constants.USER_NAME, userName)
        context.startActivity(intent)
    }

    fun search(context: Context, searchText: String) {
        val intent = Intent(context, SearchResultsActivity::class.java)
        intent.action = Intent.ACTION_SEARCH
        intent.putExtra(SearchManager.QUERY, searchText)
        context.startActivity(intent)
    }
}