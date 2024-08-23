package com.github.thetrotfreak.mad.greetingwithfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment


class DisplayFragment : Fragment() {

    companion object {
        private const val ARG_NAME = "name"

        fun newInstance(name: String): DisplayFragment {
            val fragment = DisplayFragment()
            val args = Bundle()
            args.putString(ARG_NAME, name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_display, container, false)

        val textViewWelcome = view.findViewById<TextView>(R.id.text_view_welcome)
        val name = arguments?.getString(ARG_NAME)
        textViewWelcome.text = getString(R.string.welcome_message, name)

        return view
    }
}


