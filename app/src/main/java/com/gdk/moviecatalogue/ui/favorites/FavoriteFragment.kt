package com.gdk.moviecatalogue.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.adapter.PagerAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager(vp_favorite)
        tab_favorite.setupWithViewPager(vp_favorite)
    }

    private fun setupViewPager(pager: ViewPager) {
        val adapter = fragmentManager?.let {
            PagerAdapter(it)
        }
        val movie = FavoriteMovieFragment.movieFavoriteInstance()
        adapter?.addFragment(movie, "Movie")
        val tv = FavoriteTvFragment.tvFavoriteInstance()
        adapter?.addFragment(tv, "Tv Show")
        pager.adapter = adapter
    }
}
