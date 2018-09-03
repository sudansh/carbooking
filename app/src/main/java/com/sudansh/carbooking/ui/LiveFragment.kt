package com.sudansh.carbooking.ui

import android.widget.CheckBox
import android.widget.RelativeLayout.BELOW
import android.widget.RelativeLayout.LayoutParams
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

    override fun initUI() {
        parentView.addView(checkIsOnTrip)
    }
}