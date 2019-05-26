package com.isanechek.balttur.di

import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.NetworkClientImpl
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.data.parsers.HomePageParserImpl
import com.isanechek.balttur.utils.Tracker
import com.isanechek.balttur.utils.TrackerImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module
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
}