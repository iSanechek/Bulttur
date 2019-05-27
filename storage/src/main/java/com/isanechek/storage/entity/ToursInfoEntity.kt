package com.isanechek.storage.entity

import androidx.room.Entity

@Entity(tableName = "tours_info", primaryKeys = ["id"])
data class ToursInfoEntity(val id: Int,
                           val groupTitle: String,
                           val groupUrl: String,
                           val bigTitle: String,
                           val bigUrl: String,
                           val price: String,
                           val description: String,
                           val daysTitle: String,
                           val dates: String)