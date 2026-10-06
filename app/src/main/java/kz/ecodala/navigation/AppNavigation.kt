package kz.ecodala.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kz.ecodala.screens.HomeScreen
import kz.ecodala.screens.LeaderboardScreen
import kz.ecodala.screens.RecyclingPointScreen

object Routes {

    const val HOME = "home"

    const val LEADERBOARD = "leaderboard"

    const val RECYCLING_POINT = "recycling_point/{id}"
}

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {

        // ------------------------------------------------
        // 1. HOME
        // ------------------------------------------------

        composable(
            route = Routes.HOME
        ) {
            HomeScreen(
                onLeaderboardClick = {
                    navController.navigate(Routes.LEADERBOARD)
                },

                onRecyclingPointClick = {
                    navController.navigate("recycling_point/1")
                }
            )
        }

        // ------------------------------------------------
        // 2. LEADERBOARD
        // ------------------------------------------------

        composable(
            route = Routes.LEADERBOARD
        ) {
            LeaderboardScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // ------------------------------------------------
        // 3. RECYCLING POINT DETAIL
        // ------------------------------------------------

        composable(
            route = Routes.RECYCLING_POINT,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {

            RecyclingPointScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}