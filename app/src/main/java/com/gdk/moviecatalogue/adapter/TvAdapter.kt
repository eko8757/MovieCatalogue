package com.gdk.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.model.ResponseTv
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class TvAdapter(val items: List<ResponseTv.ResultTvShow>) : RecyclerView.Adapter<TvAdapter.TvViewHolder>() {

    private lateinit var itemClickListener: OnItemClickListener

    fun setOnItemClickListener(itemClick: OnItemClickListener) {
        this.itemClickListener = itemClick
    }

    interface OnItemClickListener {
        fun onItemClickListener(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bindData(items[position], itemClickListener)
    }

    class TvViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindData(item: ResponseTv.ResultTvShow, itemClickListener: OnItemClickListener) {

            itemView.tv_title.text = item.original_name
            itemView.tv_desc.text = item.overview
            Picasso.get().load(BuildConfig.MOVIE_PATH + item.poster_path).into(itemView.img_card)

            itemView.setOnClickListener {
                val pos : Int = adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    itemClickListener.onItemClickListener(pos)
                }
            }
        }

    }
}