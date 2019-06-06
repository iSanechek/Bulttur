package com.isanechek.balttur.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "news", primaryKeys = ["id"])
data class NewsEntity(val id: Int,
                      @ColumnInfo(name = "imag_url") val imgUrl: String,
                      val date: String,
                      val title: String,
                      @ColumnInfo(name = "title_url") val titleUrl: String,
                      val link: String,
                      @ColumnInfo(name = "link_description") val linkDescription: String,
                      val content: String,
                      @ColumnInfo(name = "content_type") val contentType: String)