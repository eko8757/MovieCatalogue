package com.gdk.movieprovider.presenter.tv

import android.content.Context
import com.gdk.movieprovider.model.ResponseTv
import com.gdk.movieprovider.view.DetailView

class DetailTvPresenter(val view: DetailView.ViewTV) : DetailView.PresenterTV {

    private lateinit var dataGlobal: ResponseTv.ResultTvShow
    private var idTv: Long = 0

    override fun extractData(context: Context, data: ResponseTv.ResultTvShow) {
        val img = data.poster_path
        val title = data.title.toString()
        val firstair = data.first_air
        val rating = data.vote.toString()
        val desc = data.overview

        view.showData(img, title, firstair, rating, desc)

        this.dataGlobal = data
        this.idTv = data.id
    }
}