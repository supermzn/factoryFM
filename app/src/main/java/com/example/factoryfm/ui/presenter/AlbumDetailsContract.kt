package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.AlbumDetails

interface AlbumDetailsContract {
    interface View : MainContract.MainView{
        fun showAlbumInfo(album: AlbumDetails)

        fun showAction(actionId: Int)
    }

    interface Presenter : MainContract.MainPresenter {
        fun saveAlbum()

        fun removeAlbum()

        fun fetchAlbumInfo(album: AlbumDetails?)
    }
}