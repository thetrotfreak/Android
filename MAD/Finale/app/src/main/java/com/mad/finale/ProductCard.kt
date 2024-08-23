package com.mad.finale

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardActionsSheet(
    onDismissSheet: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissSheet
    ) {
        ListItem(headlineContent = { Text("Details") },
            leadingContent = { Icon(Icons.Outlined.Info, null) },
            modifier = Modifier.clickable {})
        ListItem(headlineContent = { Text("Add to cart") },
            leadingContent = { Icon(Icons.Outlined.AddShoppingCart, null) },
            modifier = Modifier.clickable {})
        ListItem(headlineContent = { Text("Save for later") },
            leadingContent = { Icon(Icons.Outlined.FavoriteBorder, null) },
            modifier = Modifier.clickable {})
    }
}

@Composable
fun ProductCard(
    product: Product,
    onMoreOptionsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var popupMenu by remember {
        mutableStateOf(false)
    }

    ElevatedCard(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    ) {
        Box {
            Row(
                modifier = modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingBag,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = product.name, style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.description, style = MaterialTheme.typography.bodyMedium,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = product.price.toString(), style = MaterialTheme.typography.bodyMedium
                    )
                }
//                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        popupMenu = true
                        onMoreOptionsClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert, contentDescription = null
                    )
                }
            }
            if (popupMenu) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { popupMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Add to cart") },
                        onClick = { /*TODO*/ },
                        leadingIcon = { Icon(Icons.Default.AddShoppingCart, null) })
                    DropdownMenuItem(
                        text = { Text("Remove from cart") },
                        onClick = { /*TODO*/ },
                        leadingIcon = { Icon(Icons.Default.RemoveShoppingCart, null) })
                }
            }
        }
    }
}