package com.ecodala.feature

import androidx.lifecycle.ViewModelStore
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.EcoRatingCalculator
import com.ecodala.core.domain.model.WasteType
import com.ecodala.core.testing.MainDispatcherRule
import com.ecodala.feature.home.presentation.HomeViewModel
import com.ecodala.feature.profile.presentation.ProfileViewModel
import com.ecodala.feature.profile.presentation.RecyclingHistoryViewModel
import com.ecodala.feature.tree.presentation.VirtualTreeViewModel
import com.ecodala.feature.leaderboard.presentation.LeaderboardViewModel
import com.ecodala.feature.challenges.presentation.ChallengesViewModel
import com.ecodala.feature.achievements.presentation.AchievementsViewModel
import com.ecodala.feature.submit.presentation.SubmitWasteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SharedProgressViewModelsTest {
    @get:Rule val dispatcher = MainDispatcherRule()

    @Test fun submissionReachesEveryRetainedProgressViewModel() = runTest {
        val repository = LocalEcoRepository(0)
        val home = HomeViewModel(repository)
        val profile = ProfileViewModel(repository)
        val history = RecyclingHistoryViewModel(repository)
        val tree = VirtualTreeViewModel(repository)
        val leaderboard = LeaderboardViewModel(repository)
        val challenges = ChallengesViewModel(repository)
        val achievements = AchievementsViewModel(repository)
        val submit = SubmitWasteViewModel(repository)
        val store = ViewModelStore()
        listOf(home, profile, history, tree, leaderboard, challenges, achievements, submit)
            .forEachIndexed { index, model -> store.put(index.toString(), model) }
        advanceUntilIdle()
        val count = history.uiState.value.submissions.size
        val challengeProgress = challenges.challenges.value.first().progress
        submit.onWasteTypeChange(WasteType.Plastic)
        submit.onUnitChange("kg")
        submit.onQuantityChange(5)
        submit.submitWaste()
        advanceUntilIdle()
        val total = repository.data.value.ecoPoints
        assertEquals(total, home.uiState.value.ecoPoints)
        assertEquals(total, profile.uiState.value.totalPoints)
        assertEquals(total, leaderboard.entries.value.first { it.isCurrentUser }.points)
        assertEquals(count + 1, history.uiState.value.submissions.size)
        assertEquals(EcoRatingCalculator.calculate(total).level, tree.tree.value.level)
        assertTrue(challenges.challenges.value.first().progress > challengeProgress)
        assertTrue(achievements.achievements.value.first { it.title == "Plastic Saver" }.isUnlocked)
        store.clear()
    }
}
