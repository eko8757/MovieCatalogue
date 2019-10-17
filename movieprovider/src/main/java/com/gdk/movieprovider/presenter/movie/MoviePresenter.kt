package com.gdk.movieprovider.presenter.movie

import android.annotation.SuppressLint
import android.content.ContentProviderClient
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.RemoteException
import android.util.Log
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.model.StringResponseMovie
import com.gdk.movieprovider.ui.movie.DetailMovie
import com.gdk.movieprovider.utils.StaticString
import com.gdk.movieprovider.view.MainView

class MoviePresenter(val view: MainView.MovieView) : MainView.MoviePresenter {

    private var resultMovie: ArrayList<ResponseMovie.ResultMovie>? = null
    private val MOVIE_TABLE = ResponseMovie.ResultMovie::class.java.simpleName as String
    private val CONTENT_URI =
        Uri.parse(StaticString.SCHEME + "://" + StaticString.AUTHOR + "/" + MOVIE_TABLE)

    @SuppressLint("Recycle")
    override fun getMovie(context: Context) {
        val clientContentProvider: ContentProviderClient =
            context.contentResolver.acquireContentProviderClient(CONTENT_URI)
        try {
            assert(clientContentProvider != null)
            val cursor = clientContentProvider.query(
                CONTENT_URI, arrayOf(
                    StringResponseMovie.id,
                    StringResponseMovie.overview,
                    StringResponseMovie.poster_path,
                    StringResponseMovie.release_date,
                    StringResponseMovie.title,
                    StringResponseMovie.vote_average
                ),
                null,
                null,
                null,
                null
            )

            assert(cursor != null)
            val cursorCount = cursor?.count
            if (cursorCount != null) {
                if (cursorCount > 0) {
                    view.showData(convertCursor(cursor))
                    view.makeToast("Cursor not null")
                } else {
                    view.empty()
                    view.makeToast("Cursor null")
                    cursor.close()
                }
            }

        } catch (e: RemoteException) {
            view.makeToast("Cursor error")
            Log.d("errorContentResolver", e.message.toString())
            e.printStackTrace()
        }
    }

    override fun convertCursor(cursor: Cursor): ArrayList<ResponseMovie.ResultMovie> {
        val items: ArrayList<ResponseMovie.ResultMovie> = ArrayList()
        while (cursor.moveToNext()) {
            val item = ResponseMovie.ResultMovie(cursor)
            items.add(item)
        }
        resultMovie = items
        return items
    }

    override fun toDetail(context: Context, position: Int) {
        val intent = Intent(context, DetailMovie::class.java)
        intent.putExtra("Data", resultMovie?.get(position))
        context.startActivity(intent)
    }
}