package com.isanechek.storage.entity

import androidx.room.Entity
import androidx.room.ColumnInfo

@Entity(tableName = "tours_info_table", primaryKeys = ["id"])
data class ToursInfoEntity(
    val id: Int,
    @ColumnInfo(name = "group_title") val groupTitle: String,
    @ColumnInfo(name = "group_url") val groupUrl: String,
    @ColumnInfo(name = "big_title") val bigTitle: String,
    @ColumnInfo(name = "big_url") val bigUrl: String,
    val price: String,
    val description: String,
    @ColumnInfo(name = "days_title") val daysTitle: String,
    val dates: String
)