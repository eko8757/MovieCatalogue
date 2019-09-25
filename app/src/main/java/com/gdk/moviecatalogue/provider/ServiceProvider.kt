package com.gdk.moviecatalogue.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.gdk.moviecatalogue.db.DbHelper
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.util.StaticString
import java.util.*

class ServiceProvider: ContentProvider() {

    private val AUTHOR = StaticString.AUTHOR
    private val MOVIE = 1
    private val TV_SHOW = 2
    private val MOVIE_TABLE = ResponseMovie.ResultMovie::class.java.simpleName as String
    private val TV_SHOW_TABLE = ResponseTv.ResultTvShow::class.java.simpleName as String
    private val uMatcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uMatcher.addURI(AUTHOR, MOVIE_TABLE, MOVIE)
        uMatcher.addURI(AUTHOR, TV_SHOW_TABLE, TV_SHOW)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
       Objects.requireNonNull(context).contentResolver.notifyChange(uri, null)
        return uri
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
       return when (uMatcher.match(uri)) {
           MOVIE -> {
               val movieDao = DbHelper.getinstance(context).movieDao()
               movieDao.getAllMovieCursor()
           }
           TV_SHOW -> {
               val tvDao = DbHelper.getinstance(context).tvDao()
               tvDao.getAllTvCursor()
           }
           else -> null
       }
    }

    override fun onCreate(): Boolean = true

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Objects.requireNonNull(context).contentResolver.notifyChange(uri, null)
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }
}