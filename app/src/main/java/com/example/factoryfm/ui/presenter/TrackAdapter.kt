package com.example.factoryfm.ui.presenter

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.factoryfm.R
import com.example.factoryfm.model.Track
import kotlinx.android.synthetic.main.track_item.view.*

class TrackAdapter(val tracks: List<Track>) : RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.track_item, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount() = tracks.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Track) {
            itemView.track_title.text = item.name
            val duration: String = DateUtils.formatElapsedTime(item.duration.toLong())
            itemView.track_duration.text = duration
        }
    }
}