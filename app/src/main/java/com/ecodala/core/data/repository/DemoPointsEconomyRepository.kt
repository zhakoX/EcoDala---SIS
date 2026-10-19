package com.ecodala.core.data.repository

import com.ecodala.core.domain.model.PointsEvent
import com.ecodala.core.domain.model.WasteSubmission
import com.ecodala.core.domain.repository.PointsEconomyRepository

class DemoPointsEconomyRepository(
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : PointsEconomyRepository {
    override suspend fun getPointEvents(userId: String): Result<List<PointsEvent>> {
        return repository.getPointEvents(userId)
    }

    override suspend fun getWasteSubmissions(userId: String): Result<List<WasteSubmission>> {
        return repository.getWasteSubmissions(userId)
    }
}
