package com.example.factoryfm.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.ui.presenter.SavedAlbumsContract
import com.example.factoryfm.ui.presenter.SavedAlbumsPresenter
import kotlinx.android.synthetic.main.activity_saved_albums.*
import kotlinx.android.synthetic.main.common_layout.*

class SavedAlbumsActivity : AppCompatActivity(), SavedAlbumsContract.View {
    private val presenter: SavedAlbumsPresenter by lazy { SavedAlbumsPresenter(this, this) }
    private val adapter by lazy { TopAlbumsAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_albums)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
        if (recycler_view.adapter == null)
            recycler_view.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        initDb()
    }

    private fun initDb() {
        progressBar.visibility = View.VISIBLE
        info_text.visibility = View.GONE
        presenter.loadSavedAlbums()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_action -> {
                val intent = Intent(this, SearchArtistActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showSavedAlbums(albums: List<AlbumDetails>) {
        runOnUiThread {
            progressBar.visibility = View.GONE
            info_text.visibility = View.GONE
            adapter.topTopAlbums.clear()
            adapter.topTopAlbums.addAll(albums)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onError(message: String) {
        runOnUiThread {
            progressBar.visibility = View.GONE
            info_text.text = message
            info_text.visibility = View.VISIBLE
            adapter.topTopAlbums.clear()
            adapter.notifyDataSetChanged()
        }
    }
}