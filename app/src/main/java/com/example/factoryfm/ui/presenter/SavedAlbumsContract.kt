package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.AlbumDetails

interface SavedAlbumsContract {
    interface View {
        fun showSavedAlbums(albums: List<AlbumDetails>)

        fun onError(message: String)
    }

    interface Presenter {
        fun loadSavedAlbums()
    }
}