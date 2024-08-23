package com.github.thetrotfreak.mad.greetingwithfragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val versionTextView: TextView = view.findViewById(R.id.text_view_version)
        val appVersion = requireContext().packageManager.getPackageInfo(
            requireContext().packageName,
            0
        ).versionName
        versionTextView.text = "App Version: $appVersion"

        val developerTextView: TextView = view.findViewById(R.id.text_view_developer)
        developerTextView.text = "Developer: Bivas Kumar"

        val emailButton: Button = view.findViewById(R.id.button_email)
        emailButton.setOnClickListener {
            // Open email client with pre-filled email address
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:bivas.kumar@mca.christuniversity.in")
            }
            startActivity(emailIntent)
        }

        return view
    }
}
