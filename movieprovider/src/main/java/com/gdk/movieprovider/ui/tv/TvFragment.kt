package com.gdk.movieprovider.ui.tv

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
import com.gdk.movieprovider.adapter.TvAdapter
import com.gdk.movieprovider.model.ResponseTv
import com.gdk.movieprovider.presenter.tv.TvPresenter
import com.gdk.movieprovider.view.MainView

class TvFragment : Fragment(), MainView.TvShowView, TvAdapter.OnItemClickListener {

    private lateinit var adapter: TvAdapter
    private lateinit var mPresenter: TvPresenter
    private lateinit var dataGlobal: ArrayList<ResponseTv.ResultTvShow>
    private lateinit var progressDialog: ProgressDialog
    private lateinit var recyclerView: RecyclerView
    private val TV_KEY = "DataTvShow"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tv, container, false)
        recyclerView = view.findViewById(R.id.rv_tv_provider)

        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(TV_KEY))
        } else {
            mPresenter = TvPresenter(this)
            getData()
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(TV_KEY, dataGlobal)
        }
    }

    @SuppressLint("WrongConstant")
    override fun showData(data: ArrayList<ResponseTv.ResultTvShow>) {
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = TvAdapter(data)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        adapter.notifyDataSetChanged()
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
