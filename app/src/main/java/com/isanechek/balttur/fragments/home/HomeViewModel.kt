package com.isanechek.balttur.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.epam.coroutinecache.api.CacheParams
import com.epam.coroutinecache.api.CoroutinesCache
import com.epam.coroutinecache.mappers.GsonMapper
import com.isanechek.balttur.data.NetworkClient
import com.isanechek.balttur.data.PlatformContract
import com.isanechek.balttur.data.models.HomeResult
import com.isanechek.balttur.data.parsers.HomePageParser
import com.isanechek.balttur.data.providers.HomePageCacheProvider
import com.isanechek.balttur.data.repositories.HomeRepository
import com.isanechek.balttur.utils.Tracker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import java.io.File

class HomeViewModel(
    private val client: NetworkClient,
    private val parser: HomePageParser,
    platform: PlatformContract,
    private val tracker: Tracker
) : ViewModel() {

    private val coroutinesCache = CoroutinesCache(CacheParams(10, GsonMapper(), File(platform.getPathCacheFolder())))
    private val cacheProviders: HomePageCacheProvider = coroutinesCache.using(HomePageCacheProvider::class.java)

    fun load(): LiveData<List<HomeResult>> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {



    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

}