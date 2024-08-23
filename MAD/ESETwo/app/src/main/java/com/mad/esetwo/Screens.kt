package com.mad.esetwo

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Live(modifier: Modifier = Modifier, padding: PaddingValues) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(padding)
            .consumeWindowInsets(padding)
//            .padding(8.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val checkedList = remember { mutableStateListOf<Int>() }
        val options = listOf("Twitter", "Instagram", "Facebook")
        val icons = listOf(
            painterResource(id = R.drawable.twitterx),
            painterResource(id = R.drawable.instagram),
            painterResource(id = R.drawable.facebook),
        )
        val intents = listOf(
            Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/IPL")),
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/iplt20/")),
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/IPL/")),

            )
        WebViewComposable(url = "https://www.iplt20.com/videos/highlights")
        Spacer(modifier = Modifier.size(4.dp))
        MultiChoiceSegmentedButtonRow {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    icon = {
                        SegmentedButtonDefaults.Icon(active = false) {
                            Icon(
                                painter = icons[index],
                                contentDescription = null,
                                modifier = Modifier.size(SegmentedButtonDefaults.IconSize)
                            )
                        }
                    },
                    onCheckedChange = { checked ->
                        if (checked) {
                            checkedList.add(index)
                            context.startActivity(intents[index])
                        } else {
                            checkedList.remove(index)
                        }
                    },
                    checked = checkedList.contains(index)
                ) {
                    Text(label)
                }
            }
        }
    }
}

@Composable
fun Player(modifier: Modifier = Modifier, incomingPadding: PaddingValues) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
            .padding(incomingPadding)
            .consumeWindowInsets(incomingPadding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            PlayerCard(team = "RCB", name = "Virat Kohli", onMoreOptionsClick = {})
        }
        item {
            PlayerCard(team = "RR", name = "Riyan Parag", onMoreOptionsClick = {})
        }
    }
}

@Composable
fun Team(modifier: Modifier = Modifier, padding: PaddingValues) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = modifier
            .padding(padding)
            .consumeWindowInsets(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TeamCard(shortName = "CSK", longName = "Chennai Super Kings", onMoreOptionsClick = {})
        }
        item {
            TeamCard(
                shortName = "RCB",
                longName = "Royal Challengers Bangalore",
                onMoreOptionsClick = {})
        }
    }
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
        title = { Text(text = "Exit?") },
        text = {
            Text(
                text = "This will exit the app and take you to the homescreen.",
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
            .height(320.dp)
            .padding(12.dp)
            .aspectRatio(21f / 9f),
    ) {
        AndroidView(factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
            }
        }, update = { view ->
            view.loadUrl(url)
        })
    }
}