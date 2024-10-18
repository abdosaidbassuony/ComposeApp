package com.compose_app.ui.presentaion.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingCircularProgressIndicator() {
				Column(
								verticalArrangement = Arrangement.Center,
								horizontalAlignment = Alignment.CenterHorizontally,
								modifier = Modifier.fillMaxSize(),
				) {
								CircularProgressIndicator()
				}
}