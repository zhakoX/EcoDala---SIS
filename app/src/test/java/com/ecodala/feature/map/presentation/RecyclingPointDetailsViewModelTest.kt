package com.ecodala.feature.map.presentation

import androidx.lifecycle.SavedStateHandle
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecyclingPointDetailsViewModelTest {
    @get:Rule val dispatcher = MainDispatcherRule()

    @Test fun pointIdFromSavedStateHandle_selectsTheExactPoint() = runTest {
        val model = RecyclingPointDetailsViewModel(SavedStateHandle(mapOf("pointId" to "point-8")), LocalEcoRepository(10))
        advanceUntilIdle()
        assertEquals("point-8", model.uiState.value.point?.id)
        assertFalse(model.uiState.value.isLoading)
    }

    @Test fun invalidIdShowsError_insteadOfAnotherPoint() = runTest {
        val model = RecyclingPointDetailsViewModel(SavedStateHandle(mapOf("pointId" to "missing")), LocalEcoRepository(10))
        advanceUntilIdle()
        assertNull(model.uiState.value.point)
        assertNotNull(model.uiState.value.errorMessage)
    }
}
