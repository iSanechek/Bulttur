package com.isanechek.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "news_table", primaryKeys = ["id"])
data class NewsEntity(
    val id: Int,
    @ColumnInfo(name = "img_url") val imgUrl: String,
    val date: String,
    val title: String,
    @ColumnInfo(name = "title_url") val titleUrl: String,
    val link: String,
    @ColumnInfo(name = "link_description") val linkDescription: String,
    @Embedded(prefix = "news_") @ColumnInfo(name = "info_block") val infoBlock: NewsInfoBlockEntity
)