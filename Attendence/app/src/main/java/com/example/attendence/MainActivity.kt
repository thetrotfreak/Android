package com.example.attendence

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.attendence.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mainFrag = MainFrag()

        //starting first starting fragment
        changeFrag(mainFrag)
    }

    private fun changeFrag(fragment:Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fgFragment, fragment)
            commit()
        }
    }
}