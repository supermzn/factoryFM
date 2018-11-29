package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.EditorInfo
import com.example.factoryfm.R
import com.example.factoryfm.api.LastFmApi
import com.example.factoryfm.api.RetrofitProvider
import com.example.factoryfm.model.Artist
import com.example.factoryfm.model.FmResponse
import com.example.factoryfm.ui.presenter.SearchPresenter
import com.example.factoryfm.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchArtistActivity : AppCompatActivity() {
    private val presenter: SearchPresenter by lazy { SearchPresenter() }
    private val api by lazy { RetrofitProvider.get(getString(R.string.api_key)).create(LastFmApi::class.java) }
    private val artistAdapter by lazy { ArtistAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recycler_view.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = artistAdapter

        if (savedInstanceState != null) {
            val restoredArtistList: ArrayList<Artist> =
                savedInstanceState.getParcelableArrayList(getString(R.string.parcel_artists))
            updateList(restoredArtistList)
        }

        search_button.setOnClickListener { performSearch(artist_query.text.toString()) }
        artist_query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(artist_query.text.toString())
                true
            } else false
        }
    }


    private fun performSearch(query: String, page: Int = 0) {
        hideKeyboard(this)
        val call = api.searchArtist(query)
        call.enqueue(object : Callback<FmResponse> {
            override fun onFailure(call: Call<FmResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<FmResponse>, response: Response<FmResponse>) {
                if (page == 0) {
                    artistAdapter.artists.clear()
                    recycler_view.scrollToPosition(0)
                }
                val results = response.body()?.results?.artistmatches?.artist
                results?.let {
                    updateList(it)
                }
            }
        })
    }

    private fun updateList(data: List<Artist>) {
        with(artistAdapter) {
            addElements(data)
            notifyDataSetChanged()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val adapter: ArtistAdapter = recycler_view.adapter as ArtistAdapter
        if (adapter.artists.isNotEmpty())
            outState?.run {
                putParcelableArrayList(getString(R.string.parcel_artists), ArrayList(adapter.artists))
            }
        super.onSaveInstanceState(outState)
    }


}