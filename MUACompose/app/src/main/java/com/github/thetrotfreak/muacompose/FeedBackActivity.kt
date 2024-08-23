package com.github.thetrotfreak.muacompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.thetrotfreak.muacompose.ui.theme.MUAComposeTheme
import kotlinx.coroutines.launch

class FeedBackActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MUAComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "newapp") {
                        composable("newapp") { NewApp() }
//                        composable("feedbackscreen") { FeedBackScreen() }
                        composable("lengthycolumn") { LengthyColumn() }
                    }
//                    FeedBackScreen()
//                    LengthyColumn()
//                    NewApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LengthyColumn(modifier: Modifier = Modifier) {
//    val scrollState = rememberScrollState()
    Scaffold(topBar = {
        TopAppBar(scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = { Text(text = "Column") })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .then(Modifier.verticalScroll(rememberScrollState())),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            1.rangeTo(10).forEach {
                ElevatedCard(
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = "$it", style = MaterialTheme.typography.displayLarge)
                }
                Spacer(modifier = modifier.height(12.dp))
            }
        }
    }
}

@Preview(widthDp = 320, heightDp = 480)
@Composable
fun LengthyColumnPreview() {
    LengthyColumn()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedBackScreen(modifier: Modifier = Modifier, onNavigateTo: () -> Unit) {
    var checked by remember {
        mutableStateOf(true)
    }
    var sendFeedback by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val radioOptions = listOf("Male", "Female", "Other")
    val checkboxOptions = listOf("Jetpack Compose", "Flutter", "MDC-Android")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val (checkedOption, onOptionChecked) = remember { mutableStateOf(false) }
    val checkedOptions = remember { mutableStateOf(setOf<String>()) }
//    var vscrollState = rememberScrollState()
    val dateRangeState = rememberDateRangePickerState()
    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(hostState = snackbarHostState) {
            // https://codingwithrashid.com/change-snackbar-background-color-in-android-jetpack-compose/
            Snackbar(snackbarData = it, containerColor = MaterialTheme.colorScheme.primary)
        }
    }, topBar = {
        TopAppBar(scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Christ Forms")
            })
    }, floatingActionButton = {
        ExtendedFloatingActionButton(text = { Text(text = "Send") }, icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null
            )
        }, onClick = {
            // https://stackoverflow.com/a/66952979
            sendFeedback = true
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                //     .fillMaxSize()
                .verticalScroll(rememberScrollState(), true)
            // can not get it to scroll on AVD
        ) {
            WebViewComposable(url = "https://m.christuniversity.in/welcome-page")
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Record responses", style = MaterialTheme.typography.headlineSmall)
                Switch(checked = checked, onCheckedChange = { checked = it })
            }

            HorizontalDivider()
            Text(
                text = "You identify self as",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
            Column(Modifier.selectableGroup()) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = { onOptionSelected(text) },
                                role = Role.RadioButton
                            )
                            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption), onClick = null
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
            HorizontalDivider()
            Text(
                text = "Versed in",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
            Column {
                checkboxOptions.forEach { option ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = checkedOption,
                                onValueChange = { onOptionChecked(!checkedOption) },
                                role = Role.Checkbox
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        // https://codingwithrashid.com/checkbox-group-in-android-jetpack-compose/
                        Checkbox(checked = checkedOptions.value.contains(option),
                            onCheckedChange = { checked ->
                                val currentChecked = checkedOptions.value.toMutableSet()
                                if (checked) {
                                    currentChecked.add(option)
                                } else {
                                    currentChecked.remove(option)
                                }
                                checkedOptions.value = currentChecked
                            })
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }

                }
            }

            HorizontalDivider()
            DateRangePicker(
                state = dateRangeState,
                title = { Text(text = "") },
                headline = { Text(text = "Course duration") },
            )
        }
    }
    if (sendFeedback) {
        SendFeedBackDialog(
            onDismissRequest = { sendFeedback = false },
            onConfirmation = {
                //https://developer.android.com/jetpack/compose/components/snackbar#snackbar_with_action
                scope.launch {
                    sendFeedback = false
                    val result = snackbarHostState.showSnackbar(
                        message = "Your feedback was sent!", duration = SnackbarDuration.Short
                    )
                    when (result) {
                        SnackbarResult.ActionPerformed -> {
                        }

                        SnackbarResult.Dismissed -> {
                        }
                    }
                }
            },
            dialogTitle = "Please Confirm",
            dialogText = "Your feedback will be sent",
            icon = Icons.AutoMirrored.Filled.Send
        )
    }
}

@Composable
fun SendFeedBackDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(icon = {
        Icon(icon, contentDescription = null)
    }, title = {
        Text(text = dialogTitle)
    }, text = {
        Text(
            text = dialogText,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
    }, onDismissRequest = {
        onDismissRequest()
    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation()
        }) {
            Text("Confirm")
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest()
        }) {
            Text("Dismiss")
        }
    })
}

@Composable
fun ExitToAppDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = null
            )
        },
        title = { Text(text = "Do you want to exit?") },
        text = {
            Text(
                text = "",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            TextButton(onClick = { onConfirmation() }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text("Dismiss")
            }
        })
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewComposable(url: String) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .aspectRatio(21f / 9f),
    ) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true // Potential security risk
            }
        }, update = { view ->
            view.loadUrl(url)
        })
    }
}