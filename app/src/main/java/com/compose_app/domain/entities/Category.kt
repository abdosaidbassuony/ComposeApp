package com.compose_app.domain.entities

data class Category(
				val id: Int,
				val title: String,
				val image: String,
				val minBudget: Double,
				val maxBudget: Double,
				val avgBudget: Double,
)
