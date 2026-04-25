package com.updavid.liveoci_hilt.features.code.navigation

import com.updavid.liveoci_hilt.features.code.presentation.viewmodels.CodeViewModel
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.updavid.liveoci_hilt.core.navigation.FeatureNavGraph
import com.updavid.liveoci_hilt.core.navigation.Code
import com.updavid.liveoci_hilt.features.code.presentation.pages.CodePage
import javax.inject.Inject

class CodeNavGraph @Inject constructor() : FeatureNavGraph {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController
    ) {
        navGraphBuilder.composable<Code> {
            val viewModel: CodeViewModel = hiltViewModel()

            CodePage(
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}