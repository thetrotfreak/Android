package com.mad.finale

import android.os.Parcelable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val name: String = "Name",
    val description: String = "Description",
    val price: Double = 0.0
) : Parcelable

data class CartItem(
    val product: Product,
    val quantity: UInt
)

data class Profile(
    var email: String?,
    var name: String?,
    var birthdate: String?,
    val pfp: ImageVector = Icons.Default.Person
)