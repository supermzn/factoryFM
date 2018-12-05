package com.example.factoryfm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.factoryfm.R
import com.example.factoryfm.model.AlbumDetails
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.album_item.view.*

class TopAlbumsAdapter(private val context: Context) : RecyclerView.Adapter<TopAlbumsAdapter.ViewHolder>() {
    var topTopAlbums: MutableList<AlbumDetails> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TopAlbumsAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.album_item, p0, false)
        return TopAlbumsAdapter.ViewHolder(view, context)
    }

    override fun getItemCount() = topTopAlbums.size

    override fun onBindViewHolder(holder: TopAlbumsAdapter.ViewHolder, position: Int) {
        holder.bind(topTopAlbums[position])
    }

    fun addElements(data: List<AlbumDetails>) {
        topTopAlbums.addAll(data)
    }

    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: AlbumDetails) {
            itemView.album_title.text = item.name

            val url = item.image[2].url

            displayImageWithPlaceholder(url, itemView.album_cover, R.drawable.album_placeholder, context)

            itemView.setOnClickListener {
                val intent = Intent(context, AlbumDetailsActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable(context.getString(R.string.extra_album), item)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }
}
