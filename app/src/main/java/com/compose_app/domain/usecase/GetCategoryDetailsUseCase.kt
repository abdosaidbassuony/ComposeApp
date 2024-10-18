package com.compose_app.domain.usecase

import com.compose_app.core.utils.Results
import com.compose_app.domain.entities.Category
import com.compose_app.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryDetailsUseCase @Inject constructor(
    private val repository: CategoriesRepository,
) {
    suspend operator fun invoke(categoryId: String): Results<List<Category>> {

        return try {
            val categories = repository.getCategoryDetails(categoryId = categoryId)
            Results.Success(categories)
        } catch (e: Exception) {
            Results.Failure(e)
        }
    }
}