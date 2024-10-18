package com.compose_app.ui.presentaion.cateogries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.compose_app.core.utils.Results
import com.compose_app.core.utils.formatPrice
import com.compose_app.domain.entities.Category
import com.compose_app.ui.presentaion.component.CategoriesGridListView
import com.compose_app.ui.presentaion.component.HeaderScreenWidget
import com.compose_app.ui.presentaion.component.PrimaryButton
import com.compose_app.ui.theme.background
import com.example.compose_app.R

//Categories screen content composable
@Composable
fun Content(
    modifier: Modifier = Modifier,
    categoriesState: Results.Success<List<Category>>,
    navController: NavHostController,
) {
    Scaffold(
        backgroundColor = background, modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
        ) {
            Column {
                //Header screen widget
                HeaderScreenWidget(
                    screenTitle = stringResource(R.string.event_booking),
                    screenSubTitle = stringResource(R.string.add_event_to_view_cost_estimate),
                    cost = formatPrice(1000.0),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp)
                )
                //Categories grid list view
                CategoriesGridListView(
                    categoriesState = categoriesState,
                    homeScreen = true,
                    onCartClick = {
                        //Navigate to category details screen
                        navController.navigate("category/${it.id}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 48.dp)
                )
            }
            //Save Primary button
            PrimaryButton(
                onClick = {
                    //TODO save selected categories with total cost
                },
                title = stringResource(R.string.save),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}







