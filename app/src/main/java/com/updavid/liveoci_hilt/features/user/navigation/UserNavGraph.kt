package com.updavid.liveoci_hilt.features.user.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.Code
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Login
import com.updavid.liveoci_hilt.core.navigation.Profile
import com.updavid.liveoci_hilt.core.navigation.ProfileEdit
import com.updavid.liveoci_hilt.core.navigation.Schedules
import com.updavid.liveoci_hilt.core.navigation.Tastes
import com.updavid.liveoci_hilt.features.user.presentation.page.ProfileEditPage
import com.updavid.liveoci_hilt.features.user.presentation.page.ProfilePage
import com.updavid.liveoci_hilt.features.user.presentation.page.TastesPage
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.ProfileEditViewModel
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.ProfileViewModel
import com.updavid.liveoci_hilt.features.user.presentation.viewmodel.TastesViewModel
import javax.inject.Inject

class UserNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Profile>{
            val viewModel: ProfileViewModel = hiltViewModel()

            ProfilePage(
                viewModel = viewModel,
                onNavigateToEditInterests = {
                    navController.navigate(ProfileEdit)
                },
                onLogoutSuccess = {
                    navController.navigate(Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateTastes = {
                    navController.navigate(Tastes)
                },
                onNavigateSchedules = {
                    navController.navigate(Schedules)
                },
                onNavigateToCodeFriend = {
                    navController.navigate(Code)
                },
                onBack = {
                    navController.navigateUp()
                }
            )
        }

        navGraphBuilder.composable<ProfileEdit> {
            val viewModel: ProfileEditViewModel = hiltViewModel()

            ProfileEditPage(
                viewModel = viewModel,
                onBack = { navController.navigateUp() },
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        navGraphBuilder.composable<Tastes> {
            val viewModel: TastesViewModel = hiltViewModel()

            TastesPage(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}