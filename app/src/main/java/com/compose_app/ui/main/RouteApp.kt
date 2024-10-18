package com.compose_app.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.compose_app.ui.presentaion.categoryDetails.CategoryDetailsScreen
import com.compose_app.ui.presentaion.cateogries.CategoriesScreen

/// Update version
@Composable
fun RouteApp() {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "category") {
        composable("category") { CategoriesScreen(navController) }
        composable(
            "category/{categoryId}",
            arguments = listOf(navArgument("categoryId") { type = NavType.StringType })
        ) {
            CategoryDetailsScreen(navController)
        }
    }
}

