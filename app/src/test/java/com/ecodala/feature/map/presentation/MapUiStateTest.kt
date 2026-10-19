package com.ecodala.feature.map.presentation

import com.ecodala.core.data.dummy.DummyEcoData
import com.ecodala.core.domain.model.WasteType
import org.junit.Assert.*
import org.junit.Test

class MapUiStateTest {
    @Test fun filteredPoints_filtersByWasteTypeAndSearchQuery() {
        val state = MapUiState(searchQuery = "green", selectedWasteType = WasteType.Plastic,
            allPoints = DummyEcoData.recyclingPoints)
        assertTrue(state.filteredPoints.isNotEmpty())
        assertTrue(state.filteredPoints.all { WasteType.Plastic in it.acceptedWasteTypes && it.name.contains("green", true) })
    }

    @Test fun defaultListHasAtLeastTenUniqueLocations() {
        val state = MapUiState(allPoints = DummyEcoData.recyclingPoints, isLoading = false)
        assertTrue(state.filteredPoints.size >= 10)
        assertEquals(state.filteredPoints.size, state.filteredPoints.map { it.id }.distinct().size)
    }
}
