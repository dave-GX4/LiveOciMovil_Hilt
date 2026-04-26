package com.updavid.liveoci_hilt.features.friends.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Friends
import com.updavid.liveoci_hilt.core.navigation.Requests
import com.updavid.liveoci_hilt.features.friends.presentation.page.FriendsPage
import com.updavid.liveoci_hilt.features.friends.presentation.page.RequestsPage
import com.updavid.liveoci_hilt.features.friends.presentation.viewmodels.FriendsViewModel
import com.updavid.liveoci_hilt.features.friends.presentation.viewmodels.RequestViewModel
import javax.inject.Inject

class FriendNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Friends>{
            val viewModel: FriendsViewModel = hiltViewModel()

            FriendsPage(
                viewModel = viewModel,
                onNavigateToRequests = {
                    navController.navigate(Requests)
                },
                onBack = {
                    navController.navigateUp()
                },
            )
        }

        navGraphBuilder.composable<Requests> {
            val viewModel: RequestViewModel = hiltViewModel()

            RequestsPage(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}