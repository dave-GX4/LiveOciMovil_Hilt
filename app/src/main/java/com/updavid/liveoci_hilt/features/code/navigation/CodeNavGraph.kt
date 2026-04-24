package com.updavid.liveoci_hilt.features.code.navigation

import ProfileViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Profile
import com.updavid.liveoci_hilt.features.code.presentation.pages.ProfilePage
import javax.inject.Inject


class ProfileNavGraph @Inject constructor() : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Profile> {
            val viewModel: ProfileViewModel = hiltViewModel()

            ProfilePage(
                viewModel = viewModel
            )
        }
    }
}