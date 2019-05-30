package com.isanechek.balttur.di

import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.NetworkClientImpl
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.data.PlatformContractImpl
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.data.parsers.HomePageParserImpl
import com.isanechek.balttur.data.repositories.HomeRepository
import com.isanechek.balttur.data.repositories.HomeRepositoryImpl
import com.isanechek.balttur.utils.Tracker
import com.isanechek.balttur.utils.TrackerImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import java.io.File
import java.util.concurrent.TimeUnit

private fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

val dataModule = module {

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

    factory<HomeRepository> {
        HomeRepositoryImpl(get(), get(), get(), androidApplication())
    }

    single<PlatformContract> {
        PlatformContractImpl(androidContext())
    }
}