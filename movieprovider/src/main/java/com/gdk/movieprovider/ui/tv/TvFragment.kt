package com.gdk.movieprovider.ui.tv

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.gdk.movieprovider.R
import com.gdk.movieprovider.adapter.TvAdapter
import com.gdk.movieprovider.model.ResponseTv
import com.gdk.movieprovider.presenter.tv.TvPresenter
import com.gdk.movieprovider.ui.movie.MovieFragment
import com.gdk.movieprovider.view.MainView
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.view.*
import org.jetbrains.anko.support.v4.progressDialog
import org.jetbrains.anko.support.v4.toast

class TvFragment : Fragment(), MainView.TvShowView, TvAdapter.OnItemClickListener {

    private lateinit var adapter: TvAdapter
    private lateinit var mPresenter: TvPresenter
    private lateinit var dataGlobal: ArrayList<ResponseTv.ResultTvShow>
    private lateinit var progressDialog: ProgressDialog
    private val TV_KEY = "DataTv"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_tv_provider.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(TV_KEY))
        } else {
            mPresenter = TvPresenter(this)
            getData()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(TV_KEY, dataGlobal)
        }
    }

    override fun showData(data: ArrayList<ResponseTv.ResultTvShow>) {
        adapter = TvAdapter(data)
        view?.rv_tv_provider?.adapter = adapter
        adapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun getData() {
        context?.let { mPresenter.getTvShow(it) }
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
        fun tvFavoriteInstance(): TvFragment = TvFragment()
    }
}
