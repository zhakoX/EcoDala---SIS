package com.ecodala.core.navigation

sealed class EcoDalaRoute(val route: String) {
    data object Splash : EcoDalaRoute("splash")

    data object Home : EcoDalaRoute("home")
    data object Map : EcoDalaRoute("map")
    data object RecyclingPointDetails : EcoDalaRoute("recycling-point/{pointId}") {
        fun createRoute(pointId: String) = "recycling-point/$pointId"
    }
    data object SubmitWaste : EcoDalaRoute("submit-waste")
    data object VirtualTree : EcoDalaRoute("virtual-tree")
    data object Challenges : EcoDalaRoute("challenges")
    data object Leaderboard : EcoDalaRoute("leaderboard")
    data object Profile : EcoDalaRoute("profile")
    data object Achievements : EcoDalaRoute("achievements")
    data object RecyclingHistory : EcoDalaRoute("recycling-history")
    data object Settings : EcoDalaRoute("settings")
    data object Support : EcoDalaRoute("support")
}
