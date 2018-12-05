package com.example.factoryfm.db

import android.arch.persistence.room.*
import android.content.Context
import com.example.factoryfm.model.AlbumDetails

@Database(entities = [AlbumDetails::class], version = 1, exportSchema = false)
@TypeConverters(value = [AlbumTypeConverter::class, TrackTypeConverter::class])
abstract class FmDatabase : RoomDatabase() {
    abstract val albumDao: AlbumDao

    companion object {
        private lateinit var INSTANCE: FmDatabase

        fun getInstance(context: Context): FmDatabase {
            synchronized(FmDatabase::class) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                    FmDatabase::class.java,
                    "fm_database")
                    .build()
                return INSTANCE
            }
        }
    }
}