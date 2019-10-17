package com.gdk.movieprovider.ui.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdk.movieprovider.BuildConfig
import com.gdk.movieprovider.R
import com.gdk.movieprovider.presenter.tv.DetailTvPresenter
import com.gdk.movieprovider.view.DetailView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTv : AppCompatActivity(), DetailView.ViewTV {

    private lateinit var mPresenter: DetailView.PresenterTV

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv)
        mPresenter = DetailTvPresenter(this)
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

    override fun showData(image: String, title: String, firstAir: String, rating: String, description: String) {
        Picasso.get().load(BuildConfig.MOVIE_PATH + image).into(img_detail)
        tv_title_detail.text = title
        tv_release_date.text = firstAir
        tv_vote_average.text = rating
        tv_desc_detail.text = description
    }
}
