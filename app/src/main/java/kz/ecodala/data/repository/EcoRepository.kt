package kz.ecodala.data.repository

import kotlinx.coroutines.delay
import kz.ecodala.data.achievements
import kz.ecodala.data.currentUser
import kz.ecodala.data.homeStats
import kz.ecodala.data.recyclingPoints
import kz.ecodala.data.users
import kz.ecodala.model.Achievement
import kz.ecodala.model.HomeStats
import kz.ecodala.model.RecyclingPoint
import kz.ecodala.model.User

/**
 * In-memory data access layer for the SIS4 MVVM refactor.
 *
 * [shouldFail] is intentionally deterministic so the future UI can demonstrate
 * and test its error state without relying on random failures.
 */
class EcoRepository(
    var shouldFail: Boolean = false
) {
    private val favoritePointIds = mutableSetOf<Int>()

    suspend fun loadHomeData(): HomeData {
        simulateLoad()
        return HomeData(
            user = currentUser,
            stats = homeStats,
            recentAchievements = achievements.take(3)
        )
    }

    suspend fun loadLeaderboard(): List<User> {
        simulateLoad()
        return users
    }

    /** Returns null when [pointId] does not identify an existing recycling point. */
    suspend fun loadRecyclingPoint(pointId: Int): RecyclingPoint? {
        simulateLoad()
        return recyclingPoints.find { it.id == pointId }
    }

    fun isFavorite(pointId: Int): Boolean = pointId in favoritePointIds

    /**
     * Toggles an existing point and returns its new favorite value.
     * Returns null when [pointId] is unknown so callers can represent an empty state.
     */
    fun toggleFavorite(pointId: Int): Boolean? {
        if (recyclingPoints.none { it.id == pointId }) return null

        return if (favoritePointIds.add(pointId)) {
            true
        } else {
            favoritePointIds.remove(pointId)
            false
        }
    }

    private suspend fun simulateLoad() {
        delay(500)
        if (shouldFail) throw EcoRepositoryException()
    }
}

data class HomeData(
    val user: User,
    val stats: HomeStats,
    val recentAchievements: List<Achievement>
)

class EcoRepositoryException : IllegalStateException("Simulated repository failure")
