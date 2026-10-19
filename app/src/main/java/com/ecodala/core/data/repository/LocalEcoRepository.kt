package com.ecodala.core.data.repository

import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.data.local.EcoLocalCache
import com.ecodala.core.domain.model.*
import com.ecodala.core.domain.repository.PointsEconomyRepository
import com.ecodala.core.domain.usecase.GetAchievementProgressUseCase
import com.ecodala.core.data.local.LocalProfileStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.UUID

/** One local demo account. All screens share this ledger and history. */
data class LocalEcoData(
    val submissions: List<WasteSubmission> = DummyEcoData.wasteSubmissions,
    val events: List<PointsEvent> = DummyEcoData.pointsLedger,
    val favouriteIds: Set<String> = emptySet()
) {
    val ecoPoints: Int get() = events.sumOf { it.points }
}

class LocalEcoRepository(private val loadingDelayMillis: Long = 600L) : PointsEconomyRepository {
    companion object {
        val shared = LocalEcoRepository()
    }

    private val points = DummyEcoData.recyclingPoints.toList()
    private val _data = MutableStateFlow(LocalEcoData())
    val data = _data.asStateFlow()
    private val mutex = Mutex()

    suspend fun restore() = mutex.withLock {
        EcoLocalCache.load()?.let { _data.value = it }
    }

    suspend fun recyclingPoints(simulateError: Boolean = false): Result<List<RecyclingPoint>> {
        delay(loadingDelayMillis)
        return if (simulateError) Result.failure(IllegalStateException("Demo loading error"))
        else Result.success(points.toList())
    }

    suspend fun recyclingPoint(id: String): Result<RecyclingPoint> {
        delay(loadingDelayMillis)
        return points.firstOrNull { it.id == id }?.let { Result.success(it) }
            ?: Result.failure(IllegalArgumentException("Recycling point not found: $id"))
    }

    suspend fun setFavourite(id: String) = mutex.withLock {
        if (points.none { it.id == id }) return@withLock
        val previous = _data.value
        val ids = previous.favouriteIds
        val next = previous.copy(favouriteIds = if (id in ids) ids - id else ids + id)
        EcoLocalCache.save(next)
        _data.value = next
    }

    suspend fun currentUser(): Result<EcoUser> {
        val profile = LocalProfileStore.profile.value
        val total = data.value.ecoPoints
        val entries = leaderboard().getOrThrow()
        return Result.success(DummyEcoData.currentUser.copy(
            fullName = profile.fullName ?: DummyEcoData.currentUser.fullName,
            email = profile.email ?: DummyEcoData.currentUser.email,
            ecoPoints = total,
            level = EcoRatingCalculator.calculate(total).level,
            globalRank = entries.first { it.isCurrentUser }.rank
        ))
    }

    suspend fun submitWaste(type: WasteType, quantity: Double, unit: String, comment: String?): Result<WasteSubmission> {
        delay(loadingDelayMillis)
        if (!quantity.isFinite() || quantity <= 0 || quantity > 10000 || unit !in listOf("kg", "g", "pcs", "bag")) {
            return Result.failure(IllegalArgumentException("Enter a quantity from 1 to 10000 and a valid unit"))
        }
        return mutex.withLock {
            val now = LocalDateTime.now()
            val id = UUID.randomUUID().toString()
            val reward = EcoRatingCalculator.pointsForWaste(type, quantity, unit)
            val submission = WasteSubmission(id, DummyEcoData.currentUser.id, type, quantity, unit,
                null, comment?.trim()?.takeIf { it.isNotEmpty() }, reward, now.toString())
            val event = PointsEvent(id, submission.userId, PointsSource.WasteSubmission,
                "${type.title} recycled", reward, YearMonth.from(now).toString(), now.toString())
            val next = _data.value.copy(
                submissions = listOf(submission) + _data.value.submissions,
                events = _data.value.events + event
            )
            return try {
                EcoLocalCache.save(next)
                _data.value = next
                Result.success(submission)
            } catch (error: java.io.IOException) {
                Result.failure(error)
            }
        }
    }

    suspend fun submissions() = Result.success(data.value.submissions)

    suspend fun challenges(): Result<List<Challenge>> {
        val added = (data.value.submissions.size - DummyEcoData.wasteSubmissions.size).coerceAtLeast(0)
        return Result.success(DummyEcoData.challenges.map { challenge ->
            if (challenge.status != ChallengeStatus.Active) challenge else {
                val progress = (challenge.progress + added).coerceAtMost(challenge.target)
                challenge.copy(progress = progress, description = "$progress / ${challenge.target}",
                    status = if (progress >= challenge.target) ChallengeStatus.Completed else ChallengeStatus.Active)
            }
        })
    }

    suspend fun achievements(): Result<List<Achievement>> = GetAchievementProgressUseCase(this)(DummyEcoData.currentUser.id)

    suspend fun leaderboard(): Result<List<LeaderboardEntry>> = Result.success(
        DummyEcoData.leaderboard.map { entry ->
            if (entry.isCurrentUser) entry.copy(points = data.value.ecoPoints,
                name = LocalProfileStore.profile.value.fullName ?: DummyEcoData.currentUser.fullName) else entry
        }.sortedByDescending { it.points }.mapIndexed { index, entry -> entry.copy(rank = index + 1) }
    )

    override suspend fun getPointEvents(userId: String) = Result.success(data.value.events.filter { it.userId == userId })
    override suspend fun getWasteSubmissions(userId: String) = Result.success(data.value.submissions.filter { it.userId == userId })
}
