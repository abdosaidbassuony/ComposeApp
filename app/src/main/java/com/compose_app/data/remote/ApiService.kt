package com.compose_app.data.remote

import com.compose_app.core.utils.Results
import com.compose_app.data.models.Category
import retrofit2.http.GET

interface ApiService {
    @GET("/categories.json")
    suspend fun getYourData(): List<Category>
}