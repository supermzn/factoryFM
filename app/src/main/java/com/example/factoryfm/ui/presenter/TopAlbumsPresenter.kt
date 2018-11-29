package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.FmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopAlbumsPresenter(val view: TopAlbumsContract.View) : MainPresenter(), TopAlbumsContract.Presenter {

    override fun loadTopAlbums(mbid: String) {
        val call = api.getTopAlbums(mbid)
        call.enqueue(object : Callback<FmResponse> {
            override fun onFailure(call: Call<FmResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                val results = response.body()?.topAlbums?.album
                results?.let {
                    view.onNewDataReceived(it)
                }
            }
        })
    }

}
