package com.sudansh.carbooking.ui

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.sudansh.carbooking.R

class CarPagerAdapter(private val activity: Activity, fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment = when (position) {
        0 -> LiveFragment()
        1 -> AvailableFragment()
        else -> LiveFragment()
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): String = when (position) {
        0 -> activity.getString(R.string.tab_title_live)
        1 -> activity.getString(R.string.tab_title_available)
        else -> activity.getString(R.string.tab_title_live)
    }
}
