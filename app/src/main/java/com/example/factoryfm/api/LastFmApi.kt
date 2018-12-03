package com.example.factoryfm.api

import com.example.factoryfm.model.FmResponse
import com.example.factoryfm.utils.ALBUM_DETAILS_URL
import com.example.factoryfm.utils.SEARCH_ARTIST_URL
import com.example.factoryfm.utils.TOP_ALBUMS_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface LastFmApi {
    @GET(SEARCH_ARTIST_URL)
    fun searchArtist(
        @Query("artist") artist: String,
        @Query("page") page: Int
    ): Call<FmResponse>

    @GET(TOP_ALBUMS_URL)
    fun getTopAlbums(
        @Query("mbid") mbid: String,
        @Query("page") page: Int
    ): Call<FmResponse>

    @GET(ALBUM_DETAILS_URL)
    fun getAlbum(
        @QueryMap params: Map<String, String>
    ): Call<FmResponse>
}