package com.ecodala.feature.leaderboard.presentation

import org.junit.Assert.*
import org.junit.Test

class FacultyLeaderboardSummaryTest {
    @Test fun summaryComputesLeaderAndTotalsOutsideComposable() {
        val first = FacultyLeaderboardEntry(2, "Engineering", "ENG", 100, 10, "A", 1)
        val second = FacultyLeaderboardEntry(1, "Science", "SCI", 250, 20, "B", 2)
        val summary = FacultyLeaderboardSummary.from(listOf(first, second))
        assertEquals("Science", summary.leader?.name)
        assertEquals(350, summary.totalPoints)
        assertEquals(30, summary.totalMembers)
        assertNull(FacultyLeaderboardSummary.from(emptyList()).leader)
    }
}
