package com.example.attendence.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.attendence.R
import com.example.attendence.data.Attendence
import com.example.attendence.databinding.ItemAttenBinding

class AttenAdapter (
    private var courses: List<Attendence>
): RecyclerView.Adapter<AttenAdapter.AttenViewHolder>() {

    inner class  AttenViewHolder(val binding: ItemAttenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttenViewHolder {
        val view  = LayoutInflater.from(parent.context)
        val binding = ItemAttenBinding.inflate(view, parent, false)
        return AttenViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: AttenViewHolder, position: Int) {
        holder.binding.apply {
            courseName.text = courses[position].course
            courseAtten.text = courses[position].attenper.toString()
        }
    }
}