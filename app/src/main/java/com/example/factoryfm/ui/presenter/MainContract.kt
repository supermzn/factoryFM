package com.example.factoryfm.ui.presenter

import android.widget.ProgressBar

interface MainContract {
    val progressbar: ProgressBar

    interface MainView {
        fun onError(message: String) {
        }

    }

    interface MainPresenter
}