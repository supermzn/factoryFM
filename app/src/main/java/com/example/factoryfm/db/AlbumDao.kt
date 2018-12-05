package com.example.factoryfm.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.OnConflictStrategy.ROLLBACK
import com.example.factoryfm.model.AlbumDetails

@Dao
interface AlbumDao {
    @Insert(onConflict = REPLACE)
    fun insertAlbum(album: AlbumDetails)

    @Update(onConflict = ROLLBACK)
    fun updateAlbum(album: AlbumDetails)

    @Delete
    fun deleteAlbum(album: AlbumDetails)

    @Query("SELECT * FROM album")
    fun getAllAlbums(): List<AlbumDetails>

    @Query("SELECT * FROM album where mbid = :mbid")
    fun getAlbumById(mbid: String): AlbumDetails?
}