package com.gdk.movieprovider.presenter.tv

import android.content.Context
import android.content.Intent
import android.database.Cursor
import com.gdk.movieprovider.model.ResponseTv
import com.gdk.movieprovider.ui.tv.DetailTv
import com.gdk.movieprovider.view.MainView

class TvPresenter(val view: MainView.TvShowView) : MainView.TvShowPresenter {

    private var resultTv: ArrayList<ResponseTv.ResultTvShow>? = null

    override fun getTvShow(context: Context) {
        val items = ArrayList<ResponseTv.ResultTvShow>()
        val item = ResponseTv.ResultTvShow()

        item.id = 1
        item.overview =
            "After a particle accelerator causes a freak storm, CSI Investigator Barry Allen is struck by lightning and falls into a coma. Months later he awakens with the power of super speed, granting him the ability to move through Central City like an unseen guardian angel. Though initially excited by his newfound powers, Barry is shocked to discover he is not the only \\\"meta-human\\\" who was created in the wake of the accelerator explosion -- and not everyone is using their new powers for good. Barry partners with S.T.A.R. Labs and dedicates his life to protect the innocent. For now, only a few close friends and associates know that Barry is literally the fastest man alive, but it won't be long before the world learns what Barry Allen has become...The Flash."
        item.poster_path = "/is7DUNsw59EcTwCO1FgECbNIfu0.jpg"
        item.first_air = "2019-09-06"
        item.title = "Desmontando la Historia"
        item.vote = 40.0

        items.add(item)
        items.add(item)
        items.add(item)
        items.add(item)
        items.add(item)
    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailTv::class.java)
        intent.putExtra("Data", resultTv?.get(position))
        context.startActivity(intent)
    }

    override fun convertCursor(cursor: Cursor): ArrayList<ResponseTv.ResultTvShow> {
        return ArrayList()
    }
}