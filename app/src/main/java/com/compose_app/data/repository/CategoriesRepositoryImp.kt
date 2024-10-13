package com.compose_app.data.repository

import com.compose_app.core.utils.Results
import com.compose_app.data.models.Category
import com.compose_app.data.remote.ApiService
import com.compose_app.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImp @Inject constructor(
    private val apiService: ApiService
) : CategoriesRepository {
    override suspend fun getCategories(): List<Category> {
        return apiService.getYourData()


    }
}