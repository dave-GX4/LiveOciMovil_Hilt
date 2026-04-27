package com.updavid.liveoci_hilt.features.activity.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.Activities
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.FormActivity
import com.updavid.liveoci_hilt.features.activity.presentation.page.ActivitiesPage
import com.updavid.liveoci_hilt.features.activity.presentation.page.FormActivityPage
import com.updavid.liveoci_hilt.features.activity.presentation.viewmodel.ActivitiesViewModel
import com.updavid.liveoci_hilt.features.activity.presentation.viewmodel.ActivityFormViewModel
import javax.inject.Inject

class ActivityNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Activities>{
            val viewModel: ActivitiesViewModel = hiltViewModel()

            ActivitiesPage(
                viewModel = viewModel,
                onNavigateToForm = {
                    navController.navigate(FormActivity)
                }
            )
        }

        navGraphBuilder.composable<FormActivity>{
            val viewModel: ActivityFormViewModel = hiltViewModel()

            FormActivityPage(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}