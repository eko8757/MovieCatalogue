package com.gdk.moviecatalogue.presenter.movie

import android.content.Context
import android.net.Uri
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.util.StaticString
import com.gdk.moviecatalogue.view.DetailView

class DetailPresenterMovie(val view: DetailView.ViewMovie) : DetailView.PresenterMovie {
    private lateinit var dataGlobal: ResponseMovie.ResultMovie
    private var idMovie: Long = 0

    private val CONTENT_URI = Uri.Builder()
        .scheme(StaticString.SCHEME)
        .authority(StaticString.AUTHOR)
        .appendPath(ResponseMovie.ResultMovie::class.java.simpleName as String)
        .build()

    override fun extractData(context: Context, data: ResponseMovie.ResultMovie) {
        val image = data.poster_path.toString()
        val title = data.title.toString()
        val release = data.release_date.toString()
        val rating = data.vote_average.toString()
        val description = data.overview.toString()
        view.showData(image,title,release,rating,description)
        this.dataGlobal = data
        this.idMovie = data.id!!
    }

    override fun setFavorite(context: Context) {
        DbHelper.getInstance(context).movieDao().insertMovie(dataGlobal)
        context.contentResolver.insert(CONTENT_URI, dataGlobal.values())
    }

    override fun unsetFavorite(context: Context) {
        DbHelper.getInstance(context).movieDao().removeData(idMovie)
    }

    override fun getFavorite(context: Context): Boolean {
        return DbHelper.getInstance(context).movieDao().getSelectDataMovie(idMovie) != null
    }
}