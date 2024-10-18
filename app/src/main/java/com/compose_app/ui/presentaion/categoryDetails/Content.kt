package com.compose_app.ui.presentaion.categoryDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.compose_app.core.utils.Results
import com.compose_app.core.utils.formatPrice
import com.compose_app.domain.entities.Category
import com.compose_app.ui.presentaion.component.BackArrowIcon
import com.compose_app.ui.presentaion.component.CategoriesGridListView
import com.compose_app.ui.presentaion.component.HeaderScreenWidget
import com.compose_app.ui.theme.background
import com.example.compose_app.R

@Composable
fun Content(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    categoriesState: Results.Success<List<Category>>,
) {
    Surface(
        color = background,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            //	Back arrow icon
            BackArrowIcon(
                onBackClick = { navController.popBackStack() },
                modifier = Modifier.padding(16.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                //	Header screen widget
                HeaderScreenWidget(
                    screenTitle = stringResource(R.string.event_booking),
                    screenSubTitle = stringResource(R.string.add_event_to_view_cost_estimate),
                    cost = formatPrice(1000.0),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                //	Categories grid list view
                CategoriesGridListView(
                    categoriesState = categoriesState,
                    onAddItemClicked = { },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 48.dp),
                )
            }
        }
    }
}
