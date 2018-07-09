package cn.saymagic.begonia.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import cn.saymagic.begonia.pojo.DataSourceParam
import cn.saymagic.begonia.ui.fragment.PhotoListFragment
import cn.saymagic.begonia.util.Constants

class CommonFragmentPageAdapter(fm: FragmentManager, private val titleSource: Array<String>, private val fragmentCreator : (Int) -> Fragment) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragmentCreator.invoke(position)

    override fun getCount(): Int {
        return titleSource.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleSource[position]
    }
}