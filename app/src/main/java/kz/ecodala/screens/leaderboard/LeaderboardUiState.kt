package kz.ecodala.screens.leaderboard

import kz.ecodala.model.User

/** The explicit status is the single source of truth for which leaderboard UI state to render. */
enum class LeaderboardStatus {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR
}

data class LeaderboardUiState(
    val status: LeaderboardStatus = LeaderboardStatus.LOADING,
    val users: List<User> = emptyList(),
    val errorMessage: String? = null
)
