package com.example.factoryfm.ui.presenter

import android.content.Context
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.model.FmResponse
import com.example.factoryfm.model.TopAlbum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAlbumsPresenter(val view: TopAlbumsContract.View, val context: Context) : MainPresenter(),
    TopAlbumsContract.Presenter {
    private var requestInProgress: Boolean = false
    private var page: Int = 1

    override fun loadTopAlbums(mbid: String) {
        if (!requestInProgress) {
            requestInProgress = true
            val call = api.getTopAlbums(mbid, page)
            call.enqueue(object : Callback<FmResponse> {
                override fun onFailure(call: Call<FmResponse>, t: Throwable) {
                    view.onError(context.getString(R.string.something_went_wrong))
                    requestInProgress = false
                }

                override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                    if (response.isSuccessful) {
                        val results = response.body()?.topAlbums?.album
                        if (results == null || results.isEmpty()) {
                            view.onError(context.getString(R.string.no_albums))
                        } else {

                            view.onNewDataReceived(convertToAlbumDetails(results))
                        }
                    } else {
                        view.onError(context.getString(R.string.something_went_wrong))
                    }
                    page++
                    requestInProgress = false
                }
            })
        }
    }

    private fun convertToAlbumDetails(list: List<TopAlbum>): List<AlbumDetails> {
        val albumDetailsArray: MutableList<AlbumDetails> = mutableListOf()
        for (item in list) {
            val album = AlbumDetails(
                name = item.name,
                mbid = item.mbid ?: "",
                artist = item.artist.name,
                image = item.image
            )
            albumDetailsArray.add(album)
        }
        return albumDetailsArray
    }

}
