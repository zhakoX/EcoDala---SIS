package com.ecodala.core.data.repository

import com.ecodala.core.domain.model.EcoRatingCalculator
import com.ecodala.core.domain.model.WasteType
import com.ecodala.core.domain.usecase.GetPointsWalletUseCase
import com.ecodala.core.domain.usecase.GetMonthlyImpactUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class LocalEcoRepositoryTest {
    @Test fun submissionUpdatesLedgerHistoryLeaderboardAndProgressTogether() = runTest {
        val repository = LocalEcoRepository(0)
        val before = repository.data.value
        val challengeBefore = repository.challenges().getOrThrow().first().progress
        val reward = EcoRatingCalculator.pointsForWaste(WasteType.Plastic, 5.0, "kg")
        val result = repository.submitWaste(WasteType.Plastic, 5.0, "kg", "Campus cleanup").getOrThrow()
        val user = repository.currentUser().getOrThrow()
        assertEquals(before.ecoPoints + reward, user.ecoPoints)
        assertEquals(result, repository.submissions().getOrThrow().first())
        assertEquals(before.submissions.size + 1, repository.data.value.submissions.size)
        assertEquals(user.ecoPoints, repository.leaderboard().getOrThrow().first { it.isCurrentUser }.points)
        assertEquals(user.ecoPoints, GetPointsWalletUseCase(repository)("user-1").getOrThrow().totalPoints)
        assertEquals(user.level, EcoRatingCalculator.calculate(user.ecoPoints).level)
        assertTrue(repository.challenges().getOrThrow().first().progress > challengeBefore)
        assertTrue(repository.achievements().getOrThrow().first { it.title == "Plastic Saver" }.isUnlocked)
    }

    @Test fun invalidSubmissionCannotAwardPoints() = runTest {
        val repository = LocalEcoRepository(0)
        val before = repository.data.value
        assertTrue(repository.submitWaste(WasteType.Paper, -1.0, "kg", null).isFailure)
        assertTrue(repository.submitWaste(WasteType.Paper, Double.NaN, "kg", null).isFailure)
        assertTrue(repository.submitWaste(WasteType.Paper, 1.0, "unknown", null).isFailure)
        assertEquals(before, repository.data.value)
    }

    @Test fun gramsAreConvertedInRewardAndMonthlyImpact() = runTest {
        val repository = LocalEcoRepository(0)
        val before = GetMonthlyImpactUseCase(repository)("user-1").getOrThrow().recycledKg
        val result = repository.submitWaste(WasteType.Plastic, 1000.0, "g", null).getOrThrow()
        assertEquals(EcoRatingCalculator.pointsForWaste(WasteType.Plastic, 1.0, "kg"), result.earnedPoints)
        assertEquals(before + 1.0, GetMonthlyImpactUseCase(repository)("user-1").getOrThrow().recycledKg, 0.001)
    }
}
