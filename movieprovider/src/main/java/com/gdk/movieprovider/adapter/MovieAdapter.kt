package com.gdk.movieprovider.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdk.movieprovider.BuildConfig
import com.gdk.movieprovider.R
import com.gdk.movieprovider.model.ResponseMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class MovieAdapter(val items: List<ResponseMovie.ResultMovie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private lateinit var itemClickListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(pos : Int)
    }

    fun setOnItemClickListener(itemClick: OnItemClickListener) {
        this.itemClickListener = itemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(items[position], itemClickListener)
    }

    class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindData(item: ResponseMovie.ResultMovie, itemClickListener: OnItemClickListener) {
            itemView.tv_title.text = item.title.toString()
            itemView.tv_desc.text = item.overview.toString()
            Picasso.get().load(BuildConfig.MOVIE_PATH + item.poster_path).into(itemView.img_card)

            itemView.setOnClickListener {
                val pos: Int = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(pos)
                }
            }
        }
    }
}