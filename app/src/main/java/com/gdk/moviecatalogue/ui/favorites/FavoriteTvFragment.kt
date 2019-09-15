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
import com.gdk.moviecatalogue.adapter.TvAdapter
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.presenter.tv.FavoriteTvPresenter
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_favorite_tv.view.*

class FavoriteTvFragment : Fragment(), MainView.TvShowView, TvAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mPresenter: FavoriteTvPresenter
    private lateinit var adapter: TvAdapter
    private lateinit var dataGlobal: ArrayList<ResponseTv.ResultTvShow>
    private val KEYTVSHOW = "DataMovieFavorite"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_favorite_tv.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEYTVSHOW))
        } else {
            mPresenter = FavoriteTvPresenter(this, context)
            getData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(KEYTVSHOW, dataGlobal)
        }
    }

    override fun showData(data: ArrayList<ResponseTv.ResultTvShow>) {
        hideProgress()
        adapter = TvAdapter(data)
        this.dataGlobal = data
        view?.rv_favorite_tv?.adapter = adapter
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener(this)
    }

    override fun getData() {
        context?.let { mPresenter.getFavoriteTvShow(it) }
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

    override fun onItemClickListener(position: Int) {
        context?.let { mPresenter.toDetail(it, position) }
    }

    companion object {
        fun tvFavoriteInstance(): FavoriteTvFragment = FavoriteTvFragment()
    }

}
