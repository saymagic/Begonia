package cn.saymagic.begonia.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import cn.saymagic.begonia.R
import cn.saymagic.begonia.adapter.CommonFragmentPageAdapter
import cn.saymagic.begonia.fundamental.into
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.ui.fragment.CollectionListFragment
import cn.saymagic.begonia.ui.fragment.PhotoListFragment
import cn.saymagic.begonia.util.Constants
import cn.saymagic.begonia.viewmodel.UserProfileModel
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val name = intent.getStringExtra(Constants.USER_NAME)
        if (name == null) {
            finish()
            return
        }
        val userName = findViewById<TextView>(R.id.user_name)
        val userDesc = findViewById<TextView>(R.id.user_desc)
        val userAvatar = findViewById<ImageView>(R.id.user_avatar)
        userName.text = name
        userAvatar.setImageResource(R.drawable.circle)
        val userModel = ViewModelProviders.of(this).get(UserProfileModel::class.java)
        userModel.user.observe(this, Observer {
            it?.let {
                userName.text = it.name
                userDesc.text = "${it.location?:""} ${it.badge?.slug?:""} "
                if (it.profileImage?.large != null) {
                    userAvatar.into(it.profileImage.large, true)
                }
            }
        })
        userModel.getUser(name)
        userValueTabLayout.setupWithViewPager(userValuePager)
        userValuePager.adapter = CommonFragmentPageAdapter(supportFragmentManager, resources.getStringArray(R.array.user_profile_item_titles)){
             when (it) {
                0 -> PhotoListFragment.newInstance(DataSourceParam(Constants.PHOTO_DATA_TYPE_USER).putString(Constants.PHOTO_DATA_PARAM_USERNAME, name))
                1 -> PhotoListFragment.newInstance(DataSourceParam(Constants.PHOTO_DATA_TYPE_USER_LIKED).putString(Constants.PHOTO_DATA_PARAM_USERNAME, name))
                else -> CollectionListFragment.newInstance(DataSourceParam(Constants.COLLECTION_DATA_TYPE_USER).putString(Constants.COLLECTION_DATA_PARAM_USERNAME, name))
            }
        }
    }
}
