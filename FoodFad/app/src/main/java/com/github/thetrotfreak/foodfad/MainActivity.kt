package com.github.thetrotfreak.foodfad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.EmojiFoodBeverage
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.github.thetrotfreak.foodfad.ui.theme.FoodFadTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: FoodExchangeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FoodExchangeViewModel::class.java]
        setContent {
            FoodFadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    FoodScaffold()
                }
            }
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Food", "Community", "Shop")
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { /*TODO*/ },
            label = { Text("Food") },
            icon = { Icon(Icons.AutoMirrored.Filled.List, null) },
        )
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            label = { Text("Community") },
            icon = { Icon(Icons.Filled.Diversity1, null) })
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            label = { Text("Shop") },
            icon = { Icon(Icons.Filled.ShoppingCart, null) })
        NavigationBarItem(selected = false,
            onClick = { /*TODO*/ },
            label = { Text("Donate") },
            icon = { Icon(Icons.Filled.VolunteerActivism, null) })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodScaffold() {
//    var foodies = remember { mutableStateListOf<FoodItem>() }
    var presses by remember { mutableIntStateOf(0) }
    var foodies by remember {
        mutableStateOf<List<FoodItem>>(emptyList())
    }
    Scaffold(topBar = {
        TopAppBar(colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ), title = {
            Text("Food Fad")
        })
    }, bottomBar = {
        BottomAppBar {
            HomeScreen(modifier = Modifier)
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            foodies = foodies + FoodItem(
                name = "Grapes",
                description = "Bangalore blue",
                quantity = 3,
                expiryDate = System.currentTimeMillis(),
                owner = User(name = "Bivas", location = "")
            )

        }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(foodies.toList()) { food ->
                FoodItemCard(foodItem = food)
            }
        }
    }
}

@Composable
fun FoodItemCard(foodItem: FoodItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ), elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.Bottom
        ) {
            Icon(imageVector = Icons.Filled.EmojiFoodBeverage, contentDescription = null)
            Text(text = foodItem.name, style = MaterialTheme.typography.labelLarge)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = foodItem.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
