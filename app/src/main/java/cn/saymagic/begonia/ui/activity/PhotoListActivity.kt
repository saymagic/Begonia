package cn.saymagic.begonia.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import cn.saymagic.begonia.R
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.ui.fragment.PhotoListFragment
import cn.saymagic.begonia.util.Constants
import kotlinx.android.synthetic.main.activity_photo.*
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SearchView
import cn.saymagic.begonia.util.UIController
import cn.saymagic.begonia.viewmodel.PhotoListActivityViewModel


class PhotoListActivity : AppCompatActivity() {

    val TAG = "PhotoListActivity"
    lateinit var viewModel: PhotoListActivityViewModel
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PhotoListActivityViewModel::class.java)
        setContentView(R.layout.activity_photo)
        val fragmentManager = supportFragmentManager;
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.main_activity_photo_container, PhotoListFragment.newInstance(DataSourceParam(Constants.PHOTO_DATA_TYPE_NORMAL)))
        transaction.commit()
        setSupportActionBar(mainToolbar)
        viewModel.shadowLiveData.observe(this, Observer {
            mainPhotoShadowView.visibility = if(it == true) View.VISIBLE else View.GONE
        })
        viewModel.searchLiveData.observe(this, Observer {
            if (!TextUtils.isEmpty(it)) {
                UIController.search(this, it?:"")
            }
            searchView.onActionViewCollapsed()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        searchView.queryHint = "photo、user、collection"
        searchView.setOnSearchClickListener {
            viewModel.onSearchChanged("", start = true)
        }

        searchView.setOnCloseListener {
            viewModel.onSearchChanged("", finished = true)
            true
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.onSearchChanged(query ?: "", finished = true)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onSearchChanged(newText ?: "")
                return false
            }
        })
        return true
    }

}

