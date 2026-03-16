package com.updavid.liveoci_hilt.features.auth.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Home
import com.updavid.liveoci_hilt.core.navigation.Login
import com.updavid.liveoci_hilt.core.navigation.Register
import com.updavid.liveoci_hilt.features.auth.presentation.pages.LoginPage
import com.updavid.liveoci_hilt.features.auth.presentation.pages.RegisterPage
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.LoginViewModel
import com.updavid.liveoci_hilt.features.auth.presentation.viewmodels.RegisterViewModel
import javax.inject.Inject

class AuthNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Login> {
            val viewModel: LoginViewModel = hiltViewModel()

            LoginPage(
                viewModel = viewModel,
                onNavigateToRegister = { navController.navigate(Register) },
                onLoginSuccess = { navController.navigate(Home) }
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