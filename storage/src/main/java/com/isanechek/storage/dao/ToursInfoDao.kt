package com.isanechek.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.isanechek.storage.entity.ToursInfoEntity

@Dao
abstract class ToursInfoDao : BaseDao<ToursInfoEntity> {

    @Query("SELECT * FROM tours_info_table")
    abstract fun loadInfo(): Set<ToursInfoEntity>

    @Query("SELECT * FROM tours_info_table WHERE id =:id")
    abstract fun loadInfo(id: Int): ToursInfoEntity

}