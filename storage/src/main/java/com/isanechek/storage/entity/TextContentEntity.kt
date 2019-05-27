package com.isanechek.storage.entity

import androidx.room.Entity

@Entity(tableName = "text_content_table", primaryKeys = ["id"])
data class TextContentEntity(val id: String, val content: String)