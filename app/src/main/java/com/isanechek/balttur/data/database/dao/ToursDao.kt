package com.isanechek.balttur.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isanechek.balttur.data.database.entity.ToursInfoEntity

@Dao
interface ToursDao {

    @Query("SELECT * FROM tours_info")
    fun load(): LiveData<List<ToursInfoEntity>>

    @Query("SELECT * FROM tours_info WHERE id =:id")
    fun load(id: Int): LiveData<ToursInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<ToursInfoEntity>)

    @Query("TRUNCATE TABLE tours_info")
    suspend fun clear()

    @Query("SELECT count(*) FROM tours_info")
    suspend fun count(): Int

    @Transaction
    suspend fun update(items: List<ToursInfoEntity>) {
        clear()
        insert(items)
    }
}