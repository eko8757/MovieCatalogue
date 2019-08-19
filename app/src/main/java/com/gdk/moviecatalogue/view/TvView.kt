package com.gdk.moviecatalogue.view

import com.gdk.moviecatalogue.model.TvResponse

interface TvView {

    fun makeToast(msg: String)
    fun showProgress()
    fun hideProgress()
    fun showData(data: ArrayList<TvResponse.ResultTvShow>)
}