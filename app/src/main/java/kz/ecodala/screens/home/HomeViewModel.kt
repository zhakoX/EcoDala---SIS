package kz.ecodala.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.ecodala.data.repository.EcoRepository

class HomeViewModel(
    private val repository: EcoRepository = EcoRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "HomeViewModel initialized")
        loadHomeData()
    }

    fun retry() {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                status = HomeStatus.LOADING,
                errorMessage = null
            )

            runCatching { repository.loadHomeData() }
                .onSuccess { homeData ->
                    _uiState.value = _uiState.value.copy(
                        status = HomeStatus.CONTENT,
                        user = homeData.user,
                        stats = homeData.stats,
                        recentAchievements = homeData.recentAchievements,
                        errorMessage = null
                    )
                }
                .onFailure { throwable ->
                    _uiState.value = _uiState.value.copy(
                        status = HomeStatus.ERROR,
                        user = null,
                        stats = null,
                        recentAchievements = emptyList(),
                        errorMessage = throwable.message ?: "Unable to load home data"
                    )
                }
        }
    }

    override fun onCleared() {
        Log.d(TAG, "HomeViewModel.onCleared")
        super.onCleared()
    }

    private companion object {
        const val TAG = "SIS4_VIEWMODEL"
    }
}
