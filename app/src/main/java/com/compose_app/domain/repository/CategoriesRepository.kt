package com.compose_app.domain.repository

import com.compose_app.data.models.Category

interface CategoriesRepository {
    suspend fun getCategories(): List<Category>

}