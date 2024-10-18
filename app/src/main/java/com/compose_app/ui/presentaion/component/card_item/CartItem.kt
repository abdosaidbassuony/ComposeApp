package com.compose_app.ui.presentaion.component.card_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    price: Double? = null,
    homeScreen: Boolean = false,
    isAdded: Boolean = false,
    onCartClick: () -> Unit = {},
    onAddItemClicked: () -> Unit = {},
) {
    //Cart item card
    Card(
        modifier = modifier,
        elevation = 2.dp, onClick = onCartClick,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            //Item image
            CardImage(imageUrl)
            //Add item icon
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                //Item name and price
                ItemNameAndPrice(title, price)
                //Navigate to details icon
                if (homeScreen) NavigateToDetailsIcon()
            }
        }
        if (!homeScreen) Box(contentAlignment = Alignment.TopEnd) {
            AddItemIcon(isAdded, onAddItemClicked)
        }
    }
}







