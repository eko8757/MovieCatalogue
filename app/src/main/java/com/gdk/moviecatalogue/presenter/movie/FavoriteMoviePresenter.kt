package com.gdk.moviecatalogue.presenter.movie

import android.content.Context
import android.content.Intent
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.ui.movie.DetailMovieActivity
import com.gdk.moviecatalogue.view.MainView

class FavoriteMoviePresenter(val view: MainView.MovieView, val context: Context?): MainView.FavoriteMoviePresenter {

    private var data: ArrayList<ResponseMovie.ResultMovie>? = null

    override fun getFavoriteMovie(context: Context) {
        view.showProgress()
        val getData = DbHelper.getInstance(context).movieDao().getAllDataMovie()
        if (getData != null && getData.isNotEmpty()) {
            view.showData(getData as ArrayList<ResponseMovie.ResultMovie>)
            this.data = getData
        } else {
            view.hideProgress()
        }

    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra("Data", data?.get(position))
        context.startActivity(intent)
    }
}