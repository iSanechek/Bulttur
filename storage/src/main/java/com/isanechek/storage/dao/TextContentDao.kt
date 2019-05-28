package com.isanechek.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.isanechek.storage.entity.TextContentEntity

@Dao
abstract class TextContentDao : BaseDao<TextContentEntity> {

    @Query("SELECT * FROM text_content_table")
    abstract fun loadContent(): Set<TextContentEntity>

    @Query("SELECT * FROM text_content_table WHERE id =:id")
    abstract fun loadContent(id: Int): TextContentEntity
}