package com.compose_app.ui.presentaion.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Header screen widget
@Composable
fun HeaderScreenWidget(
    modifier: Modifier = Modifier,
    screenTitle: String,
    screenSubTitle: String,
    cost: String? = null,
) {
    //Prepare cost value
    val value = if (cost == null) "-" else "$$cost"
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        //Screen title
        Text(
            text = screenTitle,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 20.sp
            )
        )
        //Screen subtitle
        Text(
            text = screenSubTitle,
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            ), modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 8.dp)
        )
        //Event cost value
        Text(
            text = value,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(top = 48.dp)
        )
    }
}
