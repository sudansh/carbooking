package com.sudansh.carbooking.ui

import android.support.design.widget.FloatingActionButton
import android.widget.CheckBox
import android.widget.RelativeLayout.*
import android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT
import com.sudansh.carbooking.R

class LiveFragment : BaseFragment() {

    private val checkIsOnTrip by lazy {
        CheckBox(activity).apply {
            id = R.id.cb_live_id
            setText(R.string.hide_on_trip)
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                addRule(BELOW, R.id.tab_id)
                setPadding(16, 16, 16, 16)
            }
            setBackgroundResource(R.drawable.bg_gradiant)
            setOnCheckedChangeListener { _, isChecked -> viewModel.hideOnTrip(isChecked) }
        }
    }

    private val btnRefresh by lazy {
        FloatingActionButton(activity).apply {
            id = R.id.fab_id
            setImageResource(R.drawable.ic_refresh)
            layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                addRule(ALIGN_PARENT_START)
                addRule(ALIGN_PARENT_BOTTOM)
                setPadding(16, 16, 16, 16)
                setMargins(16, 16, 16, 150)
            }
            setOnClickListener { viewModel.getLive(true) }
        }
    }

    override fun initUI() {
        parentView.addView(checkIsOnTrip)
        parentView.addView(btnRefresh)
    }
}