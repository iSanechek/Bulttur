package com.isanechek.storage.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insert(items: Set<T>)

    @Update
    @JvmSuppressWildcards
    suspend fun update(vararg item: T)

    @Update
    @JvmSuppressWildcards
    suspend fun update(items: Set<T>)

    @Delete
    @JvmSuppressWildcards
    suspend fun remove(items: Set<T>)

}