package com.gdk.moviecatalogue.presenter.tv

import android.content.Context
import android.net.Uri
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.util.StaticString
import com.gdk.moviecatalogue.view.DetailView

class DetailPresenterTv(val view: DetailView.ViewTVShow): DetailView.PresenterTVShow {

    private lateinit var dataGlobal: ResponseTv.ResultTvShow
    private var idTvShow: Long = 0

    private val CONTENT_URI = Uri.Builder()
        .scheme(StaticString.SCHEME)
        .authority(StaticString.AUTHOR)
        .appendPath(ResponseTv.ResultTvShow::class.java.simpleName as String)
        .build()

    override fun extractData(context: Context, data: ResponseTv.ResultTvShow) {
        val image = data.poster_path.toString()
        val title = data.original_name.toString()
        val firstAir = data.first_air_date.toString()
        val rating = data.vote_average.toString()
        val description = data.overview.toString()
        view.showData(image,title,firstAir,rating,description)
        this.dataGlobal = data
        this.idTvShow = data.id!!
    }

    override fun setFavorite(context: Context) {
        DbHelper.getinstance(context).tvDao().insertTvshow(dataGlobal)
        context.contentResolver.insert(CONTENT_URI, dataGlobal.values())
    }

    override fun unsetFavorite(context: Context) {
        DbHelper.getinstance(context).tvDao().removeData(idTvShow)
    }

    override fun getFavorite(context: Context): Boolean {
        return DbHelper.getinstance(context).tvDao().getSelectDataTv(idTvShow) != null
    }
}