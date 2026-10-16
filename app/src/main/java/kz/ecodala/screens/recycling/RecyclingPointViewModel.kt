package kz.ecodala.screens.recycling

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.ecodala.data.repository.EcoRepository

class RecyclingPointViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository: EcoRepository = EcoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecyclingPointUiState())
    val uiState: StateFlow<RecyclingPointUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "RecyclingPointViewModel initialized")
        loadRecyclingPoint()
    }

    fun retry() {
        loadRecyclingPoint()
    }

    fun toggleFavorite() {
        val pointId = _uiState.value.recyclingPoint?.id ?: return
        val isFavorite = repository.toggleFavorite(pointId) ?: return

        _uiState.value = _uiState.value.copy(isFavorite = isFavorite)
    }

    private fun loadRecyclingPoint() {
        val pointId = savedStateHandle.get<Int>(POINT_ID)
        if (pointId == null) {
            _uiState.value = _uiState.value.copy(
                status = RecyclingPointStatus.EMPTY,
                recyclingPoint = null,
                isFavorite = false,
                errorMessage = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                status = RecyclingPointStatus.LOADING,
                errorMessage = null
            )

            runCatching { repository.loadRecyclingPoint(pointId) }
                .onSuccess { recyclingPoint ->
                    _uiState.value = if (recyclingPoint == null) {
                        _uiState.value.copy(
                            status = RecyclingPointStatus.EMPTY,
                            recyclingPoint = null,
                            isFavorite = false,
                            errorMessage = null
                        )
                    } else {
                        _uiState.value.copy(
                            status = RecyclingPointStatus.CONTENT,
                            recyclingPoint = recyclingPoint,
                            isFavorite = repository.isFavorite(recyclingPoint.id),
                            errorMessage = null
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        status = RecyclingPointStatus.ERROR,
                        recyclingPoint = null,
                        isFavorite = false,
                        errorMessage = throwable.message ?: "Unable to load recycling point"
                    )
                }
        }
    }

    override fun onCleared() {
        Log.d(TAG, "RecyclingPointViewModel.onCleared")
        super.onCleared()
    }

    private companion object {
        const val TAG = "SIS4_VIEWMODEL"
        const val POINT_ID = "id"
    }
}
