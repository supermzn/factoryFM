package com.example.factoryfm.ui.presenter

import com.example.factoryfm.model.Artist

interface SearchArtistContract {
    interface View : MainContract.MainView{
        fun updateList(result: List<Artist>)
    }

    interface Presenter : MainContract.MainPresenter{
        fun performSearch(query: String)

        fun newSearch()

    }
}