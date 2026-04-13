package com.updavid.liveoci_hilt.features.analyzer.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.Analyzer
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.features.analyzer.presentation.pages.AnalyzerPage
import com.updavid.liveoci_hilt.features.analyzer.presentation.viewmodels.AnalyzerViewModel
import javax.inject.Inject

class AnalyzerNavGraph @Inject constructor(): FeatureNavGraph {
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Analyzer> {
            val viewmodel: AnalyzerViewModel = hiltViewModel()

            AnalyzerPage(
                viewModel = viewmodel
            )
        }
    }
}