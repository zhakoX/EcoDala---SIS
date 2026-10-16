package kz.ecodala.screens.home

import kz.ecodala.model.Achievement
import kz.ecodala.model.HomeStats
import kz.ecodala.model.User

/** The explicit status is the single source of truth for which Home UI state to render. */
enum class HomeStatus {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR
}

data class HomeUiState(
    val status: HomeStatus = HomeStatus.LOADING,
    val user: User? = null,
    val stats: HomeStats? = null,
    val recentAchievements: List<Achievement> = emptyList(),
    val errorMessage: String? = null
)
