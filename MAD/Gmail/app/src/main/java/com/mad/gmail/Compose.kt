package com.mad.gmail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.InsertDriveFile
import androidx.compose.material.icons.automirrored.outlined.Label
import androidx.compose.material.icons.automirrored.outlined.Message
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Forum
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun GmailApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(Routes.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(Routes.ComposeEmailScreen.route) {
            ComposeEmailScreen(navController = navController)
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val navigationDrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val mainScreenNavController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { GmailTopBar(drawerState = navigationDrawerState) },
            bottomBar = { GmailBottomBar(navController = mainScreenNavController) },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    text = { Text("Compose") },
                    icon = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = null) },
                    onClick = {
                        navController.navigate(Routes.ComposeEmailScreen.route)
                    },
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            NavHost(
                navController = mainScreenNavController,
//                graph = navGraph,
                startDestination = Routes.NavigationBarEmail.route,
                modifier = modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            ) {
                composable(Routes.NavigationDrawerAllInboxes.route) { AllInboxes(modifier = modifier) }
                composable(Routes.NavigationDrawerPrimary.route) { Primary(modifier = modifier) }
                composable(Routes.NavigationDrawerSocial.route) { Social(modifier = modifier) }
                composable(Routes.NavigationDrawerSettings.route) { Settings(modifier = modifier) }
                composable(Routes.NavigationDrawerHelp.route) { Help(modifier = modifier) }
                composable(Routes.NavigationDrawerMoreApps.route) {
                    MoreApps(
                        modifier = modifier,
                        snackbarHostState = snackbarHostState
                    )
                }
                composable(Routes.NavigationBarEmail.route) { Email(modifier = modifier) }
                composable(Routes.NavigationBarChat.route) { Chat(modifier = modifier) }
                composable(Routes.NavigationBarMeet.route) { Meet(modifier = modifier) }
            }
        }
        if (navigationDrawerState.isOpen) {
            GmailNavigationDrawer(
                drawerState = navigationDrawerState,
                navController = mainScreenNavController
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmailTopBar(modifier: Modifier = Modifier, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var active by rememberSaveable {
        mutableStateOf(false)
    }
    SearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { active = false },
        active = active,
        onActiveChange = {
            active = it
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter),
        placeholder = { Text("Search in mail") },
        leadingIcon = {
            IconButton(
                onClick = {
                    if (drawerState.isClosed) {
                        scope.launch { drawerState.open() }
                    }
                    if (drawerState.isOpen) {
                        scope.launch { drawerState.close() }
                    }
                }
            ) {
                Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
            }
        },
        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Outlined.AccountCircle, contentDescription = null)
            }
        },
    ) {
    }
}


@Composable
fun GmailBottomBar(modifier: Modifier = Modifier, navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { navController.navigate(Routes.NavigationBarEmail.route) },
            icon = { Icon(Icons.Outlined.Email, null) },
            label = { Text("Email") },
            alwaysShowLabel = true,
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.NavigationBarChat.route) },
            icon = { Icon(Icons.AutoMirrored.Outlined.Message, null) },
            label = { Text("Chat") },
            alwaysShowLabel = true,
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Routes.NavigationBarMeet.route) },
            icon = { Icon(Icons.Outlined.Videocam, null) },
            label = { Text("Meet") },
            alwaysShowLabel = true,
        )
    }
}

@Composable
fun GmailNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    navController: NavController
) {
    val scroll = rememberScrollState()
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = RectangleShape,
            ) {
                Surface(
                    modifier = modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = null)
                        Spacer(modifier = modifier.size(8.dp))
                        Text(
                            text = "Gmail",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(state = scroll)
                ) {
                    NavigationDrawerItem(
                        label = { Text("All inboxes") },
                        icon = { Icon(Icons.Outlined.AllInbox, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerAllInboxes.route)
                        },
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Primary") },
                        icon = { Icon(Icons.Outlined.Inbox, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerPrimary.route)
                        },
                    )
                    NavigationDrawerItem(
                        label = { Text("Social") },
                        icon = { Icon(Icons.Outlined.Group, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerSocial.route)
                        },
                    )
                    NavigationDrawerItem(
                        label = { Text("Forums") },
                        icon = { Icon(Icons.Outlined.Forum, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Updates") },
                        icon = { Icon(Icons.Outlined.Flag, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Starred") },
                        icon = { Icon(Icons.Outlined.StarOutline, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Snoozed") },
                        icon = { Icon(Icons.Outlined.AccessTime, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Important") },
                        icon = { Icon(Icons.AutoMirrored.Outlined.Label, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Sent") },
                        icon = { Icon(Icons.AutoMirrored.Outlined.Send, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Drafts") },
                        icon = { Icon(Icons.AutoMirrored.Outlined.InsertDriveFile, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Bin") },
                        icon = { Icon(Icons.Outlined.Delete, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        icon = { Icon(Icons.Outlined.Settings, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerSettings.route)
                        },
                    )
                    NavigationDrawerItem(
                        label = { Text("Send feedback") },
                        icon = { Icon(Icons.Outlined.Feedback, null) },
                        selected = false,
                        onClick = { /*TODO*/ },
                    )
                    NavigationDrawerItem(
                        label = { Text("Help") },
                        icon = { Icon(Icons.AutoMirrored.Outlined.HelpOutline, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerHelp.route)
                        },
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("More apps by Google") },
                        icon = { Icon(Icons.Outlined.Apps, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerMoreApps.route)
                        },
                    )
                }
            }
        }
    ) {
    }
}