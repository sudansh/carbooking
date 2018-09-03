package com.sudansh.carbooking.di

import com.sudansh.carbooking.ui.MapsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext


val appModule = applicationContext {

    viewModel { MapsViewModel(get()) }
}