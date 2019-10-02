package com.gdk.movieprovider.view

import android.content.Context
import android.database.Cursor
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.model.ResponseTv

interface MainView {
    interface MovieView {
        fun showData(data: ArrayList<ResponseMovie.ResultMovie>)
        fun getData()
        fun empty()
        fun makeToast(msg: String)
        fun showProgress()
        fun hideProgress()
    }

    interface MoviePresenter {
        fun getMovie(context: Context)
        fun toDetail(context: Context, position: Int)
        fun convertCursor(cursor: Cursor): ArrayList<ResponseMovie.ResultMovie>
    }

    interface TvShowView {
        fun showData(data: ArrayList<ResponseTv.ResultTvShow>)
        fun getData()
        fun empty()
        fun showProgress()
        fun hideProgress()
    }

    interface TvShowPresenter {
        fun getTvShow(context: Context)
        fun toDetail(context: Context, position: Int)
        fun convertCursor(cursor: Cursor): ArrayList<ResponseTv.ResultTvShow>
    }

}