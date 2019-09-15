package com.gdk.moviecatalogue.view

import android.content.Context
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.model.ResponseTv

interface MainView {
    interface MovieView {
        fun showData(data: ArrayList<ResponseMovie.ResultMovie>)
        fun getData()
        fun showProgress()
        fun hideProgress()
    }

    interface MoviePresenter {
        fun getMovie()
        fun goToDetailMovie(context: Context, position: Int)
    }

    interface FavoriteMoviePresenter {
        fun getFavoriteMovie(context: Context)
        fun toDetail(context: Context, position: Int)
    }

    interface TvShowView {
        fun showData(data: ArrayList<ResponseTv.ResultTvShow>)
        fun getData()
        fun showProgress()
        fun hideProgress()
    }

    interface TvShowPresenter {
        fun getTvShow()
        fun goToDetailTvShow(context: Context, position: Int)
    }

    interface FavoritTvShowPresenter {
        fun getFavoriteTvShow(context: Context)
        fun toDetail(context: Context, position: Int)
    }
}