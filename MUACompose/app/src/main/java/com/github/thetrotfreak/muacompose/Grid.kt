package com.github.thetrotfreak.muacompose

import android.app.Activity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material.icons.outlined._123
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewApp(modifier: Modifier = Modifier) {
    // to exit the activity via a dialog box action
    val activity = LocalContext.current as? Activity

    // haptic for long press of a card
    val haptics = LocalHapticFeedback.current

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var navigationIconState by remember {
        mutableStateOf(false)
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    var openDialog by remember {
        mutableStateOf(false)
    }
    var contextCardIndex by remember {
        mutableStateOf<Int?>(null)
    }
    var activeCardIndex by remember {
        mutableStateOf<Int?>(null)
    }
    var popupMenu by remember {
        mutableStateOf(false)
    }
    var popupMenuOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var popupMenuHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
    val drawerScope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            TopAppBar(
                title = { Text(text = "Numbers") },
                navigationIcon = {
                    IconToggleButton(
                        checked = navigationIconState,
                        onCheckedChange = {
                            navigationIconState = it
                        }
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
                            modifier = Modifier.clickable { expanded = false; openDialog = true }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            items(32) { index ->
                ElevatedCard(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .indication(interactionSource, LocalIndication.current)
                        .onSizeChanged {
                            popupMenuHeight = with(density) { it.height.toDp() }
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    val press = PressInteraction.Press(it)
                                    interactionSource.emit(press)
                                    tryAwaitRelease()
                                    interactionSource.emit(PressInteraction.Release(press))
                                },
                                onTap = { tapOffset ->
                                    popupMenuOffset =
                                        DpOffset(tapOffset.x.toDp(), tapOffset.y.toDp())
                                }
                            )
                        }
                        .combinedClickable(
                            onLongClick = {
                                haptics.performHapticFeedback(
                                    HapticFeedbackType.LongPress
                                )
                                contextCardIndex = index
                            },
                            onClick = {
                                popupMenu = true
                                activeCardIndex = index
                            }
                        )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "$index", style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }
        }
        if(navigationIconState) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        ListItem(
                            headlineContent = { Text("Home") },
                            leadingContent = { Icon(Icons.Outlined.Home, null) },
                            modifier = Modifier.clickable {}
                        )
                        ListItem(
                            headlineContent = { Text("FeedbackScreen") },
                            leadingContent = { Icon(Icons.Outlined.Feedback, null) },
                            modifier = Modifier.clickable {}
                        )
                        ListItem(
                            headlineContent = { Text("LengthyColumn") },
                            leadingContent = { Icon(Icons.Outlined._123, null) },
                            modifier = Modifier.clickable {}
                        )
                    }
                }
            ) {
                Text(text = "hello")
            }
        }
        if (openDialog) {
            ExitToAppDialog(
                onDismissRequest = { openDialog = false },
                onConfirmation = { activity?.finishAffinity() }
            )
        }
        if (contextCardIndex != null) {
            CardActionsSheet(
                onDismissSheet = { contextCardIndex = null }
            )
        }
        if (popupMenu) {
            DropdownMenu(
                expanded = true,
                onDismissRequest = { popupMenu = false },
                offset = popupMenuOffset.copy(
                    y = popupMenuOffset.y - popupMenuHeight
                )
            ) {
                ListItem(
                    headlineContent = { Text("Increment") },
                    leadingContent = { Icon(Icons.Outlined.Add, null) },
                    modifier = Modifier.clickable {}
                )
                ListItem(
                    headlineContent = { Text("Decrement") },
                    leadingContent = { Icon(Icons.Outlined.Remove, null) }
                )
                ListItem(
                    headlineContent = { Text("Restore") },
                    leadingContent = { Icon(Icons.Outlined.Restore, null) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CardActionsSheet(
    onDismissSheet: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissSheet
    ) {
        ListItem(
            headlineContent = { Text("Details") },
            leadingContent = { Icon(Icons.Outlined.Info, null) },
            modifier = Modifier.clickable {}
        )
        ListItem(
            headlineContent = { Text("Add to favorites") },
            leadingContent = { Icon(Icons.Outlined.FavoriteBorder, null) }
        )
        ListItem(
            headlineContent = { Text("Remove") },
            leadingContent = { Icon(Icons.Outlined.DeleteOutline, null) }
        )
    }
}