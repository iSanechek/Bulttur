package com.isanechek.balttur.di

import com.isanechek.balttur.fragments.country.CountryViewModel
import com.isanechek.balttur.fragments.dashboard.DashboardViewModel
import com.isanechek.balttur.utils.DialogUtils
import com.isanechek.balttur.utils.DialogUtilsImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        DashboardViewModel(androidApplication(), get(), get(), get(), get(), get(), get())
    }

    viewModel {
        CountryViewModel(get(), get(), get())
    }

    factory<DialogUtils> { DialogUtilsImpl(get()) }
}