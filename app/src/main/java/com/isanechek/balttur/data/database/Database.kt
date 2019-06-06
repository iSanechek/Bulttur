package com.isanechek.balttur.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.isanechek.balttur.data.database.dao.NewsDao
import com.isanechek.balttur.data.database.dao.ToursDao
import com.isanechek.balttur.data.database.entity.NewsEntity
import com.isanechek.balttur.data.database.entity.ToursInfoEntity

@Database(
    entities = [
        (NewsEntity::class),
        (ToursInfoEntity::class)
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun toursDao(): ToursDao
}