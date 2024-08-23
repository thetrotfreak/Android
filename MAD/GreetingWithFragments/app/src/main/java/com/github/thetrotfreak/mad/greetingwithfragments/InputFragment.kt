package com.github.thetrotfreak.mad.greetingwithfragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


class InputFragment : Fragment() {

    interface OnNextButtonClickListener {
        fun onNextButtonClick(name: String)
    }

    private lateinit var editTextName: EditText
    private lateinit var buttonNext: Button

    private var listener: OnNextButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        editTextName = view.findViewById(R.id.edit_text_name)
        buttonNext = view.findViewById(R.id.button_next)

        buttonNext.setOnClickListener {
            val name = editTextName.text.toString().trim()
            if (name.isNotEmpty() && listener != null) {
                listener!!.onNextButtonClick(name)
            } else {
                Toast.makeText(activity, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnNextButtonClickListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnNextButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
