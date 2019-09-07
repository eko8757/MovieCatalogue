package com.gdk.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.model.TvResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class TvAdapter(val items: List<TvResponse.ResultTvShow>, val listener: (TvResponse.ResultTvShow) -> Unit)
    : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bindData(items[position], listener)
    }

    class TvViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindData(item: TvResponse.ResultTvShow, listener: (TvResponse.ResultTvShow) -> Unit) {

            itemView.tv_title.text = item.title
            itemView.tv_desc.text = item.overview
            Picasso.get().load(BuildConfig.MOVIE_PATH + item.poster_path).into(itemView.img_card)

            itemView.setOnClickListener {
                listener(item)
            }
        }

    }
}