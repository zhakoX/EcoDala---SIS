package com.ecodala.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecodala.domain.model.WeatherDay
import com.ecodala.domain.repository.WeatherRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val items: List<WeatherDay>) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _state = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val state = _state.asStateFlow()
    private var loadJob: Job? = null

    init {
        retry()
    }

    fun retry() {
        if (loadJob?.isActive == true) return
        _state.value = WeatherUiState.Loading
        loadJob = viewModelScope.launch {
            try {
                _state.value = WeatherUiState.Success(repository.getItems())
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _state.value = WeatherUiState.Error(
                    "Could not load the forecast. Check your internet connection and retry.",
                )
            }
        }
    }
}
