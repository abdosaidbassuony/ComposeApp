package com.compose_app.ui.presentaion.component.card_item

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose_app.R

@Composable
fun AddItemIcon(isAdded: Boolean, onAddItemClicked: () -> Unit) {
    IconButton(
        onClick = onAddItemClicked,
    ) {
        //Add item icon with check mark if item is added
        Icon(
            modifier = Modifier.size(24.dp), imageVector = if (isAdded) {
                Icons.Filled.CheckCircle
            } else {
                Icons.Filled.AddCircle
            }, tint = Color.White, contentDescription = stringResource(R.string.remove_item)
        )
    }
}
