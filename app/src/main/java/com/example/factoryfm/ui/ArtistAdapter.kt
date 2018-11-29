package com.example.factoryfm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.factoryfm.R
import com.example.factoryfm.model.Artist
import com.example.factoryfm.utils.displayImageWithPlaceholder
import kotlinx.android.synthetic.main.artist_item.view.*

class ArtistAdapter(private val context: Context) : RecyclerView.Adapter<ArtistAdapter.ViewHolder>() {
    var artists: MutableList<Artist> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ArtistAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.artist_item, p0, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount() = artists.size

    override fun onBindViewHolder(holder: ArtistAdapter.ViewHolder, position: Int) {
        holder.bind(artists[position])
    }

    fun addElements(data: List<Artist>) {
       artists.addAll(data)
    }

    class ViewHolder(itemView: View, private val context: Context) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Artist) {
            itemView.artist_name.text = item.name
            val url = item.image[3].url

            displayImageWithPlaceholder(url, itemView.image, R.drawable.artist_placeholder, context)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, TopAlbumsActivity::class.java)
                val bundle = Bundle()
                bundle.putString(context.getString(R.string.extra_artist_mbid), item.mbid)
                bundle.putString(context.getString(R.string.extra_artist_image), url)
                bundle.putString(context.getString(R.string.extra_artist_name), item.name)
                intent.putExtras(bundle)
                context.startActivity(intent)
            }
        }
    }
}