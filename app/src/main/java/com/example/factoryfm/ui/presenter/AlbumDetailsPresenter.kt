package com.example.factoryfm.ui.presenter

import android.content.Context
import com.example.factoryfm.R
import com.example.factoryfm.db.FmDatabase
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.model.FmResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumDetailsPresenter(val view: AlbumDetailsContract.View, val context: Context) : MainPresenter(),
    AlbumDetailsContract.Presenter {
    var album: AlbumDetails? = null
    private val database = FmDatabase.getInstance(context)
    private val dao = database.albumDao


    private fun downloadAlbumInfo(params: Map<String, String>) {
        val call = api.getAlbum(params)
        call.enqueue(object : Callback<FmResponse> {
            override fun onFailure(call: Call<FmResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                album = response.body()?.album
                album?.let {
                    view.showAction(R.id.save_action)
                    view.showAlbumInfo(it)
                }
            }
        })
    }

    override fun fetchAlbumInfo(album: AlbumDetails?) {
        this.album = album
        album?.let {
            findAlbumInDb(it)
        }
    }

    private fun findAlbumInDb(tmpAlbum: AlbumDetails) {
        with(tmpAlbum) {
            if (mbid.isNotEmpty()) {
                album = tmpAlbum
                GlobalScope.launch {
                    val albumDb = dao.getAlbumById(mbid)
                    if (albumDb == null) {
                        downloadAlbumInfo(getAlbumRequestParams(tmpAlbum))
                    } else {
                        album = albumDb
                        view.showAction(R.id.delete_action)
                        view.showAlbumInfo(albumDb)
                    }
                }
            } else {
                downloadAlbumInfo(getAlbumRequestParams(tmpAlbum))
            }
        }
    }

    private fun getAlbumRequestParams(album: AlbumDetails): Map<String, String> {
        val requestParams = mutableMapOf<String, String>()
        with(album) {
            if (mbid.isNotEmpty()) {
                requestParams["mbid"] = mbid
            } else {
                requestParams["album"] = name
                requestParams["artist"] = artist
            }
        }
        return requestParams
    }

    override fun saveAlbum() {
        album?.let {
            if (it.mbid.isNotEmpty()) {
                GlobalScope.launch {
                    dao.insertAlbum(it)
                }
            } else {
                view.onError(context.getString(R.string.unable_to_save))
            }

        }
    }

    override fun removeAlbum() {
        GlobalScope.launch {
            album?.let { dao.deleteAlbum(it) }
        }
    }
}
