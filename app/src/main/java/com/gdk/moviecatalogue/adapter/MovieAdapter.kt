package com.gdk.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.model.MovieResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class MovieAdapter(val items: List<MovieResponse.ResultMovie>, val listener: (MovieResponse.ResultMovie) -> Unit)
    : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(items[position], listener)
    }

    class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindData(item: MovieResponse.ResultMovie, listener: (MovieResponse.ResultMovie) -> Unit) {
            itemView.tv_title.text = item.title
            itemView.tv_desc.text = item.overview
            Picasso.get().load(BuildConfig.MOVIE_PATH + item.poster_path).into(itemView.img_card)

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }
}