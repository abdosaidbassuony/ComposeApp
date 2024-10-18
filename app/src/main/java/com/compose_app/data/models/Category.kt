package com.compose_app.data.models

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
				@SerializedName("id")
				val id: Int,
				@SerializedName("title")
				val title: String,
				@SerializedName("image")
				val image: String,
				@SerializedName("minBudget")
				val minBudget: Double,
				@SerializedName("maxBudget")
				val maxBudget: Double,
				@SerializedName("avgBudget")
				val avgBudget: Double,
)
