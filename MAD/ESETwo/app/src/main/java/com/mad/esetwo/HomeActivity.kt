package com.mad.esetwo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mad.esetwo.ui.theme.ESETwoTheme

class HomeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ESETwoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 640, widthDp = 420)
@Composable
fun GreetingPreview2() {
    ESETwoTheme {
//        Live()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun Home(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as? Activity
    var openDialog by remember {
        mutableStateOf(false)
    }
    var navigationIconState by remember {
        mutableStateOf(false)
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var expanded by remember {
        mutableStateOf(false)
    }
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = { Text("IPL20") },
                navigationIcon = {
                    IconToggleButton(
                        checked = navigationIconState,
                        onCheckedChange = { navigationIconState = it }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ListItem(
                            headlineContent = { Text("Sort") },
                            leadingContent = { Icon(Icons.AutoMirrored.Filled.Sort, null) },
                            modifier = Modifier.clickable {}
                        )
                        ListItem(
                            headlineContent = { Text("Help") },
                            leadingContent = { Icon(Icons.AutoMirrored.Filled.Help, null) },
                            modifier = Modifier.clickable {}
                        )
                        HorizontalDivider()
                        ListItem(
                            headlineContent = { Text("Exit") },
                            leadingContent = { Icon(Icons.AutoMirrored.Filled.ExitToApp, null) },
                            modifier = Modifier.clickable {
                                expanded = false
                                openDialog = true
                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IPLBottomAppBar(
                    modifier = Modifier,
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Live.route
        ) {
            composable(Screen.Live.route) { Live(padding = innerPadding) }
            composable(Screen.Player.route) { Player(incomingPadding = innerPadding) }
            composable(Screen.Team.route) { Team(padding = innerPadding) }

        }
        if (openDialog) {
            ExitToAppDialog(
                onDismissRequest = { openDialog = false },
                onConfirmation = { activity?.finishAffinity() }
            )
        }
//        run {
//            Player(incomingPadding = innerPadding)
//        }
    }
}

@Composable
fun IPLBottomAppBar(modifier: Modifier, navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screen.Live.route) },
            label = { Text("Live") },
            icon = { Icon(Icons.Filled.LiveTv, null) },
        )
        NavigationBarItem(selected = false,
            onClick = { navController.navigate(Screen.Player.route) },
            label = { Text("Player") },
            icon = { Icon(Icons.Filled.Person, null) }
        )
        NavigationBarItem(selected = false,
            onClick = { navController.navigate(Screen.Team.route) },
            label = { Text("Team") },
            icon = { Icon(Icons.Filled.Groups, null) }
        )
    }
}
