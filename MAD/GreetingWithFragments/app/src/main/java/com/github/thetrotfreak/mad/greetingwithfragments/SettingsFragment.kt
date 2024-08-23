package com.github.thetrotfreak.mad.greetingwithfragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        sharedPreferences =
            requireContext().getSharedPreferences("ThemePrefs", Context.MODE_PRIVATE)

        val themeSwitch: SwitchCompat = view.findViewById(R.id.switch_theme)
        themeSwitch.isChecked = sharedPreferences.getBoolean("isDarkThemeEnabled", false)
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Save theme state to SharedPreferences
            sharedPreferences.edit().putBoolean("isDarkThemeEnabled", isChecked).apply()

            // Handle theme change
            if (isChecked) {
                // Apply dark theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Apply light theme
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return view
    }
}