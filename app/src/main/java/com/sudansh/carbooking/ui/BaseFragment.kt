package com.sudansh.carbooking.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.sudansh.carbooking.R
import org.koin.android.architecture.ext.sharedViewModel

abstract class BaseFragment : Fragment() {
    internal val parentView by lazy { RelativeLayout(context) }
    internal val viewModel by sharedViewModel<MapsViewModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        parentView.id = R.id.parent_view
        return parentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
    }

    abstract fun initUI()

}
