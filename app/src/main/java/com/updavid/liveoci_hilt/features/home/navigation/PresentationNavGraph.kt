package com.updavid.liveoci_hilt.features.home.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Home
import com.updavid.liveoci_hilt.features.home.presentation.page.HomePage
import com.updavid.liveoci_hilt.features.home.presentation.viewmodel.HomeViewModel
import javax.inject.Inject

class PresentationNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Home>{
            val viewmodel: HomeViewModel = hiltViewModel()
            HomePage(viewModel = viewmodel)
        }
    }
}