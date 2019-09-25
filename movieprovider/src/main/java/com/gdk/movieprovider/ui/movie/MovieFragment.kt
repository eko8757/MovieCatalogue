package com.gdk.movieprovider.ui.movie

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.gdk.movieprovider.R
import com.gdk.movieprovider.adapter.MovieAdapter
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.presenter.movie.MoviePresenter
import com.gdk.movieprovider.view.MainView
import kotlinx.android.synthetic.main.fragment_movie.view.*

class MovieFragment : Fragment(), MainView.MovieView, MovieAdapter.OnItemClickListener {

    private lateinit var adapter: MovieAdapter
    private lateinit var mPresenter: MoviePresenter
    private lateinit var dataGlobal: ArrayList<ResponseMovie.ResultMovie>
    private val MOVIE_KEY = "moviekey"
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_movie_provider.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_KEY)) {
            showData(savedInstanceState.getParcelableArrayList(MOVIE_KEY))
        } else {
            mPresenter = MoviePresenter(this)
            getData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(MOVIE_KEY, dataGlobal)
        }
    }

    override fun showData(data: ArrayList<ResponseMovie.ResultMovie>) {
        adapter = MovieAdapter(data)
        view?.rv_movie_provider?.adapter = adapter
        adapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun getData() {
        context?.let { mPresenter.getMovie(it) }
    }

    override fun empty() {

    }

    override fun showProgress() {
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun onItemClick(pos: Int) {
        context?.let { mPresenter.toDetail(it, pos) }
    }

    companion object {
        fun movieFavoriteInstance(): MovieFragment= MovieFragment()
    }

}
