package com.compose_app.domain.usecase

import com.compose_app.core.utils.Results
import com.compose_app.domain.entities.Category
import com.compose_app.domain.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
				private val repository: CategoriesRepository,
) {
				suspend operator fun invoke(): Results<List<Category>> {
								return try {
												val categories = repository.getCategories()
												Results.Success(categories)
								} catch (e: Exception) {
												Results.Failure(e)
								}
				}
}