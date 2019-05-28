package com.isanechek.storage.dao

import androidx.room.Dao
import androidx.room.Query
import com.isanechek.storage.entity.ItemMenuEntity

@Dao
abstract class HomeMenuDao : BaseDao<ItemMenuEntity> {

    @Query("SELECT * FROM item_menu_table")
    abstract fun loadMenu(): Set<ItemMenuEntity>

    @Query("SELECT * FROM item_menu_table WHERE id =:id")
    abstract fun loadMenu(id: Int): ItemMenuEntity
}