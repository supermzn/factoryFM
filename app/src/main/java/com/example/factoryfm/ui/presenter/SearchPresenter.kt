package com.example.factoryfm.ui.presenter

import android.content.Context
import com.example.factoryfm.R
import com.example.factoryfm.model.FmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(
    val view: SearchArtistContract.View,
    val context: Context
) : MainPresenter(), SearchArtistContract.Presenter {
    private var requestInProgress: Boolean = false
    private var page: Int = 1

    override fun performSearch(query: String) {
        if (query.isEmpty()) {
            view.onError(context.getString(R.string.empty_search_query))
        }
        if (!requestInProgress) {
            requestInProgress = true
            val call = api.searchArtist(query, page)
            call.enqueue(object : Callback<FmResponse> {
                override fun onFailure(call: Call<FmResponse>, t: Throwable) {
                    view.onError(context.getString(R.string.something_went_wrong))
                    requestInProgress = false
                }

                override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                    if (response.isSuccessful) {
                        val result = response.body()?.results?.artistmatches?.artist
                        if (result == null || result.isEmpty()) {
                            view.onError(context.getString(R.string.no_results_for, query))
                        } else {
                            view.updateList(result)
                        }
                    } else {
                        view.onError(context.getString(R.string.something_went_wrong))
                    }
                    page++
                    requestInProgress = false
                }
            })
        }
    }

    override fun newSearch() {
        page = 1
    }
}
