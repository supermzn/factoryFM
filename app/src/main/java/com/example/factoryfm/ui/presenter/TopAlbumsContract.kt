package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.TopAlbum

interface TopAlbumsContract {
    interface View : MainContract.MainView{
        fun onNewDataReceived(data: List<TopAlbum>)
    }

    interface Presenter : MainContract.MainPresenter{
        fun loadTopAlbums(mbid: String)
    }
}