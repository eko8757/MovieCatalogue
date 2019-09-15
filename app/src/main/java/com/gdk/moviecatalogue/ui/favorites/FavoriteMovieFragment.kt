package com.gdk.moviecatalogue.ui.favorites

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
import com.gdk.moviecatalogue.presenter.movie.FavoriteMoviePresenter
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_favorite_movie.view.*

class FavoriteMovieFragment : Fragment(), MainView.MovieView, MovieAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mPresenter: FavoriteMoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var dataGlobal: ArrayList<ResponseMovie.ResultMovie>
    private val KEYMOVIE = "DataMovieFavorite"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_favorite_movie.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEYMOVIE))
        } else {
            mPresenter = FavoriteMoviePresenter(this, context)
            getData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEYMOVIE, dataGlobal)
    }

    override fun showData(data: ArrayList<ResponseMovie.ResultMovie>) {
        hideProgress()
        adapter = MovieAdapter(data)
        view?.rv_favorite_movie?.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun getData() {
        context?.let { mPresenter.getFavoriteMovie(it) }
    }

    override fun showProgress() {
        progress = ProgressDialog(activity, R.style.CustomProgressDialog)
        progress.setCanceledOnTouchOutside(false)
        progress.setMessage(getString(R.string.loading_message))
        progress.show()
    }

    override fun hideProgress() {
        progress.dismiss()
    }

    override fun onItemClick(pos: Int) {
        context?.let { mPresenter.toDetail(it, pos) }
    }

    companion object {
        fun movieFavoriteInstance(): FavoriteMovieFragment = FavoriteMovieFragment()
    }

}
