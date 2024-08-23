package com.example.attendence

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.attendence.data.Attendence
import com.example.attendence.databinding.FragmentAttenFormBinding
import com.example.attendence.model.DataSource
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AttenForm : Fragment(R.layout.fragment_atten_form) {

    private lateinit var binding: FragmentAttenFormBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAttenFormBinding.bind(view)

        val fragmentManager = requireActivity().supportFragmentManager
        binding.topAppBar.setNavigationOnClickListener {
            fragmentManager.popBackStack()
        }

        binding.courseAddBtn.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Confirmation")
                .setMessage("Do you want to add ?")
                .setNegativeButton("Decline") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Accept") {_, _ ->
                    val courseName = binding.courseNameText.text.toString()
                    val con = binding.conductedText.text
                    val pre = binding.presentText.text
                    DataSource.data.add(Attendence(courseName,con.toString().toInt(),pre.toString().toInt(),85.0f))
                    val mainFrag = MainFrag()
                    fragmentManager.beginTransaction()
                        .replace(R.id.fgFragment, mainFrag)
                        .addToBackStack(null)
                        .commit()
                }
                .show()

        }


    }
}