package com.github.thetrotfreak.foodfad

import java.util.UUID

data class FoodItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val quantity: Int,
    val expiryDate: Long,
    val owner: User
)
