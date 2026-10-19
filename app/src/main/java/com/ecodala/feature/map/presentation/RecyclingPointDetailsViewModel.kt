package com.ecodala.feature.map.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.domain.model.RecyclingPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecyclingPointDetailsUiState(
    val point: RecyclingPoint? = null,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val sustainableFact: String = "Recycling one ton of paper saves about 17 trees and thousands of liters of water."
)

class RecyclingPointDetailsViewModel @JvmOverloads constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: LocalEcoRepository = LocalEcoRepository.shared
) : ViewModel() {
    private val pointId: String = checkNotNull(savedStateHandle["pointId"])

    private val _uiState = MutableStateFlow(RecyclingPointDetailsUiState())
    val uiState: StateFlow<RecyclingPointDetailsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.recyclingPoint(pointId)
                .onSuccess { point -> _uiState.update { it.copy(point = point, isLoading = false) } }
                .onFailure { error -> _uiState.update { it.copy(isLoading = false, errorMessage = error.message) } }
        }
    }
}
