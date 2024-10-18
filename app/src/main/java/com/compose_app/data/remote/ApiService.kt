package com.compose_app.data.remote

import com.compose_app.data.models.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/categories.json")
    suspend fun getCategories(): List<CategoryResponse>
    @GET("/categories/{categoryId}.json")
    suspend fun getCategoryDetails(@Path("categoryId") categoryId: String): List<CategoryResponse>
}
