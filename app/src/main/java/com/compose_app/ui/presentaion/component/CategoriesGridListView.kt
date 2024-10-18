package com.compose_app.ui.presentaion.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose_app.core.utils.Results
import com.compose_app.domain.entities.Category
import com.compose_app.ui.presentaion.component.card_item.CardItem

@Composable
fun CategoriesGridListView(
    modifier: Modifier = Modifier,
    categoriesState: Results.Success<List<Category>>,
    homeScreen: Boolean = false,
    onCartClick: (Category) -> Unit = {},
    onAddItemClicked: (Category) -> Unit = {},
) {
    // Category vertical grid
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        // Category cart item
        items(categoriesState.data.size) { index ->
            val category = categoriesState.data[index]
            CardItem(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = category.image,
                title = category.title,
                homeScreen = homeScreen,
                onAddItemClicked = {
                    onAddItemClicked(category)
                },
                onCartClick = { onCartClick(category) },
            )
        }
    }
}
