package com.updavid.liveoci_hilt.features.user.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Profile
import com.updavid.liveoci_hilt.core.navigation.Tastes
import javax.inject.Inject

class UserNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Profile>{

        }

        navGraphBuilder.composable<Tastes>{

        }
    }
}