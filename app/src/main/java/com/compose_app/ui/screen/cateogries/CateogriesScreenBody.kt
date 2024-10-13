package com.compose_app.ui.screen.cateogries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compose_app.core.utils.Results
import com.compose_app.data.models.Category
import com.compose_app.ui.component.BackArrowIcon
import com.compose_app.ui.theme.background

@Composable
fun CategoriesScreenBody(categoriesState: Results.Success<List<Category>>) {
    Scaffold (
        backgroundColor = background,
        modifier = Modifier.fillMaxSize(),
    ) {padding->
        println("padding $padding")
        Column(modifier = Modifier.padding(padding)) {
            BackArrowIcon()
            LazyColumn {
                items(categoriesState.data.size) { index -> // Display data
                    val category = categoriesState.data[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                        elevation = 12.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(text = category.title)
                                }
                            }
                            Icon(imageVector = Icons.Default.Add, contentDescription = "add")

                        }
                    }
                }
            }

        }
    }
}




