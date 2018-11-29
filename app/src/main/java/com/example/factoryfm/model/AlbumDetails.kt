package com.example.factoryfm.model

data class AlbumDetails(
    val name: String,
    val playcount: Int,
    val mbid: String,
    val url: String,
    val artist: String,
    val image: List<FmImage>,
    val tracks: FmTrack
)
