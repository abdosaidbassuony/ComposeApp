package com.compose_app.domain.repository

import com.compose_app.domain.entities.Category

interface CategoriesRepository {
				suspend fun getCategories(): List<Category>
				suspend fun getCategoryDetails(categoryId: String): List<Category>
}