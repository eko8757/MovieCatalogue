package com.gdk.moviecatalogue.presenter.tv

import android.content.Context
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.view.DetailView

class DetailPresenterTv(val view: DetailView.ViewTVShow): DetailView.PresenterTVShow {

    private lateinit var dataGlobal: ResponseTv.ResultTvShow
    private var idTvShow: Long = 0

    override fun extractData(context: Context, data: ResponseTv.ResultTvShow) {
        val image = data.poster_path.toString()
        val title = data.title.toString()
        val firstAir = data.first_air_date.toString()
        val rating = data.vote_average.toString()
        val popularity  = data.popularity.toString()
        val description = data.overview.toString()
        view.showData(image,title,firstAir,rating,popularity,description)
        this.dataGlobal = data
        this.idTvShow = data.id!!
    }

    override fun setFavorite(context: Context) {
        DbHelper.getinstance(context).tvDao().insertTvshow(dataGlobal)
    }

    override fun unsetFavorite(context: Context) {
        DbHelper.getinstance(context).tvDao().removeData(idTvShow)
    }

    override fun getFavorite(context: Context): Boolean {
        return DbHelper.getinstance(context).tvDao().getSelectDataTv(idTvShow) != null
    }
}