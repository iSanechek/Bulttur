package com.isanechek.balttur

import android.app.Application
import com.isanechek.balttur.di.appModule
import com.isanechek.balttur.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import saschpe.android.customtabs.CustomTabsActivityLifecycleCallbacks

class BaltturApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaltturApplication)
            modules(listOf(appModule, dataModule))
        }

        registerActivityLifecycleCallbacks(CustomTabsActivityLifecycleCallbacks())
    }
}