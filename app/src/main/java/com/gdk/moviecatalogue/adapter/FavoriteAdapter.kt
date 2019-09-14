package com.gdk.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.db.DbMovie
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class FavoriteAdapter(val items: List<DbMovie>, val listener: (DbMovie) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindData(items[position], listener)
    }

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindData(item: DbMovie, listener: (DbMovie) -> Unit) {
            itemView.tv_title.text = item.title
            itemView.tv_desc.text = item.desc
            Picasso.get().load(BuildConfig.MOVIE_PATH + item.image).into(itemView.img_card)
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }
}