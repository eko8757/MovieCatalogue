package com.gdk.moviecatalogue.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdk.moviecatalogue.BuildConfig
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.model.MovieResponse
import com.gdk.moviecatalogue.model.TvResponse
import com.gdk.moviecatalogue.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var itemsMovie: MovieResponse.ResultMovie? = null
    var itemsTv: TvResponse.ResultTvShow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intentMovie = intent
        itemsMovie = intentMovie.getParcelableExtra("name")
        tv_title_detail.text = itemsMovie?.title
        tv_release_date.text = itemsMovie?.release_date
        tv_desc_detail.text = itemsMovie?.overview

        val API_MOVIE = BuildConfig.MOVIE_PATH
        Picasso.get().load(API_MOVIE + itemsMovie?.poster_path).into(img_detail)

        val intentTv = intent
        itemsTv = intentTv.getParcelableExtra("TV")
        tv_title_detail.text = itemsTv?.title
        tv_release_date.text = itemsTv?.first_air_date
        tv_desc_detail.text = itemsTv?.overview

        val API_TV = BuildConfig.MOVIE_PATH
        Picasso.get().load(API_TV + itemsMovie?.poster_path).into(img_detail)
    }


}
