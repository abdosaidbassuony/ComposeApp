package com.compose_app.ui.presentaion.component.card_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter

@Composable
fun CardImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(
            model = imageUrl,
        ),
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f),
        contentScale = ContentScale.Crop,
    )
}