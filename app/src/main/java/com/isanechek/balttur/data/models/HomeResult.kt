package com.isanechek.balttur.data.models

sealed class HomeResult {
    data class News(val news: Set<com.isanechek.balttur.data.models.News>): HomeResult()
    data class Fail(val message: String): HomeResult()
    object Loading : HomeResult()
    object LoadingDone : HomeResult()
}