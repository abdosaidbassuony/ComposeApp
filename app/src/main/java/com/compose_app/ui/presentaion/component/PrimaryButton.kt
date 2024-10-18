package com.compose_app.ui.presentaion.component

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PrimaryButton(onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
				Button(
								onClick = onClick,
								modifier = modifier
				) {
								Text(text = title)
				}
}