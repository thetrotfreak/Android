package com.mad.gmail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.util.Locale

sealed class Routes(val route: String) {
    data object NavigationDrawerAllInboxes : Routes("AllInboxes")
    data object NavigationDrawerPrimary : Routes("Primary")
    data object NavigationDrawerSocial : Routes("Social")
    data object NavigationDrawerSettings : Routes("Settings")
    data object NavigationDrawerHelp : Routes("Help")
    data object NavigationDrawerMoreApps : Routes("MoreApps")
    data object NavigationBarEmail : Routes("Email")
    data object NavigationBarChat : Routes("Chat")
    data object NavigationBarMeet : Routes("Meet")
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
                        val launchIntent =
                            context.packageManager.getLaunchIntentForPackage(appName)
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
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
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

@Composable
fun Email(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Click on the compose button to start emailing!")
    }
}

@Composable
fun Chat(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Start a chat with your contacts.")
    }
}

@Composable
fun Meet(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
        Text(text = "Join a Google Meet")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeEmailScreen(modifier: Modifier = Modifier, navController: NavController) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.Attachment,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
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