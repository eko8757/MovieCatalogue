package com.gdk.moviecatalogue.ui.movie

import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.db.DbMovie
import com.gdk.moviecatalogue.db.database
import com.gdk.moviecatalogue.model.MovieResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailMovieActivity : AppCompatActivity() {

    private var id_: Long = 0
    var items: MovieResponse.ResultMovie? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        val i = intent
        items = i.getParcelableExtra("name")
        tv_title_detail.text = items?.title
        tv_release_date.text = items?.release_date
        tv_desc_detail.text = items?.overview
        tv_vote_average.text = items?.vote_average.toString()

        val API_URL = BuildConfig.MOVIE_PATH
        Picasso.get().load(API_URL + items?.poster_path).into(img_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        menuItem = menu
        favoriteState()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.add_favorite -> {
                if (isFavorite) {
                    removeFavorite()
                } else {
                    addToFavorite()
                }

                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(DbMovie.TABLE_ITEMS,
                    DbMovie.ID to items?.id,
                    DbMovie.TITLE to items?.title,
                    DbMovie.DATE to items?.release_date,
                    DbMovie.VOTE to items?.vote_average,
                    DbMovie.DESC to items?.overview,
                    DbMovie.IMAGE to items?.poster_path)
            }
            toast("Added to favorite")
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    private fun removeFavorite() {
        try {
            database.use {
                delete(DbMovie.TABLE_ITEMS, "(ID_ = {id})", "id" to id_)
            }
            toast("Deleted favorite")
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
        }
    }

    private fun setFavorite() {
        database.use {
            if (isFavorite) {
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_black_24dp)
            } else {
                menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite_border_black_24dp)
            }
        }
    }

    private fun favoriteState() {
        database.use {
            val result = select(DbMovie.TABLE_ITEMS).whereArgs("(ID_ = {id})", "id" to id_)
            val favorite = result.parseList(classParser<DbMovie>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
