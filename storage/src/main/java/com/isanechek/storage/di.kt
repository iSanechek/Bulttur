package com.isanechek.storage

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val storageModule = module {

    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            StorageDatabase::class.java,
            "Balttur.db")
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    factory {
        get<StorageDatabase>().homeToursInfoDa()
    }

    factory {
        get<StorageDatabase>().homeContentDao()
    }

}