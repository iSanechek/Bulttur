package com.isanechek.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.isanechek.storage.entity.NewsEntity

@Dao
abstract class NewsDao : BaseDao<NewsEntity> {

    @Query("SELECT * FROM news_table")
    abstract fun loadNews(): Set<NewsEntity>

    @Query("SELECT * FROM news_table WHERE id =:id")
    abstract fun loadNews(id: Int): NewsEntity
}