package com.compose_app.ui.presentaion.component.card_item

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ItemNameAndPrice(title: String, price: Double?) {
    Column {
        //	Item name
        Text(
            text = title,
            fontWeight = FontWeight.Light,
            color = Color.Gray
        )
        //	Item price if available
        if (price != null)
            Text(
                text = "$$price",
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
    }
}
