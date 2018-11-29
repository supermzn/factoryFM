package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.ui.presenter.AlbumDetailsContract
import com.example.factoryfm.ui.presenter.AlbumDetailsPresenter
import com.example.factoryfm.ui.presenter.TrackAdapter
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.activity_album_details.*

class AlbumDetailsActivity : AppCompatActivity(), AlbumDetailsContract.View {
    private val presenter by lazy { AlbumDetailsPresenter(this) }
    lateinit var trackAdapter: TrackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val extras = intent.extras
        val albumMbid = extras?.getString(getString(R.string.extra_album_mbid))
        val albumTitle = extras?.getString(getString(R.string.extra_album_title))
        val albumArtist = extras?.getString(getString(R.string.extra_artist_name))
        val requestParams = mutableMapOf<String, String>()
        if (albumMbid != null) {
            requestParams["mbid"] = albumMbid
        }
        else if(albumTitle != null && albumArtist != null) {
            requestParams["album"] = albumTitle
            requestParams["artist"] = albumArtist
        }
        presenter.fetchAlbumInfo(requestParams)
    }

    override fun showAlbumInfo(album: AlbumDetails) {
        album_title.text = album.name
        artist_name.text = album.artist
        val url = album.image[3].url

        displayImageWithPlaceholder(url, album_cover, R.drawable. album_placeholder, this)

        recycler_view.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        trackAdapter = TrackAdapter(album.tracks.track)
        val divider = DividerItemDecoration(recycler_view.context, LinearLayoutManager.VERTICAL)
        recycler_view.addItemDecoration(divider)
        recycler_view.adapter = trackAdapter
    }
}
