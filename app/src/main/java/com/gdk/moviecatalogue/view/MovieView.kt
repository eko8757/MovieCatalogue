package com.gdk.moviecatalogue.view

import com.gdk.moviecatalogue.model.MovieResponse

interface MovieView {

    fun makeToast(msg: String)
    fun showProgress()
    fun hideProgress()
    fun showData(data: ArrayList<MovieResponse.ResultMovie>)
    fun getData()
}