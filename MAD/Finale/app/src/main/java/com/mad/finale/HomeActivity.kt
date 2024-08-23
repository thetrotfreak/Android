package com.mad.finale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.Shop
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mad.finale.ui.theme.FinaleTheme
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinaleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
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
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { MyAppTopBar(drawerState = navigationDrawerState) },
            bottomBar = { MyAppBottomBar(navController = mainScreenNavController) },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Compose") },
//                    icon = { Icon(imageVector = Icons.Outlined.Edit, contentDescription = null) },
//                    onClick = {
//                        navController.navigate(Routes.ComposeEmailScreen.route)
//                    },
//                )
//            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { padding ->
            NavHost(
                navController = mainScreenNavController,
                startDestination = Routes.NavigationBarHome.route,
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
                composable(Routes.NavigationBarHome.route) {
                    HomeScreen(
                        modifier = modifier,
                        navController = mainScreenNavController
                    )
                }
                composable(
                    route = Routes.ProductScreen.route,
                    arguments = listOf(
                        navArgument(
                            name = "productData"
                        ) {
                            type = NavType.ParcelableType(Product::class.java)
                        }
                    )
                ) { ProductScreen(modifier = modifier, navController = mainScreenNavController) }
                composable(Routes.NavigationBarYou.route) { YouScreen(modifier = modifier) }
                composable(Routes.NavigationBarCart.route) {
                    CartScreen(
                        cartItems = listOf(
                            CartItem(Product("Product 1", "", 9.99), 1u),
                            CartItem(Product("Product 2", "", 14.99), 2u),
                            CartItem(Product("Product 3", "", 7.99), 3u)
                        ),
                        onBuyClick = {},
                        onPayClick = {},
                        modifier = modifier
                    )
                }
            }
        }
        if (navigationDrawerState.isOpen) {
            MyAppNavigationDrawer(
                drawerState = navigationDrawerState,
                navController = mainScreenNavController
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppTopBar(modifier: Modifier = Modifier, drawerState: DrawerState) {
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
        placeholder = { Text("Search for products") },
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
                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
            }
        },
    ) {
    }
}

@Composable
fun MyAppBottomBar(modifier: Modifier = Modifier, navController: NavController) {
    var selectedItem by remember {
        mutableStateOf<BottomNavItem?>(null)
    }

    NavigationBar {
        val items = listOf(
            BottomNavItem.Home,
            BottomNavItem.You,
            BottomNavItem.Cart
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem == item,
                onClick = {
                    selectedItem = item
                    navController.navigate(item.route)
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                alwaysShowLabel = true,
            )
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    object Home : BottomNavItem(Routes.NavigationBarHome.route, Icons.Outlined.Home, "Home")
    object You : BottomNavItem(Routes.NavigationBarYou.route, Icons.Outlined.Person, "You")
    object Cart : BottomNavItem(Routes.NavigationBarCart.route, Icons.Outlined.ShoppingCart, "Cart")
}

@Composable
fun MyAppNavigationDrawer(
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
                        Icon(imageVector = Icons.Filled.Shop, contentDescription = null)
                        Spacer(modifier = modifier.size(8.dp))
                        Text(
                            text = "Shop",
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
                        label = { Text("Electronics") },
                        icon = { Icon(Icons.Outlined.AllInbox, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerAllInboxes.route)
                        },
                    )
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Grocery") },
                        icon = { Icon(Icons.Outlined.Inbox, null) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Routes.NavigationDrawerPrimary.route)
                        },
                    )
//                    NavigationDrawerItem(
//                        label = { Text("Social") },
//                        icon = { Icon(Icons.Outlined.Group, null) },
//                        selected = false,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            navController.navigate(Routes.NavigationDrawerSocial.route)
//                        },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Forums") },
//                        icon = { Icon(Icons.Outlined.Forum, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Updates") },
//                        icon = { Icon(Icons.Outlined.Flag, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    HorizontalDivider()
//                    NavigationDrawerItem(
//                        label = { Text("Starred") },
//                        icon = { Icon(Icons.Outlined.StarOutline, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Snoozed") },
//                        icon = { Icon(Icons.Outlined.AccessTime, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Important") },
//                        icon = { Icon(Icons.AutoMirrored.Outlined.Label, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Sent") },
//                        icon = { Icon(Icons.AutoMirrored.Outlined.Send, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Drafts") },
//                        icon = { Icon(Icons.AutoMirrored.Outlined.InsertDriveFile, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Bin") },
//                        icon = { Icon(Icons.Outlined.Delete, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    HorizontalDivider()
//                    NavigationDrawerItem(
//                        label = { Text("Settings") },
//                        icon = { Icon(Icons.Outlined.Settings, null) },
//                        selected = false,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            navController.navigate(Routes.NavigationDrawerSettings.route)
//                        },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Send feedback") },
//                        icon = { Icon(Icons.Outlined.Feedback, null) },
//                        selected = false,
//                        onClick = { /*TODO*/ },
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Help") },
//                        icon = { Icon(Icons.AutoMirrored.Outlined.HelpOutline, null) },
//                        selected = false,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            navController.navigate(Routes.NavigationDrawerHelp.route)
//                        },
//                    )
//                    HorizontalDivider()
//                    NavigationDrawerItem(
//                        label = { Text("More apps by Google") },
//                        icon = { Icon(Icons.Outlined.Apps, null) },
//                        selected = false,
//                        onClick = {
//                            scope.launch { drawerState.close() }
//                            navController.navigate(Routes.NavigationDrawerMoreApps.route)
//                        },
//                    )
                }
            }
        }
    ) {
    }
}