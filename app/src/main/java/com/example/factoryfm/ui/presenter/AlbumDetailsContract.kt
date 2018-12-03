package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.AlbumDetails

interface AlbumDetailsContract {
    interface View : MainContract.MainView{
        fun showAlbumInfo(album: AlbumDetails)
    }

    interface Presenter : MainContract.MainPresenter {
        fun fetchAlbumInfo(params: Map<String, String>)
    }
}