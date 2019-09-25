package com.gdk.moviecatalogue.ui.tv

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.adapter.TvAdapter
import com.gdk.moviecatalogue.model.ResponseTv
import com.gdk.moviecatalogue.presenter.tv.TvPresenter
import com.gdk.moviecatalogue.services.BaseApi
import com.gdk.moviecatalogue.util.invisible
import com.gdk.moviecatalogue.util.visible
import com.gdk.moviecatalogue.view.MainView
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.fragment_tv.*
import kotlinx.android.synthetic.main.fragment_tv.btn_delete
import kotlinx.android.synthetic.main.fragment_tv.view.*
import kotlinx.android.synthetic.main.fragment_tv.view.btn_delete
import kotlinx.android.synthetic.main.fragment_tv.view.search_tv

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
        setHasOptionsMenu(true)
        searchView(view)

        val factory: BaseApi = BaseApi.create()
        view.rv_tv_show.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        if (savedInstanceState != null) {
            showData(savedInstanceState.getParcelableArrayList(KEY_TV))
        } else {
            mPresenter = TvPresenter(this, factory)
            getData()
        }
    }

    override fun showData(data: ArrayList<ResponseTv.ResultTvShow>) {
        mAdapter = TvAdapter(data)
        view?.rv_tv_show?.adapter = mAdapter
        mAdapter.setOnItemClickListener(this)
        this.dataGlobal = data
    }

    private fun searchView(view: View) {
        view.search_tv?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_tv.text.toString().isNotEmpty()) {
                    btn_delete.visible()
                } else {
                    btn_delete.invisible()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        view.search_tv?.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                mPresenter.getData(search_tv.text.toString())
            }
            hideKeyboard()
            true
        }

        view.btn_delete.setOnClickListener {
            search_tv.setText("")
            mPresenter.getTvShow()
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::dataGlobal.isInitialized) {
            outState.putParcelableArrayList(KEY_TV, dataGlobal)
        }
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
}
