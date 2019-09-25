package com.gdk.movieprovider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.gdk.movieprovider.adapter.PagerAdapter
import com.gdk.movieprovider.ui.movie.MovieFragment
import com.gdk.movieprovider.ui.tv.TvFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(vp_main)
        tab_main.setupWithViewPager(vp_main)
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = fragmentManager?.let {
            PagerAdapter(it)
        }
        val movie = MovieFragment.movieFavoriteInstance()
        adapter?.addFragment(movie, getString(R.string.title_movie_pager))
        val tv = TvFragment.tvFavoriteInstance()
        adapter?.addFragment(tv, getString(R.string.title_tv_pager))
        pager.adapter = adapter
    }
}
