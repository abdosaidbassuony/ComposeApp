package com.compose_app.ui.presentaion.component.card_item

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigateToDetailsIcon() {
    Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        contentDescription = "Navigate to details"
    )
}