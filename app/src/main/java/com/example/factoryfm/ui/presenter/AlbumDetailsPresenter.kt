package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.FmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailsPresenter(view: AlbumDetailsContract.View) : MainPresenter(), AlbumDetailsContract.Presenter {
    val view: AlbumDetailsContract.View = view


    override fun fetchAlbumInfo(params: Map<String, String>) {
        val call = api.getAlbum(params)
        call.enqueue(object : Callback<FmResponse> {
            override fun onFailure(call: Call<FmResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                val album = response.body()?.album
                album?.let {
                    view.showAlbumInfo(it)
                }
            }
        })
    }

}
