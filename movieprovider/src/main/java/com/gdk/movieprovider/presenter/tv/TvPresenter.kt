package com.gdk.movieprovider.presenter.tv

import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.RemoteException
import com.gdk.movieprovider.model.ResponseTv
import com.gdk.movieprovider.model.StringResponseTvShow
import com.gdk.movieprovider.ui.tv.DetailTv
import com.gdk.movieprovider.utils.StaticString
import com.gdk.movieprovider.view.MainView

class TvPresenter(val view: MainView.TvShowView) : MainView.TvShowPresenter {

    private var resultTv: ArrayList<ResponseTv.ResultTvShow>? = null
    private val TV_TABLE = ResponseTv.ResultTvShow::class.java.simpleName as String
    private val CONTENT_URI =
        Uri.parse(StaticString.SCHEME + "://" + StaticString.AUTHOR + "/" + TV_TABLE)

    override fun getTvShow(context: Context) {

        val clientContentProvider: ContentProviderClient =
            context.contentResolver.acquireContentProviderClient(CONTENT_URI)
        try {
            assert(clientContentProvider != null)
            val cursor = clientContentProvider.query(
                CONTENT_URI, arrayOf(
                    StringResponseTvShow.id,
                    StringResponseTvShow.original_name,
                    StringResponseTvShow.poster_path,
                    StringResponseTvShow.overview,
                    StringResponseTvShow.first_air_date,
                    StringResponseTvShow.vote_average
                ), null, null, null, null
            )

            assert(cursor != null)
            if (cursor.count > 0) {
                view.showData(convertCursor(cursor))
            } else {
                view.empty()
                cursor.close()
            }

        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    override fun convertCursor(cursor: Cursor): ArrayList<ResponseTv.ResultTvShow> {
        val items: ArrayList<ResponseTv.ResultTvShow> = ArrayList()
        while (cursor.moveToNext()) {
            val item = ResponseTv.ResultTvShow(cursor)
            items.add(item)
        }
        resultTv = items
        return items
    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailTv::class.java)
        intent.putExtra("Data", resultTv?.get(position))
        context.startActivity(intent)
    }


}