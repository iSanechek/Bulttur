package com.isanechek.balttur.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.NetworkClientImpl
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.data.PlatformContractImpl
import com.isanechek.balttur.data.database.Database
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.data.parsers.HomePageParserImpl
import com.isanechek.balttur.utils.PrefUtils
import com.isanechek.balttur.utils.PrefUtilsImpl
import com.isanechek.balttur.utils.Tracker
import com.isanechek.balttur.utils.TrackerImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

private fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

val dataModule = module {

    single {
        androidApplication()
            .applicationContext
            .getSharedPreferences("balttur", Context.MODE_PRIVATE)
    } bind (SharedPreferences::class)


    single<PrefUtils> { PrefUtilsImpl(get()) }

    single {
        createOkHttpClient()
    }

    factory<NetworkClient> {
        NetworkClientImpl(
            get()
        )
    }

    single<Tracker> {
        TrackerImpl()
    }

    factory<HomePageParser> {
        HomePageParserImpl(get())
    }

    single<PlatformContract> {
        PlatformContractImpl(androidContext(), get())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            "Balttur.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory {
        get<Database>().newsDao()
    }

    factory {
        get<Database>().toursDao()
    }
}