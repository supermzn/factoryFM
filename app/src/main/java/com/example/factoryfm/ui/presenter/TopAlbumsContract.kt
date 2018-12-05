package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.AlbumDetails

interface TopAlbumsContract {
    interface View : MainContract.MainView{
        fun onNewDataReceived(data: List<AlbumDetails>)
    }

    interface Presenter : MainContract.MainPresenter{
        fun loadTopAlbums(mbid: String)
    }
}