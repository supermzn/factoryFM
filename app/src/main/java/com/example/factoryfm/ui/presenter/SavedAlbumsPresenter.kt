package com.example.factoryfm.ui.presenter

import android.content.Context
import com.example.factoryfm.R
import com.example.factoryfm.db.FmDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SavedAlbumsPresenter(
    val view: SavedAlbumsContract.View,
    val context: Context
) : SavedAlbumsContract.Presenter {

    override fun loadSavedAlbums() {
        val database = FmDatabase.getInstance(context)
        GlobalScope.launch {
            val dao = database.albumDao
            val savedAlbums = dao.getAllAlbums()
            if (!savedAlbums.isEmpty())
                view.showSavedAlbums(savedAlbums)
            else
                view.onError(context.getString(R.string.no_saved_albums))
        }
    }

}
