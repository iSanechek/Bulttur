package com.isanechek.storage.entity

import androidx.room.Entity

@Entity(tableName = "item_menu_table", primaryKeys = ["id"])
data class ItemMenuEntity(val id: String, val title: String, val url: String)