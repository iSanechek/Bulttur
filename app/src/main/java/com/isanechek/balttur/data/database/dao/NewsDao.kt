package com.isanechek.balttur.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isanechek.balttur.data.database.entity.NewsEntity

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun load(): LiveData<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id =:id")
    fun load(id: Int): LiveData<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<NewsEntity>)

    @Query("TRUNCATE TABLE news")
    suspend fun clear()

    @Query("SELECT count(*) FROM news")
    suspend fun count(): Int

    @Transaction
    suspend fun update(items: List<NewsEntity>) {
        clear()
        insert(items)
    }
}