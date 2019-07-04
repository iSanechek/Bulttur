package com.isanechek.balttur.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "tours_info", primaryKeys = ["id"])
data class ToursInfoEntity(val id: Int,
                           @ColumnInfo(name = "group_title") val groupTitle: String,
                           @ColumnInfo(name = "group_url") val groupUrl: String,
                           @ColumnInfo(name = "big_title") val bigTitle: String,
                           @ColumnInfo(name = "big_url") val bigUrl: String,
                           val price: String,
                           val description: String,
                           @ColumnInfo(name = "days_title") val daysTitle: String,
                           val dates: String)