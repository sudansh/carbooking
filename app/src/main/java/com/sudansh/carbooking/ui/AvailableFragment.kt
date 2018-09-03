package com.sudansh.carbooking.ui

import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout.BELOW
import android.widget.RelativeLayout.LayoutParams
import com.sudansh.carbooking.R
import com.sudansh.carbooking.util.minSlots

class AvailableFragment : BaseFragment() {
    private val timeSlots by lazy {
        RadioGroup(activity).apply {
            id = R.id.chipgroup_id
            layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                addRule(BELOW, R.id.tab_id)
            }
            setBackgroundResource(R.drawable.bg_gradiant)
            setPadding(16, 16, 16, 16)
            setOnCheckedChangeListener { _, position ->
                viewModel.getAvailabilityFromSlot(position - 1)
            }
        }.also {
            addChilds(it)
        }
    }

    override fun initUI() {
        parentView.addView(timeSlots)
        viewModel.getAvailabilityFromSlot(0)
    }

    private fun addChilds(radioGroup: RadioGroup) {
        minSlots.forEach {
            RadioButton(activity).apply {
                text = it.second
                setPadding(16, 16, 16, 16)
            }.also { radioButton ->
                radioGroup.addView(radioButton)
            }
        }
    }
}