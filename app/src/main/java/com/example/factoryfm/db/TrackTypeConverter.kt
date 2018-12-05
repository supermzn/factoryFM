package com.example.factoryfm.db

import android.arch.persistence.room.TypeConverter
import com.example.factoryfm.model.FmTrack
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrackTypeConverter {
    val gson: Gson = Gson()

    @TypeConverter
    fun trackToString(tracks: FmTrack): String? {
        return gson.toJson(tracks)
    }

    @TypeConverter
    fun stringToTrack(data: String?): FmTrack {
        return gson.fromJson(data, object : TypeToken<FmTrack>() {}.type)
    }
}