package com.isanechek.balttur

import android.app.Application
import com.isanechek.balttur.di.appModule
import com.isanechek.balttur.di.dataModule
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaltturApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaltturApplication)
            modules(listOf(appModule, dataModule))

            val config = YandexMetricaConfig.newConfigBuilder("9102e988-dcd0-4737-875b-79b7de63e8ba")
                .withSessionTimeout(60)
                .withAppVersion(BuildConfig.VERSION_NAME)
                .build()

            YandexMetrica.activate(this@BaltturApplication, config)
            YandexMetrica.enableActivityAutoTracking(this@BaltturApplication)
        }
    }
}