package com.updavid.liveoci_hilt.features.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Home
import com.updavid.liveoci_hilt.features.presentation.presentation.page.HomePage
import javax.inject.Inject

class PresentationNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Home>{
            HomePage(
                onActivities = {},
                onProfile = {},
                onHome = {},
                onAnalysis = {},
                onGenerateActivity = {}
            )
        }
    }
}