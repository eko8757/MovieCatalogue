package com.gdk.moviecatalogue.ui.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.adapter.FavoriteAdapter
import com.gdk.moviecatalogue.db.DbMovie
import com.gdk.moviecatalogue.db.database
import com.gdk.moviecatalogue.ui.movie.DetailMovieActivity
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class FavoriteMovieFragment : Fragment() {

    private var items: MutableList<DbMovie> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.rv_favorite_movie.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = FavoriteAdapter(items) {
            activity?.startActivity<DetailMovieActivity>("name" to "${it.id}")
        }
        view.rv_favorite_movie.adapter = adapter
        showFavorite()
    }

    private fun showFavorite() {
        context?.database?.use {
            val result = select(DbMovie.TABLE_ITEMS)
            val favorites = result.parseList(classParser<DbMovie>())
            items.addAll(favorites)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        fun movieFavoriteInstance(): FavoriteMovieFragment= FavoriteMovieFragment()
    }

}
