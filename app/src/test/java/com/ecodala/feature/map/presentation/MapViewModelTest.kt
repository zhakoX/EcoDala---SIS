package com.ecodala.feature.map.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelStore
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.WasteType
import com.ecodala.core.testing.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {
    @get:Rule val dispatcher = MainDispatcherRule()

    private fun clear(model: MapViewModel) {
        ViewModelStore().apply { put("map", model); clear() }
    }

    @Test fun searchAndCategory_matchEveryAcceptedTypeNotJustPrimaryType() = runTest {
        val model = MapViewModel(SavedStateHandle(), LocalEcoRepository(10))
        advanceUntilIdle()
        assertEquals(12, model.uiState.value.filteredPoints.size)
        model.onSearchQueryChange("campus")
        model.onCategorySelected(WasteType.Plastic)
        assertTrue(model.uiState.value.filteredPoints.any { it.id == "point-5" })
        assertTrue(model.uiState.value.filteredPoints.all { WasteType.Plastic in it.acceptedWasteTypes })
        model.onSearchQueryChange("does-not-exist")
        assertTrue(model.uiState.value.filteredPoints.isEmpty())
        clear(model)
    }

    @Test fun favouriteToggle_andSavedSearchSurviveViewModelRecreation() = runTest {
        val repository = LocalEcoRepository(10)
        val handle = SavedStateHandle()
        val model = MapViewModel(handle, repository)
        advanceUntilIdle()
        model.onSearchQueryChange("campus")
        model.onCategorySelected(WasteType.Paper)
        model.onFavouriteClick("point-5")
        advanceUntilIdle()
        assertTrue("point-5" in model.uiState.value.favouriteIds)
        val restoredHandle = SavedStateHandle(mapOf("search" to handle.get<String>("search"), "category" to handle.get<String>("category")))
        clear(model)
        val restored = MapViewModel(restoredHandle, repository)
        advanceUntilIdle()
        assertEquals("campus", restored.uiState.value.searchQuery)
        assertEquals(WasteType.Paper, restored.uiState.value.selectedWasteType)
        assertTrue("point-5" in restored.uiState.value.favouriteIds)
        restored.onFavouriteClick("point-5")
        advanceUntilIdle()
        assertFalse("point-5" in restored.uiState.value.favouriteIds)
        clear(restored)
    }

    @Test fun errorRetry_keepsErrorUntilSimulationIsDisabled() = runTest {
        val model = MapViewModel(SavedStateHandle(), LocalEcoRepository(10))
        assertTrue(model.uiState.value.isLoading)
        advanceUntilIdle()
        model.onDemoErrorClick()
        advanceUntilIdle()
        assertNotNull(model.uiState.value.errorMessage)
        assertTrue(model.uiState.value.allPoints.isEmpty())
        model.onRetry()
        advanceUntilIdle()
        assertNotNull(model.uiState.value.errorMessage)
        model.onDemoErrorDisabled()
        advanceUntilIdle()
        assertNull(model.uiState.value.errorMessage)
        assertFalse(model.uiState.value.isLoading)
        assertEquals(12, model.uiState.value.filteredPoints.size)
        clear(model)
    }
}
