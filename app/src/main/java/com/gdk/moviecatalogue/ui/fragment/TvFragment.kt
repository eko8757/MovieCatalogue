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
import com.gdk.moviecatalogue.adapter.TvAdapter
import com.gdk.moviecatalogue.model.TvResponse
import com.gdk.moviecatalogue.presenter.TvPresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.view.TvView
import kotlinx.android.synthetic.main.fragment_tv.view.*
import org.jetbrains.anko.support.v4.toast

class TvFragment : Fragment(), TvView, TvAdapter.OnItemClickListener {

    lateinit var progress: ProgressDialog
    private lateinit var mAdapter: TvAdapter
    private lateinit var mPresenter: TvPresenter
    private lateinit var dataGlobal: ArrayList<TvResponse.ResultTvShow>
    private val KEY_TV = "DataTv"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var factory: BaseApi = BaseApi.create()
        view.rv_tv_show.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEY_TV))
        } else {
            mPresenter = TvPresenter(this, factory)
            getData()
        }
    }

    override fun getData() {
        mPresenter.getDataTv()
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

    override fun showData(data: ArrayList<TvResponse.ResultTvShow>) {
        mAdapter = TvAdapter(data)
        view?.rv_tv_show?.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    override fun onItemClick(pos: Int) {
        toast(pos.toString())
    }
}
