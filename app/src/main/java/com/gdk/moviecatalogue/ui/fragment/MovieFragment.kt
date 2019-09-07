package com.gdk.moviecatalogue.ui.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.adapter.MovieAdapter
import com.gdk.moviecatalogue.model.MovieResponse
import com.gdk.moviecatalogue.presenter.MoviePresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.ui.activity.DetailActivity
import com.gdk.moviecatalogue.view.MovieView
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.toast

class MovieFragment : Fragment(), MovieView {

    lateinit var progress: ProgressDialog
    private lateinit var mAdapter: MovieAdapter
    private lateinit var mPresenter: MoviePresenter
    private lateinit var dataGlobal: ArrayList<MovieResponse.ResultMovie>
    private val KEY_MOVIE = "DataMovie"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: BaseApi = BaseApi.create()
        view.rv_movie.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEY_MOVIE))
        } else {
            mPresenter = MoviePresenter(this, factory)
            getData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.putParcelableArrayList(KEY_MOVIE, dataGlobal)
        }
    }

    override fun getData() {
        mPresenter.getDataMovie()
    }

    override fun makeToast(msg: String) {
        toast(msg)
    }

    override fun showProgress() {
        progress = ProgressDialog(activity)
        progress.setMessage("Please wait...")
        progress.show()
    }

    override fun hideProgress() {
        progress.dismiss()
    }

    override fun showData(data: ArrayList<MovieResponse.ResultMovie>) {
        mAdapter = MovieAdapter(data) {
            startActivity(intentFor<DetailActivity>("name" to it))
        }
        view?.rv_movie?.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
        this.dataGlobal = data
    }
}
