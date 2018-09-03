package com.sudansh.carbooking

import android.app.Application
import com.facebook.stetho.Stetho
import com.sudansh.carbooking.di.appModule
import com.sudansh.carbooking.di.localModule
import com.sudansh.carbooking.di.remoteModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build())

        }
        startKoin(listOf(appModule, remoteModule, localModule))
    }

}