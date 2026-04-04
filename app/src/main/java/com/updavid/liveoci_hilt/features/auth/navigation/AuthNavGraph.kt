package com.updavid.liveoci_hilt.features.auth.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Home
import com.updavid.liveoci_hilt.core.navigation.Login
import com.updavid.liveoci_hilt.core.navigation.Register
import com.updavid.liveoci_hilt.core.navigation.Splash
import com.updavid.liveoci_hilt.features.auth.presentation.pages.LoginPage
import com.updavid.liveoci_hilt.features.auth.presentation.pages.RegisterPage
import com.updavid.liveoci_hilt.features.auth.presentation.pages.SplashLoadingPage
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.LoginViewModel
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.RegisterViewModel
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.SessionViewModel
import javax.inject.Inject

class AuthNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Splash> {
            val sessionViewModel: SessionViewModel = hiltViewModel()
            val startDestination by sessionViewModel.startDestination.collectAsStateWithLifecycle()

            LaunchedEffect(startDestination) {
                startDestination?.let { destination ->
                    navController.navigate(destination) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            }

            SplashLoadingPage()
        }

        navGraphBuilder.composable<Login> {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginPage(
                viewModel = viewModel,
                onNavigateToRegister = { navController.navigate(Register) },
                onLoginSuccess = {
                    navController.navigate(Home) {
                        popUpTo<Login> { inclusive = true }
                    }
                }
            )
        }

        navGraphBuilder.composable<Register> {
            val viewModel: RegisterViewModel = hiltViewModel()

            RegisterPage(
                viewModel = viewModel,
                onBack = {
                    navController.navigateUp()
                },
                onRegisterSuccess = {
                    navController.navigate(Login) {
                        popUpTo<Register> { inclusive = true }
                    }
                }
            )
        }
    }
}