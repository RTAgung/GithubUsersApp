package com.example.githubusersapp.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubusersapp.R
import com.example.githubusersapp.view.fragment.FollowersFragment
import com.example.githubusersapp.view.fragment.FollowingFragment

class SectionsPagerAdapter(
    mContext: Context,
    fm: FragmentManager,
    private val username: String,
    numOfFollowers: Int?,
    numOfFollowing: Int?
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTittles = arrayOf(
        mContext.resources.getString(R.string.followers, numOfFollowers),
        mContext.resources.getString(R.string.following, numOfFollowing)
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstance(username)
            1 -> fragment = FollowingFragment.newInstance(username)
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return tabTittles[position]
    }

    override fun getCount(): Int = tabTittles.size
}