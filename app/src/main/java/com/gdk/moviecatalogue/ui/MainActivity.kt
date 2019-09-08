package com.gdk.moviecatalogue.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.ui.movie.MovieFragment
import com.gdk.moviecatalogue.ui.tv.TvFragment
import com.gdk.moviecatalogue.ui.favorites.FavoriteFragment
import com.gdk.moviecatalogue.util.BottomNavigationViewListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationViewListener {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_bottom_navigation.inflateMenu(R.menu.bottom_navigation_main)
        fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.main_frame, MovieFragment()).commit()

        main_bottom_navigation.setOnNavigationItemSelectedListener {
            val id = it.itemId
            when(id) {
                R.id.action_movie -> fragment = MovieFragment()
                R.id.action_tv -> fragment = TvFragment()
                R.id.action_favorites -> fragment = FavoriteFragment()
            }

            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_frame, fragment)
            transaction.commit()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId ?: 0 == R.id.settings) {
            val i = Intent(this, SettingsActivity::class.java)
            startActivity(i)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showBottomNavigationView() {
        if (main_bottom_navigation.translationY >= main_bottom_navigation.height.toFloat())
            main_bottom_navigation.animate()
                .translationY(0f)
                .setDuration(400)
                .start()
    }

    override fun hideBottomNavigationView() {
        if (main_bottom_navigation.translationY == 0f)
            main_bottom_navigation.animate()
                .translationY(main_bottom_navigation.height.toFloat())
                .setDuration(250)
                .start()
    }
}
