package com.gdk.movieprovider.presenter.movie

import android.annotation.SuppressLint
import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.RemoteException
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.model.StringResponseMovie
import com.gdk.movieprovider.ui.movie.DetailMovie
import com.gdk.movieprovider.utils.StaticString
import com.gdk.movieprovider.view.MainView
import java.lang.Error

class MoviePresenter(val view: MainView.MovieView) : MainView.MoviePresenter {

    private var resultMovie: ArrayList<ResponseMovie.ResultMovie>? = null
    private val TABLE_MOVIE = ResponseMovie.ResultMovie::class.java.simpleName as String
    private val CONTENT_URI = Uri.parse(StaticString.SCHEME+"://" + StaticString.AUTHOR + "/" + TABLE_MOVIE)

    @SuppressLint("Recycle")
    override fun getMovie(context: Context) {
        val client: ContentProviderClient = context.contentResolver.acquireContentProviderClient(CONTENT_URI)
        try {
            assert(client != null) {
                val cursor = client.query(
                    CONTENT_URI, arrayOf(
                        StringResponseMovie.id,
                        StringResponseMovie.title,
                        StringResponseMovie.poster_path,
                        StringResponseMovie.overview,
                        StringResponseMovie.release_date,
                        StringResponseMovie.vote_average
                    ), null, null, null, null
                )

                assert(cursor != null)
                if (cursor.count > 0) {
                    view.showData(convertCursor(cursor))
                } else {
                    view.empty()
                    cursor.close()
                }
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailMovie::class.java)
        intent.putExtra("Data", resultMovie?.get(position))
        context.startActivity(intent)
    }

    override fun convertCursor(cursor: Cursor): ArrayList<ResponseMovie.ResultMovie> {
        val items: ArrayList<ResponseMovie.ResultMovie> = ArrayList()

        try {
            while (cursor.moveToNext()) {
                val item = ResponseMovie.ResultMovie(cursor)
                items.add(item)
            }
        } catch (e: Error) {
            e.printStackTrace()
        }

        resultMovie = items
        return items
    }
}