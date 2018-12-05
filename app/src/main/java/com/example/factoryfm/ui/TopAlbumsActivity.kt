package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.ui.presenter.TopAlbumsContract
import com.example.factoryfm.ui.presenter.TopAlbumsPresenter
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.activity_top_albums.*
import kotlinx.android.synthetic.main.common_layout.*

class TopAlbumsActivity : AppCompatActivity(), TopAlbumsContract.View {
    private val topAlbumsPresenter: TopAlbumsPresenter by lazy { TopAlbumsPresenter(this, this) }
    private val topAlbumsAdapter by lazy { TopAlbumsAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_albums)

        val extras = intent.extras
        val artistName = extras?.getString(getString(R.string.extra_artist_name))
        artist_title.text = artistName
        val artistMbid = extras?.getString(getString(R.string.extra_artist_mbid))
        val artistImageUrl = extras?.getString(getString(R.string.extra_artist_image))

        artistImageUrl?.let {
            displayImageWithPlaceholder(it, image, R.drawable.album_placeholder, this)
        }

        recycler_view.layoutManager = GridLayoutManager(this, 2)
        recycler_view.adapter = topAlbumsAdapter
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(LinearLayoutManager.VERTICAL) && topAlbumsAdapter.topTopAlbums.isNotEmpty()) {
                    loadTopAlbums(artistMbid)
                }
            }
        })

        if (savedInstanceState != null) {
            val restoredArtistList: ArrayList<AlbumDetails> =
                savedInstanceState.getParcelableArrayList(getString(R.string.parcel_albums))
            onNewDataReceived(restoredArtistList)
        }
        loadTopAlbums(artistMbid)
    }

    private fun loadTopAlbums(mbid: String?) {
        mbid?.let {
            progressBar.visibility = View.VISIBLE
            info_text.visibility = View.GONE
            topAlbumsPresenter.loadTopAlbums(it)
        }
    }

    override fun onNewDataReceived(data: List<AlbumDetails>) {
        progressBar.visibility = View.GONE
        info_text.visibility = View.GONE
        topAlbumsAdapter.addElements(data)
        topAlbumsAdapter.notifyDataSetChanged()
    }

    override fun onError(message: String) {
        progressBar.visibility = View.GONE
        if (topAlbumsAdapter.topTopAlbums.isEmpty()) {
            info_text.visibility = View.VISIBLE
            info_text.text = message
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(getString(R.string.parcel_albums), ArrayList(topAlbumsAdapter.topTopAlbums))
        super.onSaveInstanceState(outState)
    }
}