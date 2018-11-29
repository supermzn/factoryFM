package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.TopAlbum

interface TopAlbumsContract {
    interface View {
        fun onNewDataReceived(data: List<TopAlbum>)
    }

    interface Presenter {
        fun loadTopAlbums(mbid: String)

    }
}