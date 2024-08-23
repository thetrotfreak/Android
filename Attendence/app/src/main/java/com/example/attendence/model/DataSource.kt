package com.example.attendence.model

import com.example.attendence.data.Attendence

class DataSource{
    companion object {
        val data = mutableListOf(
            Attendence("Android Programming",30,20,85.0f),
            Attendence("Natural Language Processing",30,28,85.0f)
        )
    }
    fun loadCourses(): List<Attendence> {
        return data
    }
}