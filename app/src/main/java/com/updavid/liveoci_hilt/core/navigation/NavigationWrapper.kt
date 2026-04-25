package com.updavid.liveoci_hilt.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.updavid.liveoci_hilt.core.ui.atoms.BottomNavigationBar

@Composable
fun NavigationWrapper(
    navGraphs: Set<FeatureNavGraph>
){
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.let { dest ->
        dest.hasRoute(Home::class) ||
                dest.hasRoute(BoredActivity::class) ||
                //dest.hasRoute(Code::class) ||
                dest.hasRoute(Analyzer::class) ||
                dest.hasRoute(Profile::class)
    } ?: false

    val selectedIndex = when {
        currentDestination?.hasRoute(Home::class) == true -> 0
        currentDestination?.hasRoute(BoredActivity::class) == true -> 1
        //currentDestination?.hasRoute(Code::class) == true -> 2
        currentDestination?.hasRoute(Analyzer::class) == true -> 3
        currentDestination?.hasRoute(Profile::class) == true -> 4
        else -> 0
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Splash
        ){
            navGraphs.forEach { featureNavGraph ->
                featureNavGraph.registerGraph(this, navController)
            }
        }

        AnimatedVisibility(
            visible = showBottomBar,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                appBackgroundColor = Color.Transparent,
                onItemSelected = { newIndex ->
                    // Respetamos tu lógica del botón central
                    if (newIndex == 2) {
                        // Navegar a la acción del botón "+"
                        //navController.navigate(Code)
                        return@BottomNavigationBar
                    }

                    if (newIndex == selectedIndex) return@BottomNavigationBar

                    val route: Any = when (newIndex) {
                        0 -> Home
                        1 -> BoredActivity
                        3 -> Analyzer
                        4 -> Profile
                        else -> Home
                    }

                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}