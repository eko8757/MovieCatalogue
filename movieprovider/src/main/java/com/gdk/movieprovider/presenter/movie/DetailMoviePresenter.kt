package com.gdk.movieprovider.presenter.movie

import android.content.Context
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.view.DetailView

class DetailMoviePresenter(val view: DetailView.ViewMovie) : DetailView.PresenterMovie {

    private lateinit var dataGlobal: ResponseMovie.ResultMovie
    private var idMovie: Long = 0

    override fun extractData(context: Context, data: ResponseMovie.ResultMovie) {
        val img = data.poster_path.toString()
        val title = data.title.toString()
        val release = data.release_date.toString()
        val rating = data.vote_average.toString()
        val desc = data.overview.toString()

        view.showData(img, title, release, rating, desc)

        this.dataGlobal = data
        this.idMovie = data.id
    }
}