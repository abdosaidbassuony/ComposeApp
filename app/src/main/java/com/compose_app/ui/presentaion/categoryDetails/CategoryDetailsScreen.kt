package com.compose_app.ui.presentaion.categoryDetails

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.compose_app.core.utils.Results
import com.compose_app.ui.presentaion.component.LoadingCircularProgressIndicator
import com.example.compose_app.R

//Category details screen composable
@Composable
fun CategoryDetailsScreen(navController: NavHostController) {
    val viewModel = hiltViewModel<CategoryDetailsViewModel>()
    
    when (val categoriesState = viewModel.category.value) {
        is Results.Loading -> {
            //Loading indicator
            LoadingCircularProgressIndicator()
        }
        is Results.Success -> {
            Content(
                categoriesState = categoriesState,
                navController = navController,
                modifier = Modifier.fillMaxSize()
            )
        }
        is Results.Failure -> {
            Text(text = "${stringResource(R.string.error)} ${categoriesState.exception.message}") //Display error
        }
    }
}
