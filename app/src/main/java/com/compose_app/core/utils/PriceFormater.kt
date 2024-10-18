package com.compose_app.core.utils

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(price: Double): String {
				val formatter = NumberFormat.getNumberInstance(Locale.US)
				return formatter.format(price)
}