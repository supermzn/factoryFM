package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.factoryfm.R
import com.example.factoryfm.model.Artist
import com.example.factoryfm.ui.presenter.SearchArtistContract
import com.example.factoryfm.ui.presenter.SearchPresenter
import com.example.factoryfm.utils.hideKeyboard
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.common_layout.*

class SearchArtistActivity : AppCompatActivity(), SearchArtistContract.View {
    private val presenter: SearchPresenter by lazy { SearchPresenter(this, this) }
    private val artistAdapter by lazy { ArtistAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        recycler_view.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        recycler_view.adapter = artistAdapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(LinearLayoutManager.VERTICAL) && artistAdapter.artists.isNotEmpty()) {
                    progressBar.visibility = View.VISIBLE
                    presenter.performSearch(artist_query.text.toString())
                }
            }
        })

        if (savedInstanceState != null) {
            val restoredArtistList: ArrayList<Artist> =
                savedInstanceState.getParcelableArrayList(getString(R.string.parcel_artists))
            updateList(restoredArtistList)
        }

        search_button.setOnClickListener {
            hideKeyboard(this)
            newSearch()
        }
        artist_query.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                newSearch()
                true
            } else false
        }

    }

    private fun newSearch() {
        progressBar.visibility = View.VISIBLE
        info_text.visibility = View.GONE
        artistAdapter.artists.clear()
        recycler_view.scrollToPosition(0)
        presenter.newSearch()
        presenter.performSearch(artist_query.text.toString())
    }


    override fun updateList(data: List<Artist>) {
        progressBar.visibility = View.GONE
        info_text.visibility = View.GONE
        with(artistAdapter) {
            addElements(data)
            notifyDataSetChanged()
        }
    }

    override fun onError(message: String) {
        progressBar.visibility = View.GONE
        if (artistAdapter.artists.isEmpty()) {
            info_text.text = message
            info_text.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        val adapter: ArtistAdapter = recycler_view.adapter as ArtistAdapter
        outState?.run {
            putParcelableArrayList(getString(R.string.parcel_artists), ArrayList(adapter.artists))
        }
        super.onSaveInstanceState(outState)
    }


}