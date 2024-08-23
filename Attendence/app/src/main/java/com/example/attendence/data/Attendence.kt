package com.example.attendence.data

data class Attendence (val course: String,
                       var conducted: Int,
                       var present: Int,
                       var target: Float) {
    var attenper = present.toFloat() / conducted.toFloat() * 100
}
