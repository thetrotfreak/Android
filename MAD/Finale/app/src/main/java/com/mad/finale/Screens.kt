package com.mad.finale

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale

sealed class Routes(val route: String) {
    data object NavigationDrawerAllInboxes : Routes("AllInboxes")
    data object NavigationDrawerPrimary : Routes("Primary")
    data object NavigationDrawerSocial : Routes("Social")
    data object NavigationDrawerSettings : Routes("Settings")
    data object NavigationDrawerHelp : Routes("Help")
    data object NavigationDrawerMoreApps : Routes("MoreApps")
    data object NavigationBarHome : Routes("Home")
    data object NavigationBarYou : Routes("You")
    data object NavigationBarCart : Routes("Cart")
    data object ProductScreen : Routes("ProductScreen/{productData}")
    data object MainScreen : Routes("MainScreen")
    data object ComposeEmailScreen : Routes("ComposeEmailScreen")
}

@Composable
fun MoreApps(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    val context = LocalContext.current
    val apps = listOf(
        "com.example.myfirstapp",
        "com.github.thetrotfreak.calculator2",
        "com.github.thetrotfreak.theactivitylifecycle",
        "com.github.thetrotfreak.muacompose",
        "com.android.developer.codelabs.greetingcard",
        "com.android.developer.codelabs.businesscard",
        "com.android.developer.codelabs.composequadrant",
        "com.mad.esetwo"
    )
    val scope = rememberCoroutineScope()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        contentPadding = PaddingValues(all = 8.dp),
        modifier = modifier.fillMaxSize()
    ) {
        apps.forEachIndexed { index, appName ->
            item {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .size(128.dp, 128.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    onClick = {
                        val launchIntent = context.packageManager.getLaunchIntentForPackage(appName)
                        if (launchIntent != null) {
                            context.startActivity(launchIntent)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = appName.substringAfterLast('.')
                                        .plus(" is not installed."),
                                    actionLabel = "OK",
                                    withDismissAction = false,
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    },
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        BadgedBox(
                            badge = {
                                Badge {
                                    Text("${index + 1}")
                                }
                            },
                        ) {
                            Icon(imageVector = Icons.Filled.Android, contentDescription = null)
                        }
                        Text(
                            text = appName.substringAfterLast('.')
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AllInboxes(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Add more email accounts")
    }
}

@Composable
fun Primary(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Hurray! You have no unread emails")
    }
}

@Composable
fun Social(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Oops! You have no connections")
    }
}

@Composable
fun Settings(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Settings")
    }
}

@Composable
fun Help(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Help!")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    var activeCardIndex by remember {
        mutableStateOf<Int?>(null)
    }
    val products: List<Product> =
        LocalContext.current.resources.getStringArray(R.array.products).map { productString ->
            val productParts = productString.split(",")
            Product(
                name = productParts[0],
                description = productParts[1],
                price = productParts[2].toDouble()
            )
        }
    Box {
        LazyColumn(
            modifier = Modifier.padding(8.dp).fillMaxSize()
        ) {
            products.forEachIndexed { index, product ->
                item {
                    ProductCard(
                        product = product,
                        onMoreOptionsClick = {},
                        modifier = Modifier.combinedClickable(onLongClick = {
                            activeCardIndex = index
                        }, onClick = {
                            activeCardIndex = index
                            navController.graph.findNode(Routes.ProductScreen.route)?.let {
                                navController.navigate(it.id, Bundle().apply {
                                    putParcelable("productData", product)
                                })
                            }
                        })
                    )
                }
            }
        }
        if (activeCardIndex != null) {
            CardActionsSheet(onDismissSheet = { activeCardIndex = null })
        }
    }
}

@Composable
fun YouScreen(modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val name = produceState(initialValue = "") {
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.NAME] ?: ""
        }.collect {
            value = it
        }
    }.value
    val email = produceState(initialValue = "") {
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.EMAIL] ?: ""
        }.collect {
            value = it
        }
    }.value
    val birthdate = produceState(initialValue = "") {
        context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.BIRTHDATE] ?: ""
        }.collect {
            value = it
        }
    }.value

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
        OutlinedTextField(
            value = name,
            onValueChange = {},
            label = { Text("Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = birthdate,
            onValueChange = {},
            label = { Text("Birthdate") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled._123, contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        HorizontalDivider()
        Button(onClick = {
            scope.launch {
                context.dataStore.edit { preferences ->
                    preferences[PreferencesKeys.ONBOARDED] = false
                }
            }
        }) {
            Text(text = "Logout")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmailScreen(modifier: Modifier = Modifier, navController: NavController) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(modifier = modifier.fillMaxSize(), topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        }, actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.Attachment, contentDescription = null
                )
            }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null
                )
            }
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert, contentDescription = null
                )
            }
        })
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .consumeWindowInsets(it)
                .fillMaxSize()
        ) {
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text(text = "To ") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                prefix = { Text(text = "From ") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Subject") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer
                )
            )
            TextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxSize(),
                placeholder = { Text(text = "Compose email") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                )
            )
        }
    }
}