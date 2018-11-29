package com.example.factoryfm

import android.app.Application
import com.example.factoryfm.api.LastFmApi
import com.example.factoryfm.api.RetrofitProvider
import com.example.factoryfm.ui.presenter.MainPresenter

class FmApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MainPresenter.api = RetrofitProvider.get(getString(R.string.api_key)).create(LastFmApi::class.java)
    }
}