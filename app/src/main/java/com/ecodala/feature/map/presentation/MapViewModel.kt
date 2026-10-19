package com.ecodala.feature.map.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.RecyclingPoint
import com.ecodala.core.domain.model.WasteType
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MapUiState(
    val searchQuery: String = "",
    val selectedWasteType: WasteType? = null,
    val allPoints: List<RecyclingPoint> = emptyList(),
    val favouriteIds: Set<String> = emptySet(),
    val selectedPoint: RecyclingPoint? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val demoErrorEnabled: Boolean = false,
    val showList: Boolean = false
) {
    // Derived application state belongs with UiState, never in the Screen.
    val filteredPoints: List<RecyclingPoint>
        get() = allPoints.filter { point ->
            (selectedWasteType == null || selectedWasteType in point.acceptedWasteTypes) &&
                (searchQuery.isBlank() || point.name.contains(searchQuery.trim(), true) ||
                    point.address.contains(searchQuery.trim(), true) ||
                    point.acceptedWasteTypes.any { it.title.contains(searchQuery.trim(), true) })
        }
}

class MapViewModel @JvmOverloads constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val _uiState = MutableStateFlow(MapUiState(
        searchQuery = savedStateHandle["search"] ?: "",
        selectedWasteType = savedStateHandle.get<String>("category")?.let { WasteType.valueOf(it) },
        showList = savedStateHandle["showList"] ?: false
    ))
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()
    private var loadJob: Job? = null

    init {
        Log.d("SIS4.MapViewModel", "init instance=${hashCode()}")
        viewModelScope.launch {
            repository.data.collect { data -> _uiState.update { it.copy(favouriteIds = data.favouriteIds) } }
        }
        refreshMapData()
    }

    fun refreshMapData() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, selectedPoint = null) }
            val result = repository.recyclingPoints(_uiState.value.demoErrorEnabled)
            _uiState.update { it.copy(isLoading = false,
                allPoints = result.getOrDefault(emptyList()), errorMessage = result.exceptionOrNull()?.message) }
        }
    }

    fun onSearchQueryChange(value: String) {
        savedStateHandle["search"] = value
        _uiState.update { it.copy(searchQuery = value, selectedPoint = null) }
    }

    fun onCategorySelected(type: WasteType?) {
        savedStateHandle["category"] = type?.name
        _uiState.update { it.copy(selectedWasteType = type, selectedPoint = null) }
    }

    fun onFavouriteClick(id: String) {
        viewModelScope.launch {
            try { repository.setFavourite(id) }
            catch (error: java.io.IOException) { _uiState.update { it.copy(errorMessage = error.message) } }
        }
    }

    fun onPointSelected(point: RecyclingPoint) { _uiState.update { it.copy(selectedPoint = point) } }
    fun onSelectionDismiss() { _uiState.update { it.copy(selectedPoint = null) } }
    fun onRetry() = refreshMapData()
    fun onDemoErrorClick() {
        _uiState.update { it.copy(demoErrorEnabled = true) }
        refreshMapData()
    }
    fun onDemoErrorDisabled() {
        _uiState.update { it.copy(demoErrorEnabled = false) }
        refreshMapData()
    }
    fun onListModeChange() {
        val showList = !_uiState.value.showList
        savedStateHandle["showList"] = showList
        _uiState.update { it.copy(showList = showList, selectedPoint = null) }
    }

    override fun onCleared() {
        Log.d("SIS4.MapViewModel", "onCleared instance=${hashCode()}")
        super.onCleared()
    }
}
