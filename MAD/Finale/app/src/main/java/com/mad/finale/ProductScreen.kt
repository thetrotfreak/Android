package com.mad.finale

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var showNotification by remember {
        mutableStateOf(false)
    }
    val product = navController
        .currentBackStackEntry
        ?.arguments
        ?.getParcelable<Product>("productData")
//        ?.getParcelable("productData", Product::class.java)
    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Image section
            Icon(
                imageVector = Icons.Default.ShoppingBag,
                contentDescription = it.description,
            )
            // Product details section
            Text(
                text = it.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = it.description,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "₹${
                    String.format(
                        "%.2f",
                        it.price
                    )
                }",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
            )
            Button(onClick = { showNotification = showNotification.not() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Order Now")
            }
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Add to Cart")
            }
        }
        if (showNotification) {
            ShowNotification()
        }
    }
}