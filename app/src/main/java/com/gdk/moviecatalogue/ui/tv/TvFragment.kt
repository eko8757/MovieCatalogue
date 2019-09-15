package com.gdk.moviecatalogue.ui.tv

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
import com.gdk.moviecatalogue.presenter.tv.TvPresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_tv.view.*

class TvFragment : Fragment(), MainView.TvShowView, TvAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mAdapter: TvAdapter
    private lateinit var mPresenter: TvPresenter
    private lateinit var dataGlobal: ArrayList<ResponseTv.ResultTvShow>
    private val KEY_TV = "DataTv"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: BaseApi = BaseApi.create()
        view.rv_tv_show.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEY_TV))
        } else {
            mPresenter = TvPresenter(this, factory)
            getData()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(KEY_TV, dataGlobal)
    }

    override fun onItemClickListener(position: Int) {
        context?.let { mPresenter.goToDetailTvShow(it, position) }
    }

    override fun getData() {
        mPresenter.getTvShow()
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

    override fun showData(data: ArrayList<ResponseTv.ResultTvShow>) {
        mAdapter = TvAdapter(data)
        view?.rv_tv_show?.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }
}
