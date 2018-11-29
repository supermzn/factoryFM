package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.AlbumDetails

interface AlbumDetailsContract {
    interface View {
        fun showAlbumInfo(album: AlbumDetails)
    }

    interface Presenter {
        fun fetchAlbumInfo(params: Map<String, String>)
    }
}