package com.gdk.moviecatalogue.ui.movie

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
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.presenter.movie.MoviePresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment(), MainView.MovieView, MovieAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mAdapter: MovieAdapter
    private lateinit var mPresenter: MoviePresenter
    private lateinit var dataGlobal: ArrayList<ResponseMovie.ResultMovie>
    private val KEY_MOVIE = "DataMovie"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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

    override fun showData(data: ArrayList<ResponseMovie.ResultMovie>) {
        mAdapter = MovieAdapter(data)
        view?.rv_movie?.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            outState.putParcelableArrayList(KEY_MOVIE, dataGlobal)
        }
    }

    override fun getData() {
        mPresenter.getMovie()
    }

    override fun onItemClick(pos: Int) {
        context?.let { mPresenter.goToDetailMovie(it, pos) }
    }

    override fun showProgress() {
        progress = ProgressDialog(activity, R.style.CustomProgressDialog)
        progress.setCanceledOnTouchOutside(false)
        progress.setMessage("Please wait...")
        progress.show()
    }

    override fun hideProgress() {
        progress.dismiss()
    }
}
