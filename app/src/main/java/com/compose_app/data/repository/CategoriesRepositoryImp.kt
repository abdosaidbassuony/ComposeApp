package com.compose_app.data.repository

import com.compose_app.data.remote.ApiService
import com.compose_app.domain.entities.Category
import com.compose_app.domain.repository.CategoriesRepository
import javax.inject.Inject

class CategoriesRepositoryImp @Inject constructor(
				private val apiService: ApiService,
) : CategoriesRepository {
				override suspend fun getCategories(): List<Category> {
								return apiService.getCategories().map {
												Category(
																id = it.id,
																title = it.title,
																image = it.image,
																minBudget = it.minBudget,
																maxBudget = it.maxBudget,
																avgBudget = it.avgBudget,
												)
								}
				}
				
				override suspend fun getCategoryDetails(categoryId: String): List<Category> {
								return apiService.getCategoryDetails(categoryId).map {
												Category(
																id = it.id,
																title = it.title,
																image = it.image,
																minBudget = it.minBudget,
																maxBudget = it.maxBudget,
																avgBudget = it.avgBudget,
												)
								}
				}
}