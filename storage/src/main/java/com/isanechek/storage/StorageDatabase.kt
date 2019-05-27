package com.isanechek.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isanechek.storage.dao.ToursInfoDao
import com.isanechek.storage.entity.ToursInfoEntity

@Database(entities = [
	(ToursInfoEntity::class), 
	(TextContentEntity::class),
	(NewsEntity::class),
	(ItemMenuEntity::class)], version = 1, exportSchema = false)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun homeToursInfoDao(): ToursInfoDao
    abstract fun homeContentDao(): TextContentDao
    abstract fun homeNewsDao(): NewsDao
    abstract fun homeMenuDao(): HomeMenuDao
}