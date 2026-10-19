package com.ecodala.core.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ecodala.core.ui.components.EcoDalaBottomBar
import com.ecodala.feature.achievements.presentation.AchievementsRoute
import com.ecodala.feature.challenges.presentation.ChallengesRoute
import com.ecodala.feature.home.presentation.HomeRoute
import com.ecodala.feature.leaderboard.presentation.LeaderboardRoute
import com.ecodala.feature.map.presentation.MapRoute
import com.ecodala.feature.map.presentation.RecyclingPointDetailsRoute
import com.ecodala.feature.profile.presentation.ProfileRoute
import com.ecodala.feature.profile.presentation.RecyclingHistoryRoute
import com.ecodala.feature.profile.presentation.SettingsRoute
import com.ecodala.feature.profile.presentation.SupportRoute
import com.ecodala.feature.splash.presentation.SplashScreen
import com.ecodala.feature.submit.presentation.SubmitWasteRoute
import com.ecodala.feature.tree.presentation.VirtualTreeRoute

@Composable
fun EcoDalaApp(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showBottomBar = currentDestination.shouldShowBottomBar()

    Scaffold(
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (showBottomBar) {
                EcoDalaBottomBar(
                    currentRoute = currentDestination?.route,
                    onDestinationClick = { route ->
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
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = EcoDalaRoute.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(EcoDalaRoute.Splash.route) {
                SplashScreen(onFinished = {
                    navController.navigate(EcoDalaRoute.Home.route) {
                        popUpTo(EcoDalaRoute.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(EcoDalaRoute.Home.route) {
                HomeRoute(
                    onMapClick = { navController.navigate(EcoDalaRoute.Map.route) },
                    onSubmitClick = { navController.navigate(EcoDalaRoute.SubmitWaste.route) },
                    onChallengesClick = { navController.navigate(EcoDalaRoute.Challenges.route) },
                    onLeaderboardClick = { navController.navigate(EcoDalaRoute.Leaderboard.route) },
                    onTreeClick = { navController.navigate(EcoDalaRoute.VirtualTree.route) },
                    onAchievementsClick = { navController.navigate(EcoDalaRoute.Achievements.route) }
                )
            }
            composable(EcoDalaRoute.Map.route) {
                MapRoute(onPointDetailsClick = { pointId ->
                    navController.navigate(EcoDalaRoute.RecyclingPointDetails.createRoute(pointId))
                })
            }

            composable(EcoDalaRoute.RecyclingPointDetails.route, arguments = listOf(
                androidx.navigation.navArgument("pointId") { type = androidx.navigation.NavType.StringType }
            )) {
                RecyclingPointDetailsRoute(
                    onBackClick = { navController.popBackStack() },
                )
            }
            composable(EcoDalaRoute.SubmitWaste.route) {
                SubmitWasteRoute(
                    onBackClick = { navController.popBackStack() },
                    onSubmitClick = { navController.navigate(EcoDalaRoute.VirtualTree.route) }
                )
            }
            composable(EcoDalaRoute.VirtualTree.route) {
                VirtualTreeRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.Challenges.route) {
                ChallengesRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.Leaderboard.route) {
                LeaderboardRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.Profile.route) {
                ProfileRoute(
                    onBackClick = { navController.popBackStack() },
                    onAchievementsClick = { navController.navigate(EcoDalaRoute.Achievements.route) },
                    onRecyclingHistoryClick = { navController.navigate(EcoDalaRoute.RecyclingHistory.route) },
                    onSettingsClick = { navController.navigate(EcoDalaRoute.Settings.route) },
                    onSupportClick = { navController.navigate(EcoDalaRoute.Support.route) }
                )
            }
            composable(EcoDalaRoute.Achievements.route) {
                AchievementsRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.RecyclingHistory.route) {
                RecyclingHistoryRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.Settings.route) {
                SettingsRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
            composable(EcoDalaRoute.Support.route) {
                SupportRoute(
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

private fun NavDestination?.shouldShowBottomBar(): Boolean {
    val currentRoute = this?.route ?: return false
    return bottomNavDestinations.any { it.route == currentRoute }
}
