package com.gdk.moviecatalogue.presenter.movie

import android.content.Context
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.view.DetailView

class DetailPresenterMovie(val view: DetailView.ViewMovie) : DetailView.PresenterMovie {
    private lateinit var dataGlobal: ResponseMovie.ResultMovie
    private var idMovie: Long = 0

    override fun extractData(context: Context, data: ResponseMovie.ResultMovie) {
        val image = data.poster_path.toString()
        val title = data.title.toString()
        val release = data.release_date.toString()
        val rating = data.vote_average.toString()
        val popularity  = data.popularity.toString()
        val description = data.overview.toString()
        view.showData(image,title,release,rating,popularity,description)
        this.dataGlobal = data
        this.idMovie = data.id!!
    }

    override fun setFavorite(context: Context) {
        DbHelper.getinstance(context).movieDao().insertMovie(dataGlobal)
    }

    override fun unsetFavorite(context: Context) {
        DbHelper.getinstance(context).movieDao().removeData(idMovie)
    }

    override fun getFavorite(context: Context): Boolean {
        return DbHelper.getinstance(context).movieDao().getSelectDataMovie(idMovie) != null
    }
}