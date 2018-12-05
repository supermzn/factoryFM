package com.example.factoryfm.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.ui.presenter.AlbumDetailsContract
import com.example.factoryfm.ui.presenter.AlbumDetailsPresenter
import com.example.factoryfm.ui.presenter.TrackAdapter
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.activity_album_details.*
import kotlinx.android.synthetic.main.common_layout.*

class AlbumDetailsActivity : AppCompatActivity(), AlbumDetailsContract.View {
    private val presenter by lazy { AlbumDetailsPresenter(this, this) }
    private lateinit var trackAdapter: TrackAdapter
    private var mMenu: Menu? = null
    private var visibleAction: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val extras = intent.extras
        val restoredAlbum: AlbumDetails? = extras?.getParcelable(getString(R.string.extra_album))
        progressBar.visibility = View.VISIBLE
        presenter.fetchAlbumInfo(restoredAlbum)
    }

    override fun showAlbumInfo(album: AlbumDetails) {
        progressBar.visibility = View.GONE
        info_text.visibility = View.GONE
        album_title.text = album.name
        artist_name.text = album.artist
        val url = album.image[3].url

        runOnUiThread {
            displayImageWithPlaceholder(url, album_cover, R.drawable.album_placeholder, this)
        }

        recycler_view.layoutManager = LinearLayoutManager(parent, LinearLayoutManager.VERTICAL, false)
        val tracks = album.tracks.track
        trackAdapter = TrackAdapter(album.tracks.track)
        if (tracks.isNotEmpty()) {
            val divider = DividerItemDecoration(recycler_view.context, LinearLayoutManager.VERTICAL)
            recycler_view.addItemDecoration(divider)
            recycler_view.adapter = trackAdapter
        } else {
            onError(getString(R.string.no_tracks))
        }

    }

    override fun onError(message: String) {
        progressBar.visibility = View.GONE
        if (trackAdapter.tracks.isEmpty()) {
            info_text.text = message
            info_text.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        mMenu = menu
        menuInflater.inflate(R.menu.album_menu, menu)
        menu.getItem(0).isVisible = false
        menu.getItem(1).isVisible = false
        visibleAction?.let {
            showAction(it)
        }
        return true
    }

    override fun showAction(actionId: Int) {
        visibleAction = actionId
        mMenu?.let {
            if (actionId == R.id.save_action) {
                it.getItem(0).isVisible = true
                it.getItem(1).isVisible = false
            } else {
                it.getItem(0).isVisible = false
                it.getItem(1).isVisible = true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_action -> {
                presenter.saveAlbum()
                return true
            }
            R.id.delete_action -> {
                presenter.removeAlbum()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
