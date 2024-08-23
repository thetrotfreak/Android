package com.github.thetrotfreak.foodfad

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val location: String
)
