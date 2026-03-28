package com.updavid.liveoci_hilt.features.bored.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.BoredActivity
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.features.bored.presentation.page.BoredActivityPage
import com.updavid.liveoci_hilt.features.bored.presentation.viewmodel.BoredActivityViewModel
import javax.inject.Inject

class BoredActivitiesNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<BoredActivity> {
            val viewmodel: BoredActivityViewModel = hiltViewModel()

            BoredActivityPage(
                viewModel = viewmodel,
                onBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}