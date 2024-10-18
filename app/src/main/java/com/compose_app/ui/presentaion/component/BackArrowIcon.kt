package com.compose_app.ui.presentaion.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.compose_app.R

@Composable
fun BackArrowIcon(
				modifier: Modifier = Modifier,
				onBackClick: () -> Unit,
) {
				IconButton(
								onClick = onBackClick,
								modifier = modifier
				) {
								Icon(
												imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
												contentDescription = stringResource(R.string.back)
								)
				}
}