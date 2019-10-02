package com.gdk.movieprovider.ui.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.gdk.movieprovider.BuildConfig
import com.gdk.movieprovider.R
import com.gdk.movieprovider.presenter.movie.DetailMoviePresenter
import com.gdk.movieprovider.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovie : AppCompatActivity(), DetailView.ViewMovie {

    private lateinit var mPresenter: DetailView.PresenterMovie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)
        mPresenter = DetailMoviePresenter(this)
        getData()
    }

    override fun getData() {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            mPresenter.extractData(
                applicationContext,
                bundle.getParcelable("Data")
            )
        }
    }

    override fun showData(image: String, title: String, releaseDate: String, rating: String, description: String) {
        Picasso.get().load(BuildConfig.MOVIE_PATH + image).into(img_detail)
        tv_title_detail.text = title
        tv_release_date.text = releaseDate
        tv_vote_average.text = rating
        tv_desc_detail.text = description
    }
}
