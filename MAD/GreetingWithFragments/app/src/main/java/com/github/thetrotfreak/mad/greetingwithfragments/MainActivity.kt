package com.github.thetrotfreak.mad.greetingwithfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), InputFragment.OnNextButtonClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        // Load InputFragment initially
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, InputFragment())
            .commit()
    }

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_input -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, InputFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

//                R.id.navigation_display -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.fragment_container, DisplayFragment()).commit()
//                    return@OnNavigationItemSelectedListener true
//                }

                R.id.navigation_about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, AboutFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, SettingsFragment()).commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onNextButtonClick(name: String) {
        val displayFragment = DisplayFragment.newInstance(name)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, displayFragment)
            .addToBackStack(null).commit()
    }
}