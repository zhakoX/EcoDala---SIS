package com.ecodala.domain.repository

import com.ecodala.domain.model.PointsEvent
import com.ecodala.domain.model.WasteSubmission

interface PointsEconomyRepository {
    suspend fun getCompletedChallengeCount(): Result<Int>
    suspend fun getPointEvents(userId: String): Result<List<PointsEvent>>
    suspend fun getWasteSubmissions(userId: String): Result<List<WasteSubmission>>
}
