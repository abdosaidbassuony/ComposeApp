package com.compose_app.ui.screen.cateogries

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose_app.core.utils.Results


@Composable
fun CategoriesScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<CategoriesViewModel>()
    when (val categoriesState = viewModel.categories.value) {
        is Results.Loading -> {
            CircularProgressIndicator()
        }

        is Results.Success -> {
            CategoriesScreenBody(categoriesState)
        }

        is Results.Failure -> {
            Text(text = "Error: ${categoriesState.exception.message}") // Display error
        }
    }

}

