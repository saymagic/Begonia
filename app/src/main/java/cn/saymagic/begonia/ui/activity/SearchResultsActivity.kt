package cn.saymagic.begonia.ui.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cn.saymagic.begonia.R
import android.app.SearchManager
import android.content.Intent
import cn.saymagic.begonia.adapter.CommonFragmentPageAdapter
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.ui.fragment.CollectionListFragment
import cn.saymagic.begonia.ui.fragment.PhotoListFragment
import cn.saymagic.begonia.ui.fragment.UserListFragment
import cn.saymagic.begonia.util.Constants
import kotlinx.android.synthetic.main.activity_search_results.*


class SearchResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (query == null) {
                finish()
                return
            }
            searchValueTabLayout.setupWithViewPager(searchValuePager)
            searchValuePager.adapter = CommonFragmentPageAdapter(supportFragmentManager, resources.getStringArray(R.array.search_result_item_titles)){
                when (it) {
                    0 -> PhotoListFragment.newInstance(DataSourceParam(Constants.PHOTO_DATA_TYPE_SEARCH).putString(Constants.PHOTO_DATA_PARAM_SEARCH_TEXT, query))
                    1 -> CollectionListFragment.newInstance(DataSourceParam(Constants.COLLECTION_DATA_TYPE_SEARCH).putString(Constants.COLLECTION_DATA_PARAM_SEARCH_TEXT, query))
                    else -> UserListFragment.newInstance(DataSourceParam(Constants.USER_DATA_TYPE_SEARCH).putString(Constants.USER_DATA_TYPE_PARAM_SEARCH_TEXT, query))
                }
            }
        } else {
            finish()
            return
        }
    }

}
