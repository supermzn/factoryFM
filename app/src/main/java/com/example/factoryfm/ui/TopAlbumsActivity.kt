package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.example.factoryfm.R
import com.example.factoryfm.model.TopAlbum
import com.example.factoryfm.ui.presenter.TopAlbumsContract
import com.example.factoryfm.ui.presenter.TopAlbumsPresenter
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.activity_top_albums.*

class TopAlbumsActivity : AppCompatActivity(), TopAlbumsContract.View {
    private val topAlbumsPresenter: TopAlbumsPresenter by lazy { TopAlbumsPresenter(this) }
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
        if (savedInstanceState != null) {
            val restoredArtistList: ArrayList<TopAlbum> =
                savedInstanceState.getParcelableArrayList(getString(R.string.parcel_albums))
            onNewDataReceived(restoredArtistList)
        }
        artistMbid?.let {
            if (topAlbumsAdapter.topTopAlbums.isEmpty())
                topAlbumsPresenter.loadTopAlbums(it)
        }
    }

    override fun onNewDataReceived(data: List<TopAlbum>) {
        topAlbumsAdapter.addElements(data)
        topAlbumsAdapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(getString(R.string.parcel_albums), ArrayList(topAlbumsAdapter.topTopAlbums))
        super.onSaveInstanceState(outState)
    }
}