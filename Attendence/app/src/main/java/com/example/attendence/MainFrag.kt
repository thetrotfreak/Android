package com.example.attendence

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendence.adapter.AttenAdapter
import com.example.attendence.data.Attendence
import com.example.attendence.databinding.FragmentMainBinding
import com.example.attendence.model.DataSource


class MainFrag : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var myData: List<Attendence>
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.navView.bringToFront()
        setHasOptionsMenu(true);

        myData = DataSource().loadCourses()
        val adapter  = AttenAdapter(myData)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        toggle = ActionBarDrawerToggle(activity, binding.drawerLayout,R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
             R.id.miItem1 -> Toast.makeText(context,"Clicked item 1", Toast.LENGTH_SHORT).show()
            }
            true
        }

        //fab
        binding.fab.setOnClickListener {
            val attenForm = AttenForm()
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fgFragment, attenForm)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

