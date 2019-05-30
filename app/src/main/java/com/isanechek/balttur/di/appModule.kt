package com.isanechek.balttur.di

import com.isanechek.balttur.fragments.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        HomeViewModel(get(), get(), get(), get())
    }
}