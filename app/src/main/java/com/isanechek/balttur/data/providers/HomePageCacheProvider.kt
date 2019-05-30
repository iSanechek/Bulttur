package com.isanechek.balttur.data.providers

import com.epam.coroutinecache.annotations.LifeTime
import com.epam.coroutinecache.annotations.ProviderKey
import com.isanechek.balttur.data.models.TextContentRoot
import java.util.concurrent.TimeUnit

interface HomePageCacheProvider {

    @ProviderKey("home_page_cache_key")
    @LifeTime(value = 1L, unit = TimeUnit.DAYS)
    suspend fun getTextContent(dataProvider: suspend () -> TextContentRoot): TextContentRoot
}