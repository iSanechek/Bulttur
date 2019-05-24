package com.isanechek.balttur.di

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
}