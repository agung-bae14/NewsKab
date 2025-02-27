package com.example.newskab.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.newskab.data.local.room.Converters
import kotlinx.parcelize.Parcelize

@TypeConverters(Converters::class)
@Parcelize
@Entity(tableName = "news")
data class NewsEntity(
    @field:ColumnInfo(name = "judul")
    @PrimaryKey
    val judul: String,

    @field:ColumnInfo(name = "deskripsi")
    val deskripsi: String,

    @field:ColumnInfo(name = "status")
    val status: String,

    @field:ColumnInfo(name = "multimedias")
    val multimedias: List<MultimediasItem>,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
):Parcelable

@Parcelize
data class MultimediasItem(
    @field:ColumnInfo("id")
    val id: Int,

    @field:ColumnInfo("fullpath")
    val fullpath: String,

    @field:ColumnInfo("type")
    val type: String
):Parcelable