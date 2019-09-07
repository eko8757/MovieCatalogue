package com.gdk.moviecatalogue.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.gdk.moviecatalogue.R
import com.gdk.moviecatalogue.ui.fragment.MovieFragment
import com.gdk.moviecatalogue.ui.fragment.TvFragment
import com.gdk.moviecatalogue.ui.fragment.favorites.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
}
