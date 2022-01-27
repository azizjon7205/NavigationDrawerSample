package com.example.navigationdrawersample

import PagerHomeFragment
import PagerMoviesFragment
import PagerNotificationsFragment
import PagerPhotosFragment
import PagerSettingsFragment
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var mDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvDrawer: NavigationView
    private lateinit var fragment: Fragment

    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        drawerToggle = ActionBarDrawerToggle(this,
            mDrawer,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close)
        drawerToggle.isDrawerIndicatorEnabled = true

        drawerToggle.syncState()

        mDrawer.addDrawerListener(drawerToggle)
    }

    private fun initViews() {
        mDrawer = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)
        nvDrawer = findViewById(R.id.nv_main)

        replaceFragment(PagerHomeFragment())
        title = "Home"
        nvDrawer.setNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_home -> replaceFragment(PagerHomeFragment())
                R.id.nav_photos -> replaceFragment(PagerPhotosFragment())
                R.id.nav_movies -> replaceFragment(PagerMoviesFragment())
                R.id.nav_notifications -> replaceFragment(PagerNotificationsFragment())
                R.id.nav_settings -> replaceFragment(PagerSettingsFragment())
                else -> replaceFragment(PagerHomeFragment())
            }
            it.isChecked = true
            title = it.title
            mDrawer.closeDrawers()
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            return@setNavigationItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        this.fragment = fragment
        val backStateName = fragment.javaClass.name
        val manager = supportFragmentManager
        val fragmentPopped = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped) {
            val ft = manager.beginTransaction()
            ft.replace(R.id.frame_pages, fragment)
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }

    override fun onBackPressed() {
        val count =  supportFragmentManager.backStackEntryCount
        if (count <= 1)
            finish()
        else{
            val title = supportFragmentManager.getBackStackEntryAt(count-2).name
            setTitle(title.toString())
            Log.d("RRR", title.toString())
            super.onBackPressed()
        }

    }

    fun setTitle(fragmentName: String){
        when(fragmentName){
            "PagerPhotosFragment" -> title = "Photos"
            "PagerHomeFragment" -> title = "Home"
            "PagerNotificationsFragment" -> title = "Notifications"
            "PagerMoviesFragment" -> title = "Movies"
            "PagerSettingsFragment" -> title = "Settings"
            else -> title = "Home"
        }
    }

}