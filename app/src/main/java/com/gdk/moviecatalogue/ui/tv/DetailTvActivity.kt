package com.gdk.moviecatalogue.ui.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.presenter.tv.DetailPresenterTv
import com.gdk.moviecatalogue.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_tv.*
import org.jetbrains.anko.toast

class DetailTvActivity : AppCompatActivity(), DetailView.ViewTVShow {

    private lateinit var mPresenter: DetailView.PresenterTVShow
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        mPresenter = DetailPresenterTv(this)
        getData()
        getFavorite()
    }

    override fun getData() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            mPresenter.extractData(applicationContext, bundle.getParcelable("Data"))
        }
    }

    override fun showData(
        image: String,
        title: String,
        firstAir: String,
        rating: String,
        popularity: String,
        description: String
    ) {
        tv_title_detail.text = title
        tv_release_date.text = firstAir
        tv_desc_detail.text = description
        tv_vote_average.text = rating
        Picasso.get().load(BuildConfig.MOVIE_PATH + image).into(img_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favorites_menu, menu)
        menuItem = menu
        checkFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        if (id == R.id.home) {
            finish()
        } else if (id == R.id.add_favorite) {
            if (isFavorite) {
                removeFavorite()
            } else {
                addFavorite()
            }
            isFavorite = !isFavorite
            checkFavorite()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun checkFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
        }
    }

    override fun addFavorite() {
        mPresenter.setFavorite(applicationContext)
        toast(getString(R.string.add_to_favorite))
    }

    override fun removeFavorite() {
        mPresenter.unsetFavorite(applicationContext)
        toast(getString(R.string.delete_from_favorite))
    }

    override fun getFavorite() {
        if (mPresenter.getFavorite(applicationContext)) {
            isFavorite = true
        }
    }
}
