package com.github.thetrotfreak.foodfad

import android.text.format.DateUtils.DAY_IN_MILLIS
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.UUID

class FoodExchangeViewModel : ViewModel() {

    // Hardcoded data
    private val foodItems = listOf(
        FoodItem(
            id = UUID.randomUUID().toString(),
            name = "Fresh tomatoes",
            description = "Ripe and juicy tomatoes from my garden",
            quantity = 3,
            expiryDate = System.currentTimeMillis() + 2 * DAY_IN_MILLIS, // Expires in 2 days
            owner = User(id = "user1", name = "John Doe", location = "123 Main St")
        ),
        // ... add more food items
    )

    // Expose data as LiveData
    val foodItemsLiveData = MutableLiveData(foodItems)

    // Function to add a new food item (simulated for now)
    fun addFoodItem(foodItem: FoodItem) {
        // Update internal state (e.g., add to an in-memory list)
        foodItemsLiveData.value = foodItems.plus(foodItem)
    }
}
