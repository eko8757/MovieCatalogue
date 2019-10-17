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
import androidx.recyclerview.widget.RecyclerView
import com.gdk.movieprovider.R
import com.gdk.movieprovider.adapter.MovieAdapter
import com.gdk.movieprovider.model.ResponseMovie
import com.gdk.movieprovider.presenter.movie.MoviePresenter
import com.gdk.movieprovider.view.MainView
import org.jetbrains.anko.support.v4.toast

class MovieFragment : Fragment(), MainView.MovieView, MovieAdapter.OnItemClickListener {

    private lateinit var adapter: MovieAdapter
    private lateinit var mPresenter: MoviePresenter
    private lateinit var dataGlobal: ArrayList<ResponseMovie.ResultMovie>
    private lateinit var recyclerView: RecyclerView
    private val MOVIE_KEY = "DataMovie"
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        recyclerView = view.findViewById(R.id.rv_movie_provider)

        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_KEY)) {
            showData(savedInstanceState.getParcelableArrayList(MOVIE_KEY))
        } else {
            mPresenter = MoviePresenter(this)
            getData()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(MOVIE_KEY, dataGlobal)
        }
    }

    @SuppressLint("WrongConstant")
    override fun showData(data: ArrayList<ResponseMovie.ResultMovie>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = MovieAdapter(data)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        adapter.notifyDataSetChanged()
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
        fun movieFavoriteInstance(): MovieFragment = MovieFragment()
    }

    override fun makeToast(msg: String) {
        toast(msg)
    }
}
