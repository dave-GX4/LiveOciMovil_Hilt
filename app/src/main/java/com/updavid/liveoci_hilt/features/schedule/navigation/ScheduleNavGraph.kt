package com.updavid.liveoci_hilt.features.schedule.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.FormSchedule
import com.updavid.liveoci_hilt.core.navigation.Schedules
import com.updavid.liveoci_hilt.features.schedule.presentation.pages.FormSchedulePage
import com.updavid.liveoci_hilt.features.schedule.presentation.pages.SchedulesPage
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.FormScheduleViewModel
import com.updavid.liveoci_hilt.features.schedule.presentation.viewmodels.SchedulesViewModel
import javax.inject.Inject

class ScheduleNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<FormSchedule>{
            val viewModel: FormScheduleViewModel = hiltViewModel()

            FormSchedulePage(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                },
                onSuccessSaved = {
                    navController.navigate(Schedules) {
                        popUpTo<FormSchedule> { inclusive = true }
                    }
                }
            )
        }

        navGraphBuilder.composable<Schedules> {
            val viewModel: SchedulesViewModel = hiltViewModel()

            SchedulesPage(
                viewModel = viewModel,
                onFormAdd = {
                    navController.navigate(FormSchedule)
                },
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}