package com.gdk.moviecatalogue.ui.movie

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.adapter.MovieAdapter
import com.gdk.moviecatalogue.model.ResponseMovie
import com.gdk.moviecatalogue.presenter.movie.MoviePresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.util.invisible
import com.gdk.moviecatalogue.util.visible
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.search_movie

class MovieFragment : Fragment(), MainView.MovieView, MovieAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mAdapter: MovieAdapter
    private lateinit var mPresenter: MoviePresenter
    private lateinit var dataGlobal: ArrayList<ResponseMovie.ResultMovie>
    private val MOVIE_KEY = "DataMovie"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        searchView(view)

        val factory: BaseApi = BaseApi.create()
        view.rv_movie.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(MOVIE_KEY))
        } else {
            mPresenter = MoviePresenter(this, factory)
            getData()
        }
    }

    private fun searchView(view: View) {
        view.search_movie?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_movie.text.toString().isNotEmpty()) {
                    btn_delete.visible()
                } else {
                    btn_delete.invisible()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


        view.search_movie?.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mPresenter.getData(search_movie.text.toString())
            }
            hideKeyboard()
            true
        }

        view.btn_delete.setOnClickListener {
            search_movie.setText("")
            mPresenter.getMovie()
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun showData(data: ArrayList<ResponseMovie.ResultMovie>) {
        mAdapter = MovieAdapter(data)
        view?.rv_movie?.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(MOVIE_KEY, dataGlobal)
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
        progress.setMessage(getString(R.string.loading_message))
        progress.show()
    }

    override fun hideProgress() {
        progress.dismiss()
    }
}
