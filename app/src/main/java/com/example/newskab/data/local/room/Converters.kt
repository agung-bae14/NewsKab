package com.example.newskab.data.local.room

import androidx.room.TypeConverter
import com.example.newskab.data.local.entity.MultimediasItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromMultimediaList(value: List<MultimediasItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toMultimediasList(value: String): List<MultimediasItem> {
        val type = object : TypeToken<List<MultimediasItem>>() {}.type
        return Gson().fromJson(value, type)
    }
}