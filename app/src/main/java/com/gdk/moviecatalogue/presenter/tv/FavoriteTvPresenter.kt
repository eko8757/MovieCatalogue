package com.gdk.moviecatalogue.presenter.tv

import android.content.Context
import android.content.Intent
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.ui.tv.DetailTvActivity
import com.gdk.moviecatalogue.view.MainView

class FavoriteTvPresenter(val view: MainView.TvShowView, val context: Context?) : MainView.FavoritTvShowPresenter {

    private var data: ArrayList<ResponseTv.ResultTvShow>? = null

    override fun getFavoriteTvShow(context: Context) {
        view.showProgress()
        val getData = DbHelper.getinstance(context).tvDao().getAllDataTv()
        if (getData != null && getData.isNotEmpty()) {
            view.showData(getData as ArrayList<ResponseTv.ResultTvShow>)
            this.data = getData
        } else {
            view.hideProgress()
        }
    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailTvActivity::class.java)
        intent.putExtra("Data", data?.get(position))
        context.startActivity(intent)
    }
}