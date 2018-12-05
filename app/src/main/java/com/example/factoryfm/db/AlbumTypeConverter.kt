package com.example.factoryfm.db

import android.arch.persistence.room.TypeConverter
import com.example.factoryfm.model.FmImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AlbumTypeConverter {
    val gson: Gson = Gson()

    @TypeConverter
    fun listToString(list: List<FmImage>): String? {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToList(data: String?): List<FmImage> {
        return if (data == null)
            emptyList()
        else {
            gson.fromJson(data, object : TypeToken<List<FmImage>>() {}.type)
        }
    }

}