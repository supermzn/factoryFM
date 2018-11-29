package com.example.factoryfm.model

import com.google.gson.annotations.SerializedName

data class FmResponse(
    val results: FmResults,
    @SerializedName("topalbums") val topAlbums: FmTopAlbums,
    val album: AlbumDetails
)